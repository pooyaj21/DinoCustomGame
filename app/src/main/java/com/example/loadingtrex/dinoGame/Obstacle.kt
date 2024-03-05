package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.example.loadingtrex.R

@SuppressLint("ViewConstructor")
class Obstacle(context: Context, screenWidth: Int, screenHeight: Int) :
    AppCompatImageView(context) {

    init {
        setImageResource(R.drawable.pharma)
        layoutParams = FrameLayout.LayoutParams(90, 90)
        x = screenWidth.toFloat()
        y = screenHeight / 2F
    }
}