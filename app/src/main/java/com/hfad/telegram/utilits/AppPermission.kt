package com.hfad.telegram.utilits

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Файл  для управления всеми нашими разрешениями
 */

const val READ_CONTACNTS = Manifest.permission.READ_CONTACTS
const val PERMISSION_REQUEST = 200

fun checkPermission(permission: String): Boolean {
    return if (Build.VERSION.SDK_INT >= 23 // если SDK больше чем 23
        && ContextCompat.checkSelfPermission( // и разрешение еще не выдано
            APP_ACTIVITY,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(permission), PERMISSION_REQUEST) // вызываем диалоговое окно где запрашиваем разрешение на четине контактов, arrayOf(permission) <- пермишены передаются ввиде массивов
        false

    } else true
}

