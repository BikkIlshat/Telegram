package com.hfad.telegram.utilits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hfad.telegram.models.User

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference //получаем рутовую ссылку
lateinit var REF_STORAGE_ROOT: StorageReference  //получаем рутовую ссылку
lateinit var USER: User


const val NODE_USERS = "users"
const val NODE_USERNAMES = "usernames"

//создали папку
const val FOLD_PROFILE_IMAGE = "profile_image"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photoUrl"


fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase
        .getInstance("https://telegram-f39eb-default-rtdb.europe-west1.firebasedatabase.app")
        .getReference()
    USER = User()
    CURRENT_UID = AUTH.currentUser?.uid.toString() // получили уникальный идентификационный номер
    REF_STORAGE_ROOT = FirebaseStorage
        .getInstance().getReference()

}