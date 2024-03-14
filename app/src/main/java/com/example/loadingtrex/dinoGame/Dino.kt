package com.example.loadingtrex.dinoGame

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.example.loadingtrex.R
import kotlinx.coroutines.*

internal class Dino(context: Context) : AppCompatImageView(context) {
    private var isJumping = false
    private var runningJob: Job? = null
    var isDead = false
    val jumpHeight = 200
    val measure = 120

    init {
        setImageResource(R.drawable.dino)
        x = 50F
        startRunning()
    }

    fun changeHeight(height: Float) {
        y = height
    }

    private fun startRunning() {
        runningJob = CoroutineScope(Dispatchers.Main).launch {
            while (!isDead) {
                delay(100)
                setImageResource(R.drawable.dino_run_left_leg)
                delay(100)
                setImageResource(R.drawable.dino_run_right_leg)
            }
            runningJob?.cancel()
        }
    }

    private fun stopRunning() {
        runningJob?.cancel()
        setImageResource(R.drawable.dino)
    }

    fun jump() {
        if (!isJumping && !isDead) {
            isJumping = true
            stopRunning()
            val startY = this.y
            val jumpDuration = 400L
            val downDuration = 350L

            val jumpAnim = ValueAnimator.ofFloat(0F, -jumpHeight.toFloat()).apply {
                duration = jumpDuration
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener { animation ->
                    y = startY + animation.animatedValue as Float
                }
            }

            val downAnim = ValueAnimator.ofFloat(-jumpHeight.toFloat(), 0F).apply {
                duration = downDuration
                interpolator = AccelerateInterpolator()
                addUpdateListener { animation ->
                    y = startY + animation.animatedValue as Float
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        isJumping = false
                        startRunning()
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