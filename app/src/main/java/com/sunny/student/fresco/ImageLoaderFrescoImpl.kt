package com.sunny.student.fresco

import android.graphics.Bitmap
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.interfaces.DraweeHierarchy
import com.facebook.drawee.view.DraweeHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.Postprocessor
import java.io.File

class ImageLoaderFrescoImpl : ILiveImageLoader {
    private val DEFAULT_FADE_DURATION = 300
    private val DEFAULT_BLUR_RADIUS = 5
    private val SDK_VERSION_19 = 19
    private var imageSource: ImageSource? = null
    private var draweeHolder: DraweeHolder<DraweeHierarchy>? = null
    private var initializationTime = 0L
    private var radiusDp: Float? = -1f
    private var width: Int = -1
    private var height: Int = -1
    private var placeholder: Int = -1
    private var placeholderScaleType: ScalingUtils.ScaleType? = null
    private var placeholderDrawable: Drawable? = null
    private var listener: ImageLoadListener? = null
    private var roundAsCircle: Boolean? = null
    private var cornerRadius: Float = -1f
    private var blurScale: Float = -1f
    private var blurRadius: Int = -1
    private var fadeDuration: Int = DEFAULT_FADE_DURATION
    private var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
    private var autoPlayAnimation: Boolean = false
    private var useBmp565: Boolean? = null
    private var disableMemoryCache: Boolean = false
    private var draweeController: DraweeController? = null
    private var ignoreCache = false




    override fun setUrl(url: String?) = apply {
        setImageSource(ImageSourceUri(if (url == null) listOf() else listOf(url)))
    }

    override fun setUrls(urls: List<String>?) = apply {
        setImageSource(ImageSourceUri(urls))
    }

    override fun setUrlByScene(url: String?, scene: String)= apply {
        url?.let {
         val  sceneUrl = it
          return  setImageSource(ImageSourceUri(listOf(sceneUrl)))
        }
        return setImageSource(ImageSourceUri(if (url == null) listOf() else listOf(url)))
    }

    override fun setUrlsByScene(urls: List<String>?, scene: String) = apply {
       var sceneUrls: MutableList<String> = mutableListOf()
        urls?.forEach {
            val url = it
            sceneUrls.add(url)
        }
        setImageSource(ImageSourceUri(sceneUrls?.toList()))
    }

    override fun setFile(file: File?) = apply {
        setImageSource(ImageSourceFile(file))
    }

    override fun setResourceId(resourceId: Int) = apply {
        setImageSource(ImageSourceDrawable(resourceId))
    }

    override fun setSize(width: Int, height: Int) = apply {
        this.width = width
        this.height = height
    }

    override fun setPlaceholder(placeholder: Int) = apply {
        this.placeholder = placeholder
    }

    /*
    * setPlaceholderImageScaleType
    * */
    override fun setPlaceholderImageScaleType(scaleType: ImageView.ScaleType) = apply {
        this.placeholderScaleType = convertToFrescoScaleType(scaleType)
    }

    override fun setImageLoadListener(listener: ImageLoadListener?) = apply {
        this.listener = listener
    }

    override fun setRoundAsCircle(roundAsCircle: Boolean?) = apply {
        this.roundAsCircle = roundAsCircle
    }

    override fun setCornerRadius(radiusDp: Float?) = apply {
        this.radiusDp = radiusDp
        this.cornerRadius = radiusDp ?: -1F
    }


    override fun setBlurSize(blurRadius: Int, blurScale: Float) = apply {
        this.blurScale = blurScale
        this.blurRadius = blurRadius
    }

    override fun setBlurScale(blurScale: Float) = apply {
        this.blurScale = blurScale
        this.blurRadius = DEFAULT_BLUR_RADIUS
    }

    override fun setScaleType(scaleType: ImageView.ScaleType) = apply {
        this.scaleType = scaleType
    }

    override fun setAutoPlayAnimation(autoPlay: Boolean) = apply {
        this.autoPlayAnimation = autoPlay
    }

    override fun setUseBmp565(useBmp565: Boolean?) = apply {
        this.useBmp565 = useBmp565
    }

    override fun disableMemoryCache() = apply {
        this.disableMemoryCache = true
    }

    override fun setFadeDuration(duration: Int) = apply {
        this.fadeDuration = duration
    }

    override fun setPlaceholderDrawable(drawable: Drawable?) = apply {
        this.placeholderDrawable = drawable
    }

//    override fun setDraweeController(draweeController: DraweeController?) = apply {
//        this.draweeController = draweeController
//    }

    override fun setImageSource(imageSource: ImageSource) = apply {
        this.imageSource = imageSource
    }


    override fun ignoreCache() = apply {
        this.ignoreCache = true
    }

    override fun preloadToMemory() {
        createImageRequests(null, maybeCreateBlurProcessor(), false).firstOrNull()?.let {
            ImagePipelineFactory.getInstance().imagePipeline.prefetchToBitmapCache(it, null)
        }
    }

    override fun preloadToDisk() {
        createImageRequests(null, maybeCreateBlurProcessor(), false).firstOrNull()?.let {
            ImagePipelineFactory.getInstance().imagePipeline.prefetchToDiskCache(it, null)
        }
    }

    override fun isCached(view: View?): Boolean {
        if (view == null || view.measuredWidth <= 0 || view.measuredHeight <= 0) {
            return false
        }

        return createImageRequests( ResizeOptions(view.measuredWidth, view.measuredHeight), maybeCreateBlurProcessor(), useBmp565 ?: true).firstOrNull()?.let {
            ImagePipelineFactory.getInstance().imagePipeline.isInBitmapMemoryCache(it)
        } == true
    }

    override fun loadInto(view: View?) {
        if (view == null) {
            listener?.onLoadFailed(imageSource?.getUris()?.get(0).toString(), IllegalArgumentException("imageView is null"))
            return
        }
        if (imageSource == null) {
            listener?.onLoadFailed(imageSource?.getUris()?.get(0).toString(), IllegalArgumentException("imageSource is null"))
            return
        }
        if (imageSource!!.getUris().isEmpty()) {
            listener?.onLoadFailed(null, IllegalArgumentException("imageSource.urls is empty"))
            return
        }

        val bmp565: Boolean = if (useBmp565 != null) {
            useBmp565!!
        } else {
            false
        }

        initializationTime = System.currentTimeMillis()


        val resizeOptions = if (width > 0 && height > 0) {
            ResizeOptions(width, height)
        } else if (view.measuredWidth > 0 && view.measuredHeight > 0) {
            ResizeOptions(view.measuredWidth, view.measuredHeight)
        } else {
            null
        }

        var roundingParams: RoundingParams? = null
        if (view is SimpleDraweeView) {
            roundingParams = view.hierarchy.roundingParams
        }
        if (roundAsCircle != null || cornerRadius > 0) {
            if (roundingParams == null) {
                roundingParams = RoundingParams()
            }
            roundAsCircle?.let { roundingParams?.roundAsCircle = it }
            if (cornerRadius > 0) {
                roundingParams?.setCornersRadius(cornerRadius)
            }
        }
        val mControllerListener = createFrescoListener(listener)
        if (draweeHolder == null) {
            draweeHolder = DraweeHolder.create(null, view.context)
            val draweeHolderDispatcher = DraweeHolderDispatcher()
            val hierarchy: GenericDraweeHierarchy =
                GenericDraweeHierarchyBuilder((view as View).resources).run {
                    setRoundingParams(roundingParams)
                    if (placeholderDrawable != null) {
                        setPlaceholderImage(placeholderDrawable)
                    } else if (placeholder > 0) {
                        setPlaceholderImage(placeholder)
                    }
                    placeholderImageScaleType = placeholderScaleType
                    fadeDuration = getFadeDuration()
                    build()
                }

            //set hierarchy
            draweeHolder?.hierarchy = hierarchy

            val requests: Array<ImageRequest> = createImageRequests(resizeOptions, maybeCreateBlurProcessor(), bmp565, disableMemoryCache)
            if (requests.isEmpty()) {
                return
            }

            //controller
            val oldController = if (draweeHolder?.controller != null) {
                draweeHolder?.controller
            } else {
                if (view is SimpleDraweeView) {
                    view.controller
                } else {
                    null
                }
            }
            val controllerBuilder = Fresco.newDraweeControllerBuilder()
//                .setDataSourceSupplier(retainingSupplier)
                .setAutoPlayAnimations(autoPlayAnimation)
                .setControllerListener(mControllerListener)
                .setOldController(oldController)

                if (requests.size == 1) {
                    controllerBuilder.setImageRequest(requests[0])
                } else {
                    controllerBuilder.setFirstAvailableImageRequests(requests, false)
                }


            //build controller
            val draweeController: DraweeController = controllerBuilder.build()
            //set controller
            draweeHolder?.controller = draweeController

            //if is already attached, call method onViewAttachedToWindow.
            if (isAttachedToWindow(view)) {
                draweeHolderDispatcher.onViewAttachedToWindow(view)
            }
            //add attach state change listener
            view.addOnAttachStateChangeListener(draweeHolderDispatcher)
        } else {
            draweeHolder = draweeHolder as DraweeHolder<DraweeHierarchy>
            //release original resource
            draweeHolder?.onDetach()
            // TODO(wangsiyue.kw): run until here, until the drawable is loaded, create the controller
            val hierarchy: GenericDraweeHierarchy = GenericDraweeHierarchyBuilder(view.resources).run {
                setRoundingParams(roundingParams)
                if (placeholderDrawable != null) {
                    setPlaceholderImage(placeholderDrawable)
                } else if (placeholder > 0) {
                    setPlaceholderImage(placeholder)
                }
                placeholderImageScaleType = placeholderScaleType
                fadeDuration = getFadeDuration()
                build()
            }

            //set hierarchy
            draweeHolder?.hierarchy = hierarchy
            val requests: Array<ImageRequest> = createImageRequests(resizeOptions, maybeCreateBlurProcessor(), bmp565)
            if (requests.isEmpty()) {
                return
            }

            val oldController = if (draweeHolder?.controller != null) {
                draweeHolder?.controller
            } else {
                if (view is SimpleDraweeView) {
                    view.controller
                } else {
                    null
                }
            }
            //controller
            val controllerBuilder = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(autoPlayAnimation)
                .setControllerListener(mControllerListener)
                .setOldController(oldController)

                if (requests.size == 1) {
                    controllerBuilder.setImageRequest(requests[0])
                } else {
                    controllerBuilder.setFirstAvailableImageRequests(requests, false)
                }


            //build controller
            val draweeController: DraweeController = controllerBuilder.build()
            //set controller
            draweeHolder?.controller = draweeController
            draweeHolder?.onAttach()
        }

        //set image drawable or background
        if (view is ImageView) {
            view.setImageDrawable(draweeHolder?.topLevelDrawable)
        } else {
            view.setBackground(draweeHolder?.topLevelDrawable)
        }
    }


    private fun isAttachedToWindow(view: View): Boolean {
        return if (Build.VERSION.SDK_INT >= SDK_VERSION_19) {
            view.isAttachedToWindow
        } else {
            view.windowToken != null
        }
    }

    private fun maybeCreateBlurProcessor(): Postprocessor? {
        return null
    }

    private fun createImageRequests(
        options: ResizeOptions?,
        postprocessor: Postprocessor?,
        useBmp565: Boolean,
        disableMemoryCache: Boolean = false
    ): Array<ImageRequest> {
        val uriList = imageSource?.getUris() ?: listOf()
        if (uriList.isEmpty()) {
            return arrayOf()
        }
        val imageRequests: ArrayList<ImageRequest> = ArrayList()
        for (uri in uriList) {
            val builder = ImageRequestBuilder.newBuilderWithSource(uri)
            if (disableMemoryCache) {
                builder.disableMemoryCache()
            }
            if (null != postprocessor) {
                builder.postprocessor = postprocessor
            }
            if (null != options) {
                builder.resizeOptions = options
            }
            if (useBmp565) {
                val decodeOptionsBuilder = ImageDecodeOptions.newBuilder()
                decodeOptionsBuilder.bitmapConfig = Bitmap.Config.RGB_565
                builder.imageDecodeOptions = decodeOptionsBuilder.build()
            }
            if (ignoreCache) {
                builder.lowestPermittedRequestLevel = ImageRequest.RequestLevel.FULL_FETCH

            }
            val imageRequest = builder.build()
            imageRequests.add(imageRequest)
        }
        if (imageRequests.size == 0) {
            return arrayOf()
        }
        return arrayOf(imageRequests[0])
//        val requests = arrayOfNulls<ImageRequest>(imageRequests.size)
//        return imageRequests.toArray(requests)
    }

    private inner class DraweeHolderDispatcher : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
            draweeHolder?.onAttach()
        }

        override fun onViewDetachedFromWindow(v: View) {
            draweeHolder?.onDetach()
        }
    }


    private fun convertToFrescoScaleType(scaleType: ImageView.ScaleType?): ScalingUtils.ScaleType {
        return when (scaleType) {
            ImageView.ScaleType.CENTER -> ScalingUtils.ScaleType.CENTER
            ImageView.ScaleType.CENTER_CROP -> ScalingUtils.ScaleType.CENTER_CROP
            ImageView.ScaleType.CENTER_INSIDE -> ScalingUtils.ScaleType.CENTER_INSIDE
            ImageView.ScaleType.FIT_CENTER -> ScalingUtils.ScaleType.FIT_CENTER
            ImageView.ScaleType.FIT_START -> ScalingUtils.ScaleType.FIT_START
            ImageView.ScaleType.FIT_END -> ScalingUtils.ScaleType.FIT_END
            ImageView.ScaleType.FIT_XY -> ScalingUtils.ScaleType.FIT_XY
            ImageView.ScaleType.MATRIX ->                 //NOTE this case
                //you should set FocusPoint to make sentence
                ScalingUtils.ScaleType.FOCUS_CROP
            else -> GenericDraweeHierarchyBuilder.DEFAULT_ACTUAL_IMAGE_SCALE_TYPE
        }
    }

    private fun convertToFrescoScaleType(scaleTypeInt: Int): ScalingUtils.ScaleType {
        return when (scaleTypeInt) {
            0 -> ScalingUtils.ScaleType.FIT_XY
            1 -> ScalingUtils.ScaleType.FIT_START
            2 -> ScalingUtils.ScaleType.FIT_CENTER
            3 -> ScalingUtils.ScaleType.FIT_END
            4 -> ScalingUtils.ScaleType.CENTER
            5 -> ScalingUtils.ScaleType.CENTER_INSIDE
            6 -> ScalingUtils.ScaleType.CENTER_CROP
            7 -> ScalingUtils.ScaleType.FOCUS_CROP
            8 -> ScalingUtils.ScaleType.FIT_BOTTOM_START
            else -> ScalingUtils.ScaleType.FIT_XY
        }
    }

    private fun createFrescoListener(imageLoadListener: ImageLoadListener?): ControllerListener<ImageInfo?>? {
        return object: BaseControllerListener<ImageInfo?>() {
            private var requestStartTime = 0L

            override fun onSubmit(id: String?, callerContext: Any?) {
                super.onSubmit(id, callerContext)
                requestStartTime = System.currentTimeMillis()
                imageLoadListener?.onLoadStarted()
            }

            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                imageInfo?.let {
                    imageLoadListener?.onLoadSuccess(imageSource?.getUris()?.get(0).toString(), imageInfo.width, imageInfo.height)
                }
                animatable?.let { imageLoadListener?.handleAnimatable(it) }
            }

            override fun onIntermediateImageFailed(id: String?, throwable: Throwable?) {
                super.onIntermediateImageFailed(id, throwable)
                imageLoadListener?.onLoadFailed(imageSource?.getUris()?.get(0).toString(), if (throwable != null) RuntimeException(throwable) else null)
            }

            override fun onFailure(id: String?, throwable: Throwable?) {
                super.onFailure(id, throwable)
                imageLoadListener?.onLoadFailed(imageSource?.getUris()?.get(0).toString(), if (throwable != null) RuntimeException(throwable) else null)
            }
        }
    }
}