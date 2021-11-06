package com.hfad.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.hfad.telegram.R
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.FragmentSettingsBinding
import com.hfad.telegram.utilits.*

class SettingsFragment :
    ViewBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()

    }

    //Инициализация всех полей фрагмента настроек
    private fun initFields() = with(binding) {
        settingsBio.text = USER.bio
        settingsFullName.text = USER.fullname
        settingsPhoneNumber.text = USER.phone
        settingsStatus.text = USER.status
        settingsUsername.text = USER.username
        settingsBtnChangeUsername.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        settingsBtnChangeBio.setOnClickListener { replaceFragment(ChangeBioFragment()) } // HW-19
        settingsChangePhoto.setOnClickListener { changePhotoUser() }
    }

    private fun changePhotoUser() {
        APP_ACTIVITY.cropActivityResultLauncher.launch(null)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut() // обращаемся к объекту Аутентификации и выходим из профиля
                APP_ACTIVITY.replaceActivity(RegisterActivity()) // запускаем окно регистрации
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment()) // меняем фрагмент на ChangeNameFragment
        }
        return true
    }

}