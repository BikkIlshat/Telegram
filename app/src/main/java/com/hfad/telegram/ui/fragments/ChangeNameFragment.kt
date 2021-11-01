package com.hfad.telegram.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentChangeNameBinding
import com.hfad.telegram.utilits.*

class ChangeNameFragment : BaseChangeFragment() {

    private var _binding: FragmentChangeNameBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() = with(binding) {
        super.onResume()
        initFullnameList()
    }

    private fun initFullnameList() = with(binding) {
        val fullnameList = USER.fullname.split(" ") // split() разделит нашу строку на 2 элемента и запишет это в fullnameList
        if (fullnameList.size > 1) {
            settingsInputName.setText(fullnameList[0])
            settingsInputSurname.setText(fullnameList[1])
        } else settingsInputName.setText(fullnameList[0])
    }

    override fun change(): Unit = with(binding) {
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}