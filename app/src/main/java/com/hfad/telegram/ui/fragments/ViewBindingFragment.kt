package com.hfad.telegram.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.hfad.telegram.MainActivity

abstract class ViewBindingFragment<T : ViewBinding>(
    private val inflateBinding: (
        inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean
    ) -> T
) : Fragment() {
    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding.invoke(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        if (activity is MainActivity)
            (activity as MainActivity).mAppDrawer.disableDrawer() // как только запускается базовый фрагмент т.е. любой фрагмент отличный от ChatsFragment то у нас запустится этот код и соответственно отключит наш драйвер
    }

    override fun onStop() {
        super.onStop()
        if (activity is MainActivity)
            (activity as MainActivity).mAppDrawer.enableDrawer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
