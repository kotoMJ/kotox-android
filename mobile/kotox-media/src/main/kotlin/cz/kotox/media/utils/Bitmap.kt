import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.palette.graphics.Palette
import timber.log.Timber

fun Bitmap.getSignificantColors(): List<Int> {
    val rgbColors = Palette.from(this).generate().let { palette ->

        Timber.d("palette.lightVibrantSwatch: ${palette.lightVibrantSwatch}")
        Timber.d("palette.vibrantSwatch: ${palette.vibrantSwatch}")
        Timber.d("palette.lightMutedSwatch: ${palette.lightMutedSwatch}")
        Timber.d("palette.mutedSwatch: ${palette.mutedSwatch}")
        Timber.d("palette.darkMutedSwatch: ${palette.darkMutedSwatch}")
        Timber.d("palette.darkVibrantSwatch: ${palette.darkVibrantSwatch}")

        //Some swatch might be null. Usually at least 3 swatches per photo are available.
        listOfNotNull(
            palette.lightVibrantSwatch?.rgb,
            palette.vibrantSwatch?.rgb,
            palette.lightMutedSwatch?.rgb,
            palette.mutedSwatch?.rgb,
            palette.darkMutedSwatch?.rgb,
            palette.darkVibrantSwatch?.rgb
        )
    }
    return rgbColors
}


/**
 *  Warning! ImageDecoder.decodeBitmap read EXIF orientation, Media.getBitmap doesn't
 */
fun Uri.getBitmap(
    context: Context,
): Bitmap? {
    try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    this
                )
            )
            /**
             * java.lang.IllegalStateException: unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps
             *
             * By default ImageDecoder.decodeBitmap() returns immutable bitmap.
             * And default allocation for the pixel memory is HARDWARE but may switch
             * to software in case there is a small image or when HARDWARE is incompatible.
             */
            { decoder, info, source ->
                decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                decoder.isMutableRequired = true
            }
        } else {
            MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                this
            )
        }
        return bitmap
    } catch (se: SecurityException) {
        /**
         * There might be an issue during rotation with Permission Denial: opening provider com.android.providers.media.MediaDocumentsProvider
         * when MediaDocumentProvider requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs.
         */
        Timber.w(se, "Problem with getting bitmap.")
        return null
    }
}