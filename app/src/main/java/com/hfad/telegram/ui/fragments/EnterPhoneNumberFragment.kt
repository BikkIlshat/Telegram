package com.hfad.telegram.ui.fragments

import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentEnterPhoneNumberBinding
import com.hfad.telegram.utilits.replaceFragment
import com.hfad.telegram.utilits.showToast


class EnterPhoneNumberFragment :
    ViewBindingFragment<FragmentEnterPhoneNumberBinding>(FragmentEnterPhoneNumberBinding::inflate) {

    override fun onStart() {
        super.onStart()
        binding.registerBtnNext.setOnClickListener { sendCode() }
    }

    private fun sendCode() = with(binding) {
        if (registerInputPhoneNumber.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_enter_phone)) //  сделали её вызов в файле расширений функций funs.kt
        } else {
            replaceFragment(EnterCodeFragment())//  сделали её вызов в файле расширений функций funs.kt
            //  тем самым не нужно городить:
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.dataContainer, EnterCodeFragment())
//                .addToBackStack(null)
//                .commit()
        }
    }
}
