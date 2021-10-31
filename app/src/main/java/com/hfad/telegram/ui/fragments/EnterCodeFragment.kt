package com.hfad.telegram.ui.fragments

import com.google.firebase.auth.PhoneAuthProvider
import com.hfad.telegram.MainActivity
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.FragmentEnterCodeBinding
import com.hfad.telegram.utilits.*


class EnterCodeFragment(val phoneNumber: String, val id: String) :
    ViewBindingFragment<FragmentEnterCodeBinding>(FragmentEnterCodeBinding::inflate) {


    override fun onStart() = with(binding) {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        // сделали апгрейд вызова данного метода  через лямбду в классе ApppTextWatcher:
        registerInputCode.addTextChangedListener(AppTextWatcher {
            val string = registerInputCode.text.toString()
            if (string.length >= 5) {
                enterCode()
            }
        })
    }

    private fun enterCode() = with(binding) {
        val code = registerInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(
            id,
            code
        ) // что бы произвести авторизацию  или создать нового пользователя мы должны получить объект credential
        AUTH.signInWithCredential(credential)
            .addOnCompleteListener { task -> // вешаем слушателя, что всё у нас хорошо
                if (task.isSuccessful) { // проверяем что задача (task) выполнена
                    val uid = AUTH.currentUser?.uid.toString()
                    val dateMap = mutableMapOf<String, Any>()
                    dateMap[CHILD_ID] = uid
                    dateMap[CHILD_PHONE] = phoneNumber
                    dateMap[CHILD_USERNAME] = uid

                    REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                showToast("Добро пожаловать")
                                (activity as RegisterActivity).replaceActivity(MainActivity())
                            } else showToast(task2.exception?.message.toString())
                        }
                } else showToast(task.exception?.message.toString())
            }
    }
}