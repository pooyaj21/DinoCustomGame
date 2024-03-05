package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

@SuppressLint("ViewConstructor")
class Obstacle(context: Context, screenWidth: Int, screenHeight: Int) :
    AppCompatImageView(context) {

    init {
        layoutParams = FrameLayout.LayoutParams(90, 90)
        x = screenWidth.toFloat()
        y = screenHeight / 2F
    }
}