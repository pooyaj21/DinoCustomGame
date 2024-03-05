package com.example.loadingtrex.dinoGame

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import kotlinx.coroutines.*
import java.util.*

@SuppressLint("ViewConstructor")
class GameLayout(context: Context, private val onLost: () -> Unit) : FrameLayout(context) {

    private val dino = Dino(context)
    private val middleLine = View(context).apply {
        setBackgroundColor(Color.BLACK)
    }
    private val obstacles = mutableListOf<Obstacle>()
    private var obstacleJob: Job? = null

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(Color.TRANSPARENT)
        setOnClickListener {
            dino.jump()
        }
        addView(dino, LayoutParams(100, 100))
        startObstacleGenerator()
        addView(middleLine, LayoutParams(LayoutParams.MATCH_PARENT, 10))
    }

    private fun startObstacleGenerator() {
        obstacleJob = CoroutineScope(Dispatchers.Main).launch {
            while (!dino.isDead) {
                delay((Random().nextInt(2000) + 1000).toLong()) // Random delay between 1 to 3 seconds
                addObstacle()
            }
            obstacleJob?.cancel()
        }
    }

    private fun addObstacle() {
        val obstacle = Obstacle(context, width, height)
        obstacles.add(obstacle)
        addView(obstacle)
        val animation = ObjectAnimator.ofPropertyValuesHolder(
            obstacle, PropertyValuesHolder.ofFloat("x", obstacle.x, -100F)
        )
        animation.duration = 2500L
        animation.addUpdateListener {
            // Check if the current X-coordinate reaches a certain value
            if (isCollision(obstacle)) {
                dino.isDead = true
                animation.cancel()
                obstacleJob?.cancel()
                onLost()
            }
        }
        animation.doOnEnd {
            if (!dino.isDead) {
                this@GameLayout.removeView(obstacle)
                obstacles.remove(obstacle)
            }
        }
        animation.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (dino.y == 0F) dino.changeHeight(height / 2F)
        if (middleLine.y == 0F) middleLine.y = (height / 2F) + 100
    }

    fun onDestroy() {
        obstacleJob?.cancel()
    }

    private fun isCollision(obstacle: Obstacle): Boolean {
        val rect1 = Rect()
        dino.getHitRect(rect1)

        val rect2 = Rect()
        obstacle.getHitRect(rect2)

        return rect1.intersect(rect2)
    }

}
