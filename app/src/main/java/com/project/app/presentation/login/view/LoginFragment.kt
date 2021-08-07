package com.project.app.presentation.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.app.R
import com.project.app.base.BaseFragment
import com.project.app.databinding.FragmentLoginBinding
import com.project.app.helpers.ToolbarType

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
            = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSignUp.setOnClickListener {
            goToFragmentAction(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun createScreenStyles() {
        showToolbar(ToolbarType.NONE.value, null, null)
    }

}