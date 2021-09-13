import 'package:flutter/material.dart';
// ignore: import_of_legacy_library_into_null_safe
import 'package:google_fonts/google_fonts.dart';

class Description extends StatelessWidget {
  final String title, description;

  const Description({Key? key, required this.title, required this.description}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Description')),
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,

        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              width: double.infinity,
              padding: EdgeInsets.all(30),
              child: Text(
                title,
                style: GoogleFonts.roboto(fontSize: 24, color: Colors.orange, fontWeight: FontWeight.bold),
              ),
              color: Colors.orange[50],
            ),

            Expanded(child:
            Container(
                width: double.infinity,
              // height: double.infinity,
              padding: EdgeInsets.all(30),
              child: Text(
                description,
                style: GoogleFonts.roboto(fontSize: 18, color: Colors.orange[700]),
              ),
              color: Colors.orange[100]
            ),
            ),
          ],
        ),
        decoration: BoxDecoration(
          color: Colors.white,
        ),
      ),
    );
  }
}
