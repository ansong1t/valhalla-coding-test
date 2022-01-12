package com.valhalla.ui.postdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.valhalla.common.FragmentWithBinding
import com.valhalla.inject.DefaultDateTimeFormatter
import com.valhalla.ui.postdetails.databinding.FragmentPostDetailsBinding
import com.valhalla.util.observe
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.FlowPreview
import java.text.SimpleDateFormat
import javax.inject.Inject

@FlowPreview
@AndroidEntryPoint
class PostDetailsFragment :
    FragmentWithBinding<FragmentPostDetailsBinding>() {

    private val viewModel: PostDetailsViewModel by viewModels()

    private var controller: PostDetailsCommentEpoxyController? = null

    @Inject
    @DefaultDateTimeFormatter
    lateinit var dateFormatter: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = PostDetailsCommentEpoxyController(requireContext(), dateFormatter)
    }

    override fun onViewCreated(binding: FragmentPostDetailsBinding, savedInstanceState: Bundle?) {
        binding.rvContent.setController(controller!!)
        binding.toolbar.setNavigationIcon(R.drawable.ic_left_arrow)
        binding.toolbar.setNavigationOnClickListener {
            onBackPress()
        }
        applyViewInsetters()
        viewModel.liveData.observe(viewLifecycleOwner, ::render)
        viewModel.action.observe(viewLifecycleOwner, ::handleAction)
    }

    private fun handleAction(actions: PostDetailsActions) {
        when (actions) {
            else -> {
            }
        }
    }

    private fun applyViewInsetters() {
        val binding = requireBinding()
        binding.toolbar.applyInsetter {
            type(statusBars = true) {
                padding(top = true)
            }
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPostDetailsBinding =
        FragmentPostDetailsBinding.inflate(inflater, container, false)

    private fun render(state: PostDetailsViewState) {
        val binding = requireBinding()
        binding.state = state
        controller?.state = state
    }

    override fun onDestroy() {
        binding?.rvContent?.clear()
        super.onDestroy()
        controller?.clear()
        controller = null
    }
}
