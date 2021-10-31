package com.hfad.telegram.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.hfad.telegram.MainActivity
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentChangeNameBinding
import com.hfad.telegram.utilits.*

class ChangeNameFragment :
    ViewBindingFragment<FragmentChangeNameBinding>(FragmentChangeNameBinding::inflate) {


    override fun onResume() = with(binding) {
        super.onResume()
        setHasOptionsMenu(true)
        val fullnameList = USER.fullname.split(" ") // split() разделит нашу строку на 2 элемента и запишет это в fullnameList
        if (fullnameList.size > 1) {
            settingsInputName.setText(fullnameList[0])
            settingsInputSurname.setText(fullnameList[1])
        } else settingsInputName.setText(fullnameList[0])
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> changeName()
        }
        return true
    }

    private fun changeName() = with(binding) {
        val name = settingsInputName.text.toString()
        val surname = settingsInputSurname.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener { // повесили слушателя что всё хорошо
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.fullname = fullname
                        parentFragmentManager.popBackStack()
                    }
                }
        }
    }
}