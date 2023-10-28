package cz.kotox.feature.task.poc.detail.ui

import android.content.Context
import android.content.Intent

object TaskDetailActivityIntentUtil {

    fun getCameraStartIntent(
        context: Context,
        taskId: String
    ): Intent {
        val clazz = TaskDetailActivity::class.java
        return Intent(context.applicationContext, clazz).apply {
            putExtra(ARG_TASK_ID, taskId)
        }
    }

}