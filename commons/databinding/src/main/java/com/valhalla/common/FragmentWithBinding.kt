package com.valhalla.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class FragmentWithBinding<V : ViewDataBinding> : Fragment() {
    var binding: V? = null
        private set

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createBinding(inflater, container, savedInstanceState).also { binding = it }.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                if (isExitOnBack()) {
                    // Exit app.
                    requireActivity().finishAffinity()
                } else {
                    onBackPress()
                }
            }

        onViewCreated(requireBinding(), savedInstanceState)
    }

    abstract fun onViewCreated(binding: V, savedInstanceState: Bundle?)

    protected fun requireBinding(): V = requireNotNull(binding)

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): V

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * Set to `true` when we want to exit app when user taps back or navigates up.
     * Usually used on auth screens before reaching home screen. Default is `false`.
     */
    open fun isExitOnBack(): Boolean = false

    open fun onBackPress() {
        findNavController().navigateUp()
    }
}
