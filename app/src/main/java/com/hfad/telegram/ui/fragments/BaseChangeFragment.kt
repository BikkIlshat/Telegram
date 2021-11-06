package com.hfad.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.hfad.telegram.MainActivity
import com.hfad.telegram.R
import com.hfad.telegram.utilits.APP_ACTIVITY

open class BaseChangeFragment : Fragment() {


    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true) // разрешаем создание OptionsMenu
        if (activity is MainActivity)
            APP_ACTIVITY.mAppDrawer.disableDrawer() // как только запускается базовый фрагмент т.е. любой фрагмент отличный от ChatsFragment то у нас запустится этот код и соответственно отключит наш драйвер
    }

    override fun onStop() {
        super.onStop()
        if (activity is MainActivity)
            APP_ACTIVITY.mAppDrawer.enableDrawer()
        APP_ACTIVITY.hideKeyboard()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        APP_ACTIVITY.menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {
    }

}