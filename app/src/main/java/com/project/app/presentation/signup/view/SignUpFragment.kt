package com.project.app.presentation.signup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.app.R
import com.project.app.base.BaseFragment
import com.project.app.databinding.FragmentLoginBinding
import com.project.app.databinding.FragmentSignupBinding
import com.project.app.helpers.ToolbarType

class SignUpFragment : BaseFragment<FragmentSignupBinding>() {

    override fun createScreenStyles() {
        showToolbar(ToolbarType.BACKONLY.value, null, getString(R.string.register))
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignupBinding
            = FragmentSignupBinding::inflate

    override fun onBackPressed(): Boolean {
        findNavController().navigateUp()
        return true
    }

}