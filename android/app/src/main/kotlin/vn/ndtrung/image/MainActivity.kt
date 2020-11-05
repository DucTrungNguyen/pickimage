package vn.ndtrung.image

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import java.util.*

class MainActivity: FlutterActivity() {

    private val CHANNEL = "vn.ndtrung.image/pick_image"
//    private var callbackManager: CallbackManager? = null
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            run {
                resultCall(call, result)

            }

        }
//        callbackManager = CallbackManager.Factory.create()
    }


    private fun resultCall(@NonNull call: MethodCall, result: MethodChannel.Result) {

        if ( call.method == "getImageFromGallery"){
            Log.d("vao method", "Vao method getImageFromGallery")

            val arrayImage = getAllShownImagesPath(activity)
            result.success(arrayImage);

        }


    }

    private fun getAllShownImagesPath(activity: Activity): ArrayList<String?>? {
        val uri: Uri
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String?>()
        var absolutePathOfImage: String? = null
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            mViewDataBinding.txtNotAllowPer.setVisibility(View.VISIBLE)
        } else {
//            mViewDataBinding.txtNotAllowPer.setVisibility(View.GONE)
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            cursor = activity.contentResolver.query(uri, projection, null,
                    null, null)
            column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data)
                listOfAllImages.add(absolutePathOfImage)
            }
        }
        return listOfAllImages
    }
}
