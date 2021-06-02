// ignore: import_of_legacy_library_into_null_safe
import 'package:cloud_firestore/cloud_firestore.dart';
// ignore: import_of_legacy_library_into_null_safe
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:fluttertoast/fluttertoast.dart';
// ignore: import_of_legacy_library_into_null_safe
import 'package:google_fonts/google_fonts.dart';

import 'home.dart';

class AddTask extends StatefulWidget {

  @override
  _AddTaskState createState() => _AddTaskState();
}

class _AddTaskState extends State<AddTask> {
  TextEditingController titleController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();


  addtasktofirebase() async {
    FirebaseAuth auth = FirebaseAuth.instance;
    final FirebaseUser user = await auth.currentUser();
    String uid = user.uid;
    var time = DateTime.now();
    await Firestore.instance
        .collection('tasks')
        .document(uid)
        .collection('mytasks')
        .document(time.toString())
        .setData({
      'title': titleController.text,
      'description': descriptionController.text,
      'time': time.toString(),
      'timestamp': time
    });
    FocusScope.of(context).unfocus();
    Fluttertoast.showToast(msg: 'Data Added');

    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => Home()
        )
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title:Text('New Task', style: GoogleFonts.roboto(fontSize: 20, color: Colors.white)),
      iconTheme: IconThemeData(
        color: Colors.white,
      )
      ),
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
          padding: EdgeInsets.all(20),
          child: Column(
            children: [
              Container(
                child: TextField(
                  style: TextStyle(color: Colors.orange),
                  cursorColor: Colors.orange,
                  controller: titleController,
                  decoration: InputDecoration(
                    labelText: "Title",
                    labelStyle: GoogleFonts.roboto(fontSize: 16, color: Colors.orangeAccent),
                    enabledBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                      borderSide: BorderSide(
                        color: Colors.orangeAccent,
                        width: 0.5,
                      ),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                      borderSide: BorderSide(
                        color: Colors.orange,
                        width: 1.0,
                      ),
                    ),
                  ),
                ),
              ),
              SizedBox(height: 10),
              Container(
                child: TextField(
                  style: TextStyle(color: Colors.orange),
                  cursorColor: Colors.orange,
                  // maxLines: 8,
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.newline,
                  controller: descriptionController,
                  decoration: InputDecoration(
                    labelText: "Description",
                    labelStyle: GoogleFonts.roboto(fontSize: 16, color: Colors.orangeAccent),
                    alignLabelWithHint: true,
                    enabledBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                      borderSide: BorderSide(
                        color: Colors.orangeAccent,
                        width: 0.5,
                      ),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                      borderSide: BorderSide(
                        color: Colors.orange,
                        width: 1.0,
                      ),
                    ),
                  ),
                ),
              ),
              SizedBox(height: 10),
              Container(
                  width: double.infinity,
                  height: 60,
                  child: ElevatedButton(
                    style: ButtonStyle(backgroundColor:
                    MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
                          return Theme.of(context).primaryColor;
                        }
                        )
                    ),
                    child: Text('Add Task', style: GoogleFonts.roboto(fontSize: 16)),
                    onPressed: () {
                      addtasktofirebase();
                    },
                  ))
            ],
          ),
        decoration: BoxDecoration(
          color: Colors.white,
        ),
      ),
    );
  }
}
