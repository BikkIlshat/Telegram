package com.hfad.telegram.ui.fragments

import android.widget.Toast
import com.hfad.telegram.databinding.FragmentEnterCodeBinding
import com.hfad.telegram.utilits.ApppTextWatcher
import com.hfad.telegram.utilits.showToast


class EnterCodeFragment :
    ViewBindingFragment<FragmentEnterCodeBinding>(FragmentEnterCodeBinding::inflate) {

    override fun onStart() = with(binding) {
        super.onStart()
        // сделали апгрейд вызова данного метода  через лямбду в классе ApppTextWatcher:
        registerInputCode.addTextChangedListener(ApppTextWatcher {
            val string = registerInputCode.text.toString()
            if (string.length == 6) {
                verifiCode()
            }
        })
    }

    private fun verifiCode() {
        showToast("ок") // сделали её вызов в файле расширений функций funs.kt
    }
}