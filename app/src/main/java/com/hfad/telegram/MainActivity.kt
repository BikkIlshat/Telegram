package com.hfad.telegram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.ActivityMainBinding
import com.hfad.telegram.ui.fragments.ChatsFragment
import com.hfad.telegram.ui.objects.AppDrawer
import com.hfad.telegram.utilits.AUTH
import com.hfad.telegram.utilits.initFirebase
import com.hfad.telegram.utilits.replaceActivity
import com.hfad.telegram.utilits.replaceFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    // выполняем всю функциональность нашей активити
    private fun initFunc() {
        if (AUTH.currentUser != null) { //  проверка если пользователь авторизовался выполняем код ниже:
            setSupportActionBar(mToolbar)
            mAppDrawer.create() // нарисовали выдвижное  меню
            replaceFragment(ChatsFragment(), false) // сделали её вызов в файле расширений функций funs.kt
        } else { // иначе переходим в окно авторизации пользователя
            replaceActivity(RegisterActivity()) // сделали её вызов в файле расширений функций funs.kt
        }
    }


    private fun initFields() {
        mToolbar = binding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar) // проинициализировали наше mToolbar
        initFirebase() //  проинициализировали наш FirebaseAuth и REF_DATABASE_ROOT
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}