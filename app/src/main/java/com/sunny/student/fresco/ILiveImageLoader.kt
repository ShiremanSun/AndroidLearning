package com.sunny.student.fresco

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import java.io.File

interface ILiveImageLoader {
    /** Sets the image source to load from. There should only be called once. If this
     *  is called multiple times, the last image source overwrite the value.
     */
    fun setImageSource(imageSource: ImageSource): ILiveImageLoader


    fun setUrl(url: String?): ILiveImageLoader

    fun setUrls(urls: List<String>?): ILiveImageLoader

    /**
     * url add scene tag
     */
    fun setUrlByScene(url: String?, scene: String): ILiveImageLoader

    /**
     * urls add scene tag
     */
    fun setUrlsByScene(urls: List<String>?, scene: String): ILiveImageLoader

    fun setFile(urls: File?): ILiveImageLoader

    fun setResourceId(resourceId: Int): ILiveImageLoader

    fun setSize(width: Int, height: Int): ILiveImageLoader

    fun setPlaceholder(placeholder: Int): ILiveImageLoader

    /*
    * setPlaceholderImageScaleType
    * */
    fun setPlaceholderImageScaleType(scaleType: ImageView.ScaleType): ILiveImageLoader

    fun setImageLoadListener(listener: ImageLoadListener?): ILiveImageLoader

    fun setRoundAsCircle(roundAsCircle: Boolean?): ILiveImageLoader

    fun setCornerRadius(radiusDp: Float?): ILiveImageLoader


    fun setBlurSize(blurRadius: Int, blurScale: Float): ILiveImageLoader

    fun setBlurScale(blurScale: Float): ILiveImageLoader

    fun setScaleType(scaleType: ImageView.ScaleType): ILiveImageLoader

    fun setAutoPlayAnimation(autoPlay: Boolean): ILiveImageLoader

    fun setUseBmp565(useBmp565: Boolean?): ILiveImageLoader

    // disable memory cache.
    fun disableMemoryCache(): ILiveImageLoader

    fun setFadeDuration(duration: Int): ILiveImageLoader

    fun setPlaceholderDrawable(drawable: Drawable?): ILiveImageLoader


    // Sets a flag to tell fresco to ignore cache and make full fetch.
    fun ignoreCache(): ILiveImageLoader

    // Preloads the image to memory. This does not guarantee the image will be preloaded successfully.
    fun preloadToMemory()

    // Preloads the image to disk. This does not guarantee the image will be preloaded successfully.
    fun preloadToDisk()

    //Determine if the image is being cached
    fun isCached(view: View?): Boolean

    fun loadInto(view: View?)
}

