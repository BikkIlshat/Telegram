package com.hfad.telegram.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentChangeBioBinding
import com.hfad.telegram.utilits.*

class ChangeBioFragment : BaseChangeFragment() {

    private var _binding: FragmentChangeBioBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeBioBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() = with(binding) {
        super.onResume()
        settingsInputBio.setText(USER.bio)

    }

    override fun change(): Unit = with(binding) {
        super.change()
        val newBio = settingsInputBio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    USER.bio = newBio
                    parentFragmentManager.popBackStack()
                }
            }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }
}