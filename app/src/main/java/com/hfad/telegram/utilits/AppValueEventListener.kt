package com.hfad.telegram.utilits

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


/**
 * App - все модернизированные классы в нашем проекте  начинаются с App
 */

class AppValueEventListener(val onSuccess: (DataSnapshot) -> Unit) : ValueEventListener {
    override fun onCancelled(error: DatabaseError) {
    }

    /*
этот onDataChange этого слушателя (addListenerForSingleValueEvent)  сработает только 1 раз при запуске (заполнит данные больше ничего делать не будет)
но если укажем другого слушателя addValueEventListener - этот слушатель будет  постоянно слушать изменение в базе данных и если есть какие-то изменения в базе данных
он постоянно будет обновлять нашего user
 */

    override fun onDataChange(snapshot: DataSnapshot) {
        onSuccess(snapshot)
    }

}