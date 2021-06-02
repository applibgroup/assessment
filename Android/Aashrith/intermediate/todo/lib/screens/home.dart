// ignore: import_of_legacy_library_into_null_safe
import 'package:cloud_firestore/cloud_firestore.dart';
// ignore: import_of_legacy_library_into_null_safe
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
// ignore: import_of_legacy_library_into_null_safe
import 'package:google_fonts/google_fonts.dart';

import 'addtask.dart';
import 'discription.dart';
// import 'package:intl/intl.dart';

// import 'add_task.dart';
// import 'description.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  String uid = '';
  @override
  void initState() {
    getuid();
    super.initState();
  }

  getuid() async {
    FirebaseAuth auth = FirebaseAuth.instance;
    final FirebaseUser user = await auth.currentUser();
    setState(() {
      uid = user.uid;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title:Text('TODO', style: GoogleFonts.roboto(fontSize: 20, color: Colors.white)),
        actions: [
          IconButton(
              icon: Icon(Icons.logout),
              onPressed: () async {
                await FirebaseAuth.instance.signOut();
              }),
        ],
          iconTheme: IconThemeData(
            color: Colors.white,
          )
      ),
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: StreamBuilder<QuerySnapshot>(
          stream: Firestore.instance.collection('tasks').document(uid).collection('mytasks').snapshots(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return Center(
                child: CircularProgressIndicator(),
              );
            } else {
              final docs = snapshot.data!.documents;
              return ListView.builder(
                itemCount: docs.length,
                itemBuilder: (context, index) {
                  // var time = (docs[index]['timestamp'] as Timestamp).toDate();
                  return InkWell(
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => Description(
                                title: docs[index]['title'],
                                description: docs[index]['description'],
                              )
                          )
                      );
                    },
                    child: Container(
                      margin: EdgeInsets.only(top: 15, left: 15, right: 15),
                      decoration: BoxDecoration(
                          boxShadow: [
                            BoxShadow(
                              color: Colors.grey.withOpacity(0.4),
                              spreadRadius: 3,
                              blurRadius: 7,
                              offset: Offset(5, 5),
                            ),
                          ],
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(10)),
                      height: 60,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Container(
                                  alignment: Alignment.centerRight,
                                    margin: EdgeInsets.only(left: 20),
                                    child: Text(docs[index]['title'],
                                        style: GoogleFonts.roboto(fontSize: 20, color: Colors.orange[600])
                                    )
                                ),
                                SizedBox(
                                  height: 5,
                                ),
                                // Container(
                                //     margin: EdgeInsets.only(left: 20),
                                //     child: Text(
                                //         DateFormat.yMd().add_jm().format(time)))
                              ]),
                            Container(
                                child: IconButton(
                            icon: Icon(
                              Icons.delete,
                              color: Colors.orange[600],
                            ),
                            onPressed: () async {
                              await Firestore.instance.collection('tasks').document(uid).collection('mytasks').document(docs[index]['time']).delete();
                            }))
                          ]
                          )
                      ),
                    );
                },
              );
            }
          },
        ),
        decoration: BoxDecoration(
          color: Colors.orange[200],
        ),
      ),
      floatingActionButton: FloatingActionButton(
          child: Icon(Icons.add, color: Colors.white),
          backgroundColor: Theme.of(context).primaryColor,
          onPressed: () {
            Navigator.push(
                context, MaterialPageRoute(builder: (context) => AddTask()));
          }),
    );
  }
}
