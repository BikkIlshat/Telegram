package com.hfad.telegram.ui.fragments

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.hfad.telegram.MainActivity
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.FragmentEnterCodeBinding
import com.hfad.telegram.utilits.AUTH
import com.hfad.telegram.utilits.ApppTextWatcher
import com.hfad.telegram.utilits.replaceActivity
import com.hfad.telegram.utilits.showToast


class EnterCodeFragment(val phoneNumber: String, val id: String) :
    ViewBindingFragment<FragmentEnterCodeBinding>(FragmentEnterCodeBinding::inflate) {



    override fun onStart() = with(binding) {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        // сделали апгрейд вызова данного метода  через лямбду в классе ApppTextWatcher:
        registerInputCode.addTextChangedListener(ApppTextWatcher {
            val string = registerInputCode.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() = with(binding) {
        val code = registerInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code) // что бы произвести авторизацию  или создать нового пользователя мф должны получить объект credential
        AUTH.signInWithCredential(credential).addOnCompleteListener { task -> // вешаем слушателя, что всё у нас хорошо
            if (task.isSuccessful) { // проверяем что задача (task) выполнена
                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }
}