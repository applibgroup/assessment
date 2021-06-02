// ignore: import_of_legacy_library_into_null_safe
import 'package:cloud_firestore/cloud_firestore.dart';
// ignore: import_of_legacy_library_into_null_safe
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
// ignore: import_of_legacy_library_into_null_safe
import 'package:google_fonts/google_fonts.dart';

class AuthForm extends StatefulWidget {
  @override
  _AuthFormState createState() => _AuthFormState();
}

class _AuthFormState extends State<AuthForm> {

  final _key = GlobalKey<FormState>();
  var _email = '';
  var _password = '';
  var _username = '';
  bool login = true;

  // ignore: non_constant_identifier_names
  start_authentication() {
    final validity = _key.currentState!.validate();
    FocusScope.of(context).unfocus();

    if (validity) {
      _key.currentState!.save();
      submitform(_email, _password, _username);
    }
  }

  submitform(String email, String password, String username) async {
    final auth = FirebaseAuth.instance;
    AuthResult authResult;
    try {
      if (login) {
        authResult = await auth.signInWithEmailAndPassword(email: email, password: password);
      }
      else {
        authResult = await auth.createUserWithEmailAndPassword(email: email, password: password);
        String uid = authResult.user.uid;
        await Firestore.instance.collection('users').document(uid).setData({'username': username, 'email': email});
      }
    } on PlatformException catch (err) {
      var message = 'firebase error occured';
      if (err.message != null) {
        message = err.message!;
      }
      final snackBar = SnackBar(content: Text(message), backgroundColor: Colors.red);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
    } catch (err) {
      print(err);
    }
  }

  //------------------------------------------

  @override
  Widget build(BuildContext context) {
    return Container(
        // padding: EdgeInsets.fromLTRB(0, 0, 0, 50),
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              // color: Colors.pink,
              // margin: EdgeInsets.fromLTRB(0, 0, 0, 50),
              height: 80,
              width: 100,
              padding: EdgeInsets.fromLTRB(0, 0, 0, 20),
              child: Image.asset('assets/todo.png'),
            ), //Image,
            Container(
                padding: EdgeInsets.fromLTRB(30, 0, 30, 0),
                child: Form(
                  key: _key,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      if (!login)
                        TextFormField(
                          style: TextStyle(color: Colors.orange),
                          cursorColor: Colors.orange,
                          keyboardType: TextInputType.emailAddress,
                          key: ValueKey('username'),
                          validator: (value) {
                            if (value!.isEmpty) {
                              return 'Incorrect Username';
                            }
                            return null;
                          },
                          onSaved: (value) {
                            _username = value!;
                          },
                            decoration: InputDecoration(
                              labelText: "Username",
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
                      SizedBox(height: 10),
                      TextFormField(
                        style: TextStyle(color: Colors.orange),
                        cursorColor: Colors.orange,
                        keyboardType: TextInputType.emailAddress,
                        key: ValueKey('email'),
                        validator: (value) {
                          if (value!.isEmpty || !value.contains('@')) {
                            return 'Incorrect Email';
                          }
                          return null;
                        },
                        onSaved: (value) {
                          _email = value!;
                        },
                          decoration: InputDecoration(
                            labelText: "Email ID",
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
                      SizedBox(height: 10),
                      TextFormField(
                        style: TextStyle(color: Colors.orange),
                        cursorColor: Colors.orange,
                        obscureText: true,
                        keyboardType: TextInputType.emailAddress,
                        key: ValueKey('password'),
                        validator: (value) {
                          if (value!.isEmpty) {
                            return 'Incorrect password';
                          }
                          return null;
                        },
                        onSaved: (value) {
                          _password = value!;
                        },
                        decoration: InputDecoration(
                          labelText: "Password",
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
                      SizedBox(height: 10),
                      Container(
                          // padding: EdgeInsets.all(10),
                          width: double.infinity,
                          height: 60,
                          child: ElevatedButton(
                              child: login
                                  ? Text('Sign In',
                                  style: GoogleFonts.roboto(fontSize: 16))
                                  : Text('Sign Up',
                                  style: GoogleFonts.roboto(fontSize: 16)),
                              style: ButtonStyle(backgroundColor:
                              MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
                                return Theme.of(context).primaryColor;
                              }
                              )
                              ),
                              onPressed: () {
                                start_authentication();
                              }
                              )
                      ),
                      Container(
                        child: TextButton(
                            onPressed: () {
                              setState(() {
                                login = !login;
                              });
                            },
                            child: login
                                ? Text('Not a member? sign up here',
                                style: GoogleFonts.roboto(
                                  fontSize: 16, color: Colors.orangeAccent),
                            )
                                : Text('Already a Member? sign in here',
                                style: GoogleFonts.roboto(
                                    fontSize: 16, color: Colors.orangeAccent))),
                      ),
                      // SizedBox(height: 60),
                    ],
                  ),
                ))
          ],
        ),
        decoration: BoxDecoration(
          color: Colors.white,
        ),
    );
  }
}
