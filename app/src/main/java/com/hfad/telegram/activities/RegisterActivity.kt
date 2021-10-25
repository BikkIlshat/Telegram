package com.hfad.telegram.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.hfad.telegram.R
import com.hfad.telegram.databinding.ActivityMainBinding
import com.hfad.telegram.databinding.ActivityRegisterBinding
import com.hfad.telegram.ui.fragments.EnterPhoneNumberFragment

class RegisterActivity : AppCompatActivity() {
    lateinit var mToolbar: Toolbar

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        mToolbar = binding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        supportFragmentManager.beginTransaction()
            .add(R.id.registerDataContainer, EnterPhoneNumberFragment()).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}