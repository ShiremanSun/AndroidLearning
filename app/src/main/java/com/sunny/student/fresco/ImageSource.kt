package com.sunny.student.fresco

import android.net.Uri
import java.io.File
/**
 * Image Source for LiveImageLoader
 */
abstract class ImageSource {
    /**
     * get Uris
     */
    abstract fun getUris(): List<Uri>

    companion object {
        /**
         * Resource scheme for URIs
         */
        const val LOCAL_RESOURCE_SCHEME = "res"
    }
}

/**
 * Image source from File
 */
class ImageSourceFile(val file: File?) : ImageSource() {
    override fun getUris() = if (file == null) listOf() else listOf(Uri.fromFile(file))
}

/**
 * Image source from Drawable
 */
class ImageSourceDrawable(val resourceId: Int) : ImageSource() {
    override fun getUris() = listOf(
        Uri.Builder()
            .scheme(LOCAL_RESOURCE_SCHEME)
            .path(resourceId.toString())
            .build(),
    )
}

/**
 * Image source from uri
 */
class ImageSourceUri(private val urls: List<String>?) : ImageSource() {
    override fun getUris() = urls?.map { Uri.parse(it) } ?: listOf()
}
