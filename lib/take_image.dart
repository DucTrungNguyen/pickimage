
import 'package:flutter/material.dart';
import 'dart:io';
class TakeImage extends StatefulWidget {
  final dynamic image;

  TakeImage({this.image});
  @override
  _TakeImageState createState() => _TakeImageState();
}

class _TakeImageState extends State<TakeImage> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Image.file(File(widget.image))
      )
    );
  }
}
