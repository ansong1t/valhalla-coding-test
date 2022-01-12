package com.valhalla.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.valhalla.common.FragmentWithBinding
import com.valhalla.data.entities.post.Post
import com.valhalla.ui.home.databinding.FragmentHomeBinding
import com.valhalla.util.observeIn
import com.valhalla.util.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ObsoleteCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class HomeFragment :
    FragmentWithBinding<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private var controller: HomeEpoxyController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = HomeEpoxyController()
    }

    override fun onViewCreated(
        binding: FragmentHomeBinding,
        savedInstanceState: Bundle?
    ) {
        binding.toolbar.applyInsetter {
            type(statusBars = true) {
                padding(top = true)
            }
        }
        controller?.callbacks = object : HomeEpoxyController.Callbacks {
            override fun onItemClicked(post: Post) {
                viewModel.postClicked(post)
            }
        }
        binding.rvPosts.setController(controller!!)
        viewModel.liveData.observe(viewLifecycleOwner, ::render)

        viewModel.action.observeIn(viewLifecycleOwner, ::handleActions)
    }

    private fun handleActions(action: HomeActions) {
        when (action) {

            is HomeActions.Error -> requireContext().showAlertDialog(
                title = getString(R.string.error),
                body = action.message
            )
            is HomeActions.PostsFetched -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    controller?.submitData(action.items)
                }
            }

            is HomeActions.NavigateToPostDetails -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionToPostDetailsFragment(
                        action.postId
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        binding?.rvPosts?.clear()
        super.onDestroy()
        controller = null
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    private fun render(state: HomeViewState) {
        val binding = requireBinding()
        binding.state = state
    }

    override fun isExitOnBack() = true
}
