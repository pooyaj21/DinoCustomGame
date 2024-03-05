package com.example.loadingtrex.dinoGame

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.example.loadingtrex.R

class Dino(context: Context) : AppCompatImageView(context) {
    private var isJumping = false
    private val jumpHeight = 250F
    var isDead = false

    init {
        setImageResource(R.drawable.dino)
        x = 50F
    }

    fun changeHeight(height: Float) {
        y = height
    }

    fun jump() {
        if (!isJumping && !isDead) {
            isJumping = true
            animate()
                .translationYBy(-jumpHeight)
                .setDuration(600)
                .withEndAction {
                    animate()
                        .translationYBy(jumpHeight)
                        .setDuration(500)
                        .withEndAction {
                            isJumping = false
                        }
                        .start()
                }
                .start()
        }
    }
}