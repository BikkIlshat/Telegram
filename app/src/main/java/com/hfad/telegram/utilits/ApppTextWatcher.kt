package com.hfad.telegram.utilits

import android.text.Editable
import android.text.TextWatcher

class ApppTextWatcher(val onSuccess:(Editable?) -> Unit ) : TextWatcher{


    override fun afterTextChanged(s: Editable?) {
        onSuccess(s)
    }


    // переопределённые методы расширения TextWatcher, (пустые в них нет реализаций):

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}