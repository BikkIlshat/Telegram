package com.hfad.telegram.utilits

import android.net.Uri
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



// inline  crossinline -> избегаем потери по производительности
inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS)
        .child(CURRENT_UID)  // полученный url устанавливаем в child(CHILD_PHOTO_URL) нашего пользователя
        .child(CHILD_PHOTO_URL).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }


}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putImageToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) } // сделали вызов через живой шаблон (File | Settings | Editor | Live Templates)
}

inline fun initUser( crossinline function: () -> Unit) {
    REF_DATABASE_ROOT // Realtime Database root - > telegram-f39eb
        .child(NODE_USERS)  // Realtime Database - > users
        .child(CURRENT_UID) // Realtime Database - >  SQvMtyLe6lNU9V55H8N1vQoAUdN2
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(User::class.java) ?: User() // ЭлвисОператор  выполняем  код it.getValue(User::class.java) если он не null. Если null он выполнит этот код -> User()
            if (USER.username.isEmpty()) {
                USER.username = CURRENT_UID
            }
            function()
        })
}