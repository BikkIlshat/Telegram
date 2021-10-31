package com.hfad.telegram.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.hfad.telegram.R
import com.hfad.telegram.databinding.ActivityRegisterBinding
import com.hfad.telegram.ui.fragments.EnterPhoneNumberFragment
import com.hfad.telegram.utilits.initFirebase
import com.hfad.telegram.utilits.replaceFragment

/**
 * RegisterActivity управлет фрагментами EnterCodeFragment и EnterPhoneNumberFragment
 * задача этой Activity только в том что бы произвести авторизацию или зарегистрировать нового пользователя
 * при вводе телефона который есть в базе данных происходит авторизация  пользователя
 * при вводе нового номера телефона пользователя которого нет в базе данных происходит создание  нового пользователя
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = binding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment(), false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}