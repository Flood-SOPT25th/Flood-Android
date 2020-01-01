package com.flood_android.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.Nullable
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget


class GifDrawableImageViewTarget : ImageViewTarget<Drawable> {

    private var mLoopCount = GifDrawable.LOOP_FOREVER

    constructor(view: ImageView, loopCount: Int) : super(view) {
        mLoopCount = loopCount
    }

    constructor(view: ImageView, loopCount: Int, waitForLayout: Boolean) : super(view, waitForLayout) {
        mLoopCount = loopCount
    }

    override fun setResource(@Nullable resource: Drawable?) {
        if (resource is GifDrawable) {
            resource.setLoopCount(mLoopCount)
        }
        view.setImageDrawable(resource)
    }
}