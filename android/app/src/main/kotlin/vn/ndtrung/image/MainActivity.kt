package vn.ndtrung.image

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : FlutterActivity() {

    private val CHANNEL = "vn.ndtrung.image/pick_image"
    private var imageFilePath: File? = null
    private var imageUri: Uri? = null

    //    private var callbackManager: CallbackManager? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            run {
                resultCall(call, result)

            }

        }
//        callbackManager = CallbackManager.Factory.create()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun resultCall(@NonNull call: MethodCall, result: MethodChannel.Result) {

        Log.d("ABc", call.method)

        if (call.method == "getImageFromGallery") {
            Log.d("vao method", "Vao method getImageFromGallery")

            val arrayImage = getAllShownImagesPath(activity)
            result.success(arrayImage);

        }else
        if (call.method == "getImageFromCamera") {

            Log.d("vao method", "vao method getImageFromCamera")
            val uri =  takePhoto()
            result.success(uri)
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getAllShownImagesPath(activity: Activity): ArrayList<String?>? {
        val uri: Uri
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String?>()
        var absolutePathOfImage: String? = null
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, 10)
//             requestPermissions(permission, 10)
        } else {
//            mViewDataBinding.txtNotAllowPer.setVisibility(View.GONE)
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            cursor = activity.contentResolver.query(uri, projection, null,
                    null, null)
            column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//            column_index_folder_name = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data)
                listOfAllImages.add(absolutePathOfImage)
            }
        }
        return listOfAllImages
    }

    fun takePhoto() : Uri? {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (pictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            try {
                imageFilePath = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return imageUri 
            }
            val photoUri = imageFilePath?.let { FileProvider.getUriForFile(context, context.getPackageName().toString() + ".provider", it) }
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(pictureIntent, 100)

            if (imageUri != null){
                return imageUri

            }
        }
        return imageUri
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)// getExternalFilesDir()
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir /* directory */
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = Uri.fromFile(imageFilePath)
            Log.d("Paths image", imageUri.toString())

        }
    }
}
