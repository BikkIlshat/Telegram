package com.hfad.telegram.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentEnterCodeBinding


class EnterCodeFragment :
    ViewBindingFragment<FragmentEnterCodeBinding>(FragmentEnterCodeBinding::inflate) {

    override fun onStart() = with(binding) {
        super.onStart()
        registerInputCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                val string = registerInputCode.text.toString()
                if (string.length == 6) {
                    verifiCode()
                }
            }
        })
    }
    private fun verifiCode() {
        Toast.makeText(activity, "ok", Toast.LENGTH_SHORT).show()
    }
}