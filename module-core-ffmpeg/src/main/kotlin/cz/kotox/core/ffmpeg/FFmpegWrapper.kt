package cz.kotox.core.ffmpeg

import android.media.MediaMetadataRetriever
import android.util.Log
import com.arthenica.mobileffmpeg.BuildConfig
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.arthenica.mobileffmpeg.Level
import com.opkix.base.ffmpeg.getConcatFilterScaleCommand
import com.opkix.base.ffmpeg.model.BackgroundMusicItem
import com.opkix.base.ffmpeg.model.CanvasItem
import com.opkix.base.ffmpeg.model.VideoCompositionItem
import com.opkix.base.ffmpeg.model.VideoDurationItem
import com.opkix.base.ffmpeg.model.VideoOutputItem
import cz.kotox.core.media.VideoUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.abs

class FFmpegException(val errorCode: Int) : RuntimeException()

sealed class FFmpegResponse {
	class Failure(val exception: RuntimeException) : FFmpegResponse()
	class Error(val exception: FFmpegException) : FFmpegResponse()
	object Success : FFmpegResponse()
	object Cancelled : FFmpegResponse()
	object AlreadyRunning : FFmpegResponse()
}

/**
 * Wrapper around com.arthenica.mobileffmpeg.FFmpeg implementation.
 * Use it as Singleton in the App to be able check state of running commands!
 */
class FFmpegWrapper() {

	//TODO use this via config
	val logs: Boolean = BuildConfig.DEBUG


	private var commandRunning: Boolean = false

	init {
		Config.setLogLevel(if (logs) Level.AV_LOG_ERROR else Level.AV_LOG_QUIET)
	}

	fun isRunning() = commandRunning

	suspend fun terminateRunningOperation() {
		withContext(Dispatchers.IO) {
			FFmpeg.cancel()
		}
	}

	suspend fun requestVideoRotation(videoPath: String, job: Job? = null): FFmpegResponse {
		if (commandRunning) return FFmpegResponse.AlreadyRunning //Prevent wasting of time with data preparation when ffmpeg is already running.

		return withContext(job?.let { Dispatchers.IO + it } ?: Dispatchers.IO) {
			check(videoPath.isNotBlank())

			// get current rotation
			val currentRotation = MediaMetadataRetriever().apply { setDataSource(videoPath) }.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION).toInt()

			// If you want to set 90° to metadata, you have to set 270° to ffmpeg and vice versa. 0° and 180° work correctly.
			val newRotation = when (currentRotation) {
				0 -> 90
				90 -> 0
				180 -> 270
				270 -> 180
				else -> 0
			}

			val fileType = videoPath.substringAfterLast(".")
			val rotatedPath = videoPath.replace(".$fileType", "_rotated.$fileType")

			val rotationCommandArray = arrayOf(
				"-y",
				"-i",
				videoPath,
				"-map_metadata",
				"0",
				"-metadata:s:v",
				"rotate=$newRotation",
				"-codec",
				"copy",
				rotatedPath
			)

			val result = executeFFmpegCommand(rotationCommandArray)

			if (result is FFmpegResponse.Success) {
				// remove original file and rename rotated one

				val originalFile = File(videoPath)
				val rotatedFile = File(rotatedPath)

				originalFile.delete()
				rotatedFile.renameTo(File(videoPath))
			}

			result
		}
	}

	/**
	 * https://trac.ffmpeg.org/wiki/Concatenate
	 */
	suspend fun requestVideoComposition(
		inputItemsList: List<VideoCompositionItem>,
		inputBackgroundMusic: BackgroundMusicItem?,
		outputItem: VideoOutputItem,
		progressCallback: (Float) -> Unit,
		job: Job? = null
	): FFmpegResponse {
		if (commandRunning) return FFmpegResponse.AlreadyRunning //Prevent wasting of time with data preparation when ffmpeg is already running.
		return withContext(job?.let { Dispatchers.IO + it } ?: Dispatchers.IO) {
			try {
				//val inputItemsList = inputItemsListIn.map { it.copy(startTimeInMillis = 15_000, endTimeInMillis = 25_000) } //Just for dev tracing purpose
				val commandArray = getConcatFilterScaleCommand(inputItemsList, inputBackgroundMusic, outputItem.path, CanvasItem(outputItem.width, outputItem.height))
				//Log.e(">>>:", commandArray.joinToString(separator = " "))
				executeFFmpegCommand(
					commandArray,
					progressCallback,
					inputItemsList.map { VideoDurationItem(it.videoPath, it.speedMultiplier, it.startTimeInMillis, it.endTimeInMillis) }.toTypedArray()
				)
			} catch (exception: RuntimeException) {
				FFmpegResponse.Failure(exception)
			}
		}
	}

	private fun getVideoDuration(videoPath: Array<VideoDurationItem>): Float {
		return videoPath.map {
			check(it.speedMultiplier != 0f) { "Speed multiplier must NOT be zero!" }
			val videoTime = if (it.endTimeInMillis != null && it.startTimeInMillis != null) {
				abs(it.endTimeInMillis - it.startTimeInMillis)
			} else {
				VideoUtils.getVideoDuration(it.path)
			}
			videoTime / it.speedMultiplier
		}.reduce { totalTime, oneItemTime -> totalTime + oneItemTime }
	}

	private fun executeFFmpegCommand(
		ffmpegCommand: Array<String>,
		progress: ((Float) -> Unit)? = null,
		durationArray: Array<VideoDurationItem> = emptyArray()
	): FFmpegResponse {
		if (commandRunning) return FFmpegResponse.AlreadyRunning //Parallel execution is not supported
		synchronized(commandRunning) {
			commandRunning = true

			Config.resetStatistics()

			if (progress != null) {
				val videoDuration = getVideoDuration(durationArray)
				Config.enableStatisticsCallback {
					if (commandRunning) progress.invoke(it.time.toFloat() / videoDuration)
				}
			}
			Log.e("FFMPEG:", ffmpegCommand.joinToString(separator = " "))
			val returnCode = FFmpeg.execute(ffmpegCommand)
			commandRunning = false

			Config.enableStatisticsCallback(null)

			return when {
				returnCode == 0 -> FFmpegResponse.Success
				returnCode == 255 -> FFmpegResponse.Cancelled
				returnCode < 0 -> FFmpegResponse.Error(FFmpegException(returnCode))
				else -> FFmpegResponse.Failure(IllegalStateException("Unexpected result code [$returnCode] from ffmpeg execution of command: ${ffmpegCommand.joinToString(separator = " ")}"))
			}
		}
	}
}

