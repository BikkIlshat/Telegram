package com.hfad.telegram.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.hfad.telegram.MainActivity
import com.hfad.telegram.R
import com.hfad.telegram.databinding.FragmentChangeNameBinding
import com.hfad.telegram.utilits.*

/**
 * ChangeNameFragment не наследуется от абстрактного класса ViewBindingFragment по причине того, что
 * ChangeNameFragment должен жить своей жизнью и его поведение не должно быть такое же как и у остальных
 * фрагментов.
 */

class ChangeNameFragment : Fragment() {

    private var _binding: FragmentChangeNameBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       // if (activity is MainActivity)
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_confirm_change -> changeName()
        }
        return true
    }

    private fun changeName() = with(binding) {
        val name = settingsInputName.text.toString()
        val surname = settingsInputSurname.text.toString()
        if(name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener { // повесили слушателя что всё хорошо
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.fullname = fullname
                        parentFragmentManager.popBackStack()
                    }
                }

        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}