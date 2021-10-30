package com.hfad.telegram.ui.fragments

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.hfad.telegram.MainActivity
import com.hfad.telegram.R
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.FragmentEnterPhoneNumberBinding
import com.hfad.telegram.utilits.AUTH
import com.hfad.telegram.utilits.replaceActivity
import com.hfad.telegram.utilits.replaceFragment
import com.hfad.telegram.utilits.showToast
import java.util.concurrent.TimeUnit


class EnterPhoneNumberFragment :
    ViewBindingFragment<FragmentEnterPhoneNumberBinding>(FragmentEnterPhoneNumberBinding::inflate) {

    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onStart() {
        super.onStart()
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) { // метод проверяет если верификация правильная то он запускается credential - позволяет произвести авторизацию, и созданию нового пользователя
                AUTH.signInWithCredential(credential)
                    .addOnCompleteListener { task -> // вешаем слушателя, что всё у нас хорошо
                        if (task.isSuccessful) { // проверяем что задача (task) выполнена
                            showToast("Добро пожаловать")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else {
                            showToast(task.exception?.message.toString())
                        }
                    }
            }

            override fun onVerificationFailed(p0: FirebaseException) { // запускается когда проблема с верификацией или еще с чем нибудь
                showToast(p0.message.toString())
            }

            override fun onCodeSent(
                id: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) { // метод запускается тогда когда было отправлено смс
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }
        binding.registerBtnNext.setOnClickListener { sendCode() }
    }

    private fun sendCode() = with(binding) {
        if (registerInputPhoneNumber.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_enter_phone)) //  сделали её вызов в файле расширений функций funs.kt
        } else {
            authUser()
//            replaceFragment(EnterCodeFragment())//  сделали её вызов в файле расширений функций funs.kt
            //  тем самым не нужно городить:
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.dataContainer, EnterCodeFragment())
//                .addToBackStack(null)
//                .commit()
        }
    }

    // Здесь получаем наш номер телефона
    private fun authUser() = with(binding) {
        mPhoneNumber = registerInputPhoneNumber.text.toString()
        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions
                .newBuilder(FirebaseAuth.getInstance())
                .setActivity(activity as RegisterActivity) // передаём activity как RegisterActivity (привидение типов)
                .setPhoneNumber(mPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallback)
                .build()
        )
    }
}
