package com.valhalla.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.valhalla.common.FragmentWithBinding
import com.valhalla.extensions.openBrowser
import com.valhalla.ui.about.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment :
    FragmentWithBinding<FragmentAboutBinding>() {

    override fun onViewCreated(
        binding: FragmentAboutBinding,
        savedInstanceState: Bundle?
    ) {
        binding.btnGithub.setOnClickListener {
            requireContext().openBrowser(getString(R.string.social_github_url))
        }

        binding.btnLinkedin.setOnClickListener {
            requireContext().openBrowser(getString(R.string.social_linkedin_url))
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false)
}
