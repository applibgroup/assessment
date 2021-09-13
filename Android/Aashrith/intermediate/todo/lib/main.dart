// @dart=2.9
// ignore: import_of_legacy_library_into_null_safe
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:todo/auth/authscreen.dart';
import 'package:todo/screens/home.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: StreamBuilder(
        stream: FirebaseAuth.instance.onAuthStateChanged,
        builder: (context, usersnapshot) {
          if (usersnapshot.hasData) {
            return Home();
          }
          else {
            return AuthScreen();
          }
        },
      ),
      debugShowCheckedModeBanner: false,
      theme:
      ThemeData(brightness: Brightness.dark, primaryColor: Colors.orange[500]),
    );
  }
}
