package com.hfad.telegram.utilits

enum class AppStates(val state: String) {
    ONLINE(" вести"),
    OFFLINE("был недавно"),
    TYPING("печатает");

    companion object {
        fun updateState(appStates: AppStates) {
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATE)
                .setValue(appStates.state) // передали объект appStates а у этого объекта есть состояния ONLINE(" вести"),OFFLINE("был недавно"), TYPING("печатает")
                .addOnSuccessListener { USER.state = appStates.state } // обновили AppStates
                .addOnFailureListener { showToast(it.message.toString()) }
        }
    }
}