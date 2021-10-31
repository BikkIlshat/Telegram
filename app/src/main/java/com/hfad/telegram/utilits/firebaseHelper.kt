package com.hfad.telegram.utilits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hfad.telegram.models.User

lateinit var AUTH: FirebaseAuth
lateinit var UID:String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase
        .getInstance("https://telegram-f39eb-default-rtdb.europe-west1.firebasedatabase.app")
        .getReference()
    USER = User()
    UID = AUTH.currentUser?.uid.toString() // получили уникальный идентификационный номер

}