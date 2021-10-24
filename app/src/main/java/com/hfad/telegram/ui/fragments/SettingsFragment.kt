package com.hfad.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentSettingsBinding

class SettingsFragment : ViewBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate){

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu,menu)
    }
}