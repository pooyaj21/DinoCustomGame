package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*
import com.example.loadingtrex.R

@SuppressLint("ViewConstructor")
class LostLayout(context: Context, private val onRetry: () -> Unit) : LinearLayout(context) {
    private val imageView = ImageView(context).apply {
        setImageResource(R.drawable.dino_dead)
    }
    private val textView = TextView(context).apply {
        text = "G A M E  O V E R"
        textAlignment = TEXT_ALIGNMENT_CENTER
        setTypeface(null, Typeface.BOLD)
    }
    private val retryButton = Button(context).apply {
        setBackgroundResource(R.drawable.retry)
        setOnClickListener {
            onRetry()
        }
    }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )

        addView(imageView, LayoutParams(180, 180))
        addView(textView, LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ))
        addView(retryButton, LayoutParams(120, 150))
    }
}
