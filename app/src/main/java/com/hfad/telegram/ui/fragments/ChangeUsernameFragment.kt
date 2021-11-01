package com.hfad.telegram.ui.fragments

import android.os.Bundle
import android.view.*
import com.hfad.telegram.MainActivity
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentChangeUsernameBinding
import com.hfad.telegram.utilits.*
import java.util.*

/**
 * Изменение username, проверка на уникальность в Firebase.
 * создали новую  NODE_USERNAMES в REF_DATABASE_ROOT  в которой храним все Usernames и каждый Usernames ссылается на id
 * Реализовали в классе  ChangeUsernameFragment возможность смены логина с проверкой на уникальность вводимого логина,
 * если такой логин уже существует выводим showToast("Такой пользователь уже существует") если нет showToast(getString(R.string.toast_data_update))
 *
 */
class ChangeUsernameFragment : BaseChangeFragment() {

    private var _binding: FragmentChangeUsernameBinding? = null
    private val binding get() = _binding!!

    lateinit var mNewUsername: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeUsernameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() = with(binding) {
        super.onResume()
        settingsInputUsername.setText(USER.username) // подтянули строе username в графу
    }

    /*
    начинаем процедуру изменение Usernames
     */
    override fun change() = with(binding) {
        mNewUsername = settingsInputUsername.text.toString()
            .lowercase(Locale.getDefault()) // проинициализировали mNewUsername (lowercase - текст всегда строчный в Firebase Realtime Database)
        if (mNewUsername.isEmpty()) {
            showToast("Поле пустое")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) {
                        showToast("Такой пользователь уже существует")
                    } else {
                        changeUsername()
                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT // Realtime Database root - > telegram-f39eb
            .child(NODE_USERNAMES)
            .child(mNewUsername)
            .setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    deleteOldUserName()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }

    }

    private fun deleteOldUserName() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    parentFragmentManager.popBackStack() // пройти по стеку назад
                    USER.username = mNewUsername // обнавляем нашу модель
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
