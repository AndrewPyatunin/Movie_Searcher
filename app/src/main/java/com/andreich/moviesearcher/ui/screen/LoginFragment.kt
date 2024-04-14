package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    companion object {

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding ?: throw RuntimeException("binding is null!")

    private val component by lazy { (activity?.application as MovieApp).component }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}