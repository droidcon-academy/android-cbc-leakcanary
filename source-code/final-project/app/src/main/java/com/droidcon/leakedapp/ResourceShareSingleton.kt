@file:Suppress("StaticFieldLeak")

package com.droidcon.leakedapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap


object ResourceShareSingleton {

    private var context: Context? = null

    val grayScaledImage: MutableState<Bitmap?> = mutableStateOf(null)

    fun init(context: Context) {
        this.context = context
    }

    fun setImage(@DrawableRes id: Int) {
        if (context == null) {
            throw IllegalStateException("Context must be initialized before calling setImage")
        }
        val imageDrawable = context!!.getDrawable(id)!!

        val imageBitmap = imageDrawable.toBitmap()

        val canvas = Canvas(imageBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0F)
        paint.setColorFilter(ColorMatrixColorFilter(colorMatrix))
        canvas.drawBitmap(imageBitmap, 0F, 0F, paint)

        grayScaledImage.value = imageBitmap

        Toast.makeText(context, "Image loaded", Toast.LENGTH_SHORT).show()
    }

    fun clear() {
        context = null
    }

}