package com.hfad.telegram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hfad.telegram.databinding.ActivityMainBinding
import com.hfad.telegram.ui.fragments.ChatsFragment
import com.hfad.telegram.ui.objects.AppDrawer

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
        setSupportActionBar(mToolbar)
        mAppDrawer.create() // нарисовали выдвижное  меню
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer, ChatsFragment()).commit()


    }


    private fun initFields() {
        mToolbar = binding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar) // проинициализировали наше mToolbar

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}