import 'dart:async';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/services.dart';
// import 'package:path_provider/path_provider.dart';
// import 'package:whatsup/model/post_model.dart';
// import 'package:dio/dio.dart';
// import 'package:image_downloader/image_downloader.dart';

class PickImage  {
  static const MethodChannel _channel =
  MethodChannel('vn.ndtrung.image/pick_image');
  // static var dio = Dio();

  static Future<List<dynamic>> getImageFromGallery(
    ) async {
    Map<String, dynamic> args;
    // if (Platform.isIOS) {
    //   args = <String, dynamic>{
    //     "stickerImage": imagePath,
    //     "backgroundTopColor": backgroundTopColor,
    //     "backgroundBottomColor": backgroundBottomColor,
    //     "attributionURL": attributionURL
    //   };
    // } else {
    //   // // final tempDir = await getTemporaryDirectory();
    //   //
    //   // File file = File(imagePath);
    //   // Uint8List bytes = file.readAsBytesSync();
    //   // var stickerdata = bytes.buffer.asUint8List();
    //   // String stickerAssetName = 'stickerAsset.png';
    //   // final Uint8List stickerAssetAsList = stickerdata;
    //   // final stickerAssetPath = '${tempDir.path}/$stickerAssetName';
    //   // file = await File(stickerAssetPath).create();
    //   // file.writeAsBytesSync(stickerAssetAsList);
    //   // args = <String, dynamic>{
    //   //   "stickerImage": stickerAssetName,
    //   //   "backgroundTopColor": backgroundTopColor,
    //   //   "backgroundBottomColor": backgroundBottomColor,
    //   //   "attributionURL": attributionURL
    //   // };
    // }
    final List<dynamic> response =
    await _channel.invokeMethod('getImageFromGallery', args);

    print(response.length);
    return response;
  }


}
