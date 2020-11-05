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

    final List<dynamic> response =
    await _channel.invokeMethod('getImageFromGallery', args);

    // print(response[0]);
    return response;
  }

  static Future<dynamic> getImageFromCamera(
      ) async {
    Map<String, dynamic> args;

    print('vao getImageFromCamera');
    final dynamic response =
    await _channel.invokeMethod('getImageFromCamera', args);

    print(response);
    return response;
  }


}
