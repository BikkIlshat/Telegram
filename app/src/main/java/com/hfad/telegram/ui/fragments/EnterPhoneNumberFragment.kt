package com.hfad.telegram.ui.fragments

import android.widget.Toast
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentEnterPhoneNumberBinding


class EnterPhoneNumberFragment :
    ViewBindingFragment<FragmentEnterPhoneNumberBinding>(FragmentEnterPhoneNumberBinding::inflate) {

    override fun onStart() {
        super.onStart()
        binding.registerBtnNext.setOnClickListener { sendCode() }
    }

    private fun sendCode() = with(binding) {
        if (registerInputPhoneNumber.text.toString().isEmpty()) {
            Toast.makeText(
                activity,
                getString(R.string.register_toast_enter_phone),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.registerDataContainer, EnterCodeFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
