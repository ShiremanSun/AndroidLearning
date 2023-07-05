package com.sunny.student.fresco

import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.listener.BaseRequestListener
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_fresco.image
import kotlinx.android.synthetic.main.activity_fresco.load
import kotlinx.android.synthetic.main.activity_fresco.preload

class FrescoActivity : AppCompatActivity() {
    val url = "https://p16-sign-va.tiktokcdn.com/tos-maliva-avt-0068/ccee5e79b0ff2093e4e9c0a19d96a194~c5_100x100.webp?x-expires=1687852800&x-signature=CTyKZpp575hzRwvWm3%2FQG8dExjc%3D"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fresco)
        preload.setOnClickListener {
            Fresco.getImagePipeline().prefetchToBitmapCache(
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    .setRequestListener(object : BaseRequestListener() {
                        override fun onRequestSuccess(request: ImageRequest?, requestId: String?, isPrefetch: Boolean) {
                            preload.post {
                                loadImage()
                            }
                        }

                    }).build(), this
            )
        }
        load?.setOnClickListener {
            loadImage()
        }
    }

    fun loadImage() {
        ImageLoaderFrescoImpl().setUrl(url).setImageLoadListener(object : ImageLoadListener {
            override fun onLoadStarted() {
            }

            override fun onLoadSuccess(imageUrl: String?, width: Int, height: Int) {
                Log.e("onLoadSuccess", "height=$height width=$width")
            }

            override fun onLoadFailed(imageUrl: String?, exception: Exception?) {
            }

            override fun handleAnimatable(animatable: Animatable) {
            }

        }).loadInto(image)
    }
}