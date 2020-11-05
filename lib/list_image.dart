
import 'package:flutter/material.dart';
import 'dart:io';
class ListImage extends StatefulWidget {
  final List<dynamic> listImage;

  ListImage({this.listImage});
  @override
  _ListImageState createState() => _ListImageState();
}

class _ListImageState extends State<ListImage> {
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: widget.listImage.length,
        itemBuilder: (context, index){
        var path = widget.listImage[index];
        return Padding(
          padding: const EdgeInsets.all(8.0),
          child: Image.file(File(path)),
        );

    });
  }
}
