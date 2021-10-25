package com.hfad.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.hfad.telegram.MainActivity
import com.hfad.telegram.R
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.FragmentSettingsBinding
import com.hfad.telegram.utilits.AUTH
import com.hfad.telegram.utilits.replaceActivity

class SettingsFragment :
    ViewBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut() // обращаемся к объекту Аутентификации и выходим из профиля
                (activity as MainActivity).replaceActivity(RegisterActivity()) // запускаем окно регистрации
            }
        }
        return true
    }

}