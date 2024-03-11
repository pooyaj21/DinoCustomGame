package com.example.loadingtrex.dinoGame

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.example.loadingtrex.R

class Dino(context: Context) : AppCompatImageView(context) {
    private var isJumping = false
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
            val startY = this.y
            val jumpHeight = -200F
            val jumpDuration = 400L
            val downDuration = 350L

            val jumpAnim = ValueAnimator.ofFloat(0F, jumpHeight).apply {
                duration = jumpDuration
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener { animation ->
                    y = startY + animation.animatedValue as Float
                }
            }

            val downAnim = ValueAnimator.ofFloat(jumpHeight, 0F).apply {
                duration = downDuration
                interpolator = AccelerateInterpolator()
                addUpdateListener { animation ->
                    y = startY + animation.animatedValue as Float
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        isJumping = false
                    }
                })
            }

            jumpAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    downAnim.start()
                }
            })

            jumpAnim.start()
        }
    }
}