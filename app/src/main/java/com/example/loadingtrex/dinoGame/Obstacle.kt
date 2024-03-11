package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

@SuppressLint("ViewConstructor")
internal class Obstacle(context: Context, screenWidth: Int, screenHeight: Int) :
    AppCompatImageView(context) {

    init {
        layoutParams = FrameLayout.LayoutParams(120, 120)
        x = screenWidth.toFloat() + 500F
        y = screenHeight / 2F
    }
}