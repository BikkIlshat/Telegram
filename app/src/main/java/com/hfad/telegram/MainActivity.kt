package com.hfad.telegram

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.ActivityMainBinding
import com.hfad.telegram.ui.fragments.ChatsFragment
import com.hfad.telegram.ui.objects.AppDrawer
import com.hfad.telegram.utilits.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(){

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this // присвоили нашей константе ссылку на MainActivity (HW-20)
        initFirebase() //  проинициализировали наш FirebaseAuth и REF_DATABASE_ROOT
        initUser { // как проинициализируется User только потом выполнится initFields() initFunc()
            CoroutineScope(Dispatchers.IO).launch {
                Looper.prepare()
                initContacts()
            }
            initFields()
            initFunc()
        }
    }




    // выполняем всю функциональность нашей активити
    private fun initFunc() {
        if (AUTH.currentUser != null) { //  проверка если пользователь авторизовался выполняем код ниже:
            setSupportActionBar(mToolbar)
            mAppDrawer.create() // нарисовали выдвижное  меню
            replaceFragment(
                ChatsFragment(),
                false
            ) // сделали её вызов в файле расширений функций funs.kt
        } else { // иначе переходим в окно авторизации пользователя
            replaceActivity(RegisterActivity()) // сделали её вызов в файле расширений функций funs.kt
        }

    }


    private fun initFields() {
        mToolbar = binding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar) // проинициализировали наше mToolbar

    }

    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                READ_CONTACNTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initContacts()
        }
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}