package com.example.abschlussprojekt_husewok.utils

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

/**
 * Helper object for displaying Motion Toasts with different styles.
 */
object MotionToasts {
    /**
     * Displays a success toast with the specified title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param activity The activity in which the toast should be displayed.
     * @param context The context used for retrieving resources.
     */
    fun success(title: String, message: String, activity: Activity, context: Context) {
        MotionToast.createColorToast(
            activity,
            title,
            message,
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
        )
    }

    /**
     * Displays an error toast with the specified title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param activity The activity in which the toast should be displayed.
     * @param context The context used for retrieving resources.
     */
    fun error(title: String, message: String, activity: Activity, context: Context) {
        MotionToast.createColorToast(
            activity,
            title,
            message,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
        )
    }

    /**
     * Displays a warning toast with the specified title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param activity The activity in which the toast should be displayed.
     * @param context The context used for retrieving resources.
     */
    fun warning(title: String, message: String, activity: Activity, context: Context) {
        MotionToast.createColorToast(
            activity,
            title,
            message,
            MotionToastStyle.WARNING,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
        )
    }

    /**
     * Displays an info toast with the specified title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param activity The activity in which the toast should be displayed.
     * @param context The context used for retrieving resources.
     */
    fun info(title: String, message: String, activity: Activity, context: Context) {
        MotionToast.createColorToast(
            activity,
            title,
            message,
            MotionToastStyle.INFO,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
        )
    }

    /**
     * Displays a delete toast with the specified title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param activity The activity in which the toast should be displayed.
     * @param context The context used for retrieving resources.
     */
    fun delete(title: String, message: String, activity: Activity, context: Context) {
        MotionToast.createColorToast(
            activity,
            title,
            message,
            MotionToastStyle.DELETE,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
        )
    }
}