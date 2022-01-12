package com.valhalla.ui.postdetails

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.valhalla.common.layout.infiniteLoading
import com.valhalla.common.layout.separatorThin
import com.valhalla.common.layout.subheadline
import com.valhalla.common.layout.vertSpacerMedium
import com.valhalla.common.layout.vertSpacerXlarge
import com.valhalla.extensions.observable
import java.text.SimpleDateFormat

class PostDetailsCommentEpoxyController(
    private var context: Context?,
    private val formatter: SimpleDateFormat
) : EpoxyController() {
    var state: PostDetailsViewState by observable(PostDetailsViewState(), ::requestModelBuild)

    override fun buildModels() {
        vertSpacerXlarge {
            id("spacer_top")
        }
        if (state.postLoading) {
            infiniteLoading {
                id("loading_post")
            }
            // don't show comments when post is not yet loaded
            return
        } else state.post.let { post ->
            postDetails {
                id("post_${post.id}")
                title(post.title)
                body(post.body)
            }
            vertSpacerMedium {
                id("spacer_post")
            }
            separatorThin {
                id("separator_post")
            }
            vertSpacerMedium {
                id("spacer_post2")
            }
        }

        if (state.commentsLoading) {
            infiniteLoading {
                id("loading_comments")
            }
            return
        }

        subheadline {
            id("comment_headline")
            text(this@PostDetailsCommentEpoxyController.context?.getString(R.string.comments))
        }

        vertSpacerMedium {
            id("spacer_post2")
        }
        state.comments.forEach {
            comment {
                id("comment_${it.id}")
                title(it.name)
                body(it.body)
                commentator(it.email)
            }
        }
    }

    fun clear() {
        context = null
    }
}
