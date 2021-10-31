package com.hfad.telegram.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hfad.telegram.databinding.FragmentChatsBinding

/**
 * ChatsFragment не наследуется от абстрактного класса ViewBindingFragment по причине того, что
 * ChatsFragment должен жить своей жизнью и его поведение не должно быть такое же как и у остальных
 * фрагментов т.к.  - это основной фрагмент.
 */
class ChatsFragment : Fragment() {
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}