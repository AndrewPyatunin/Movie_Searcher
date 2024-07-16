package com.andreich.moviesearcher.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.ExpandableTextViewWithImageBinding

class CustomTextViewWithImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.iconExpandableStyle,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes), OnTouchListener {

    private val binding: ExpandableTextViewWithImageBinding

    override fun addView(child: View?) {
        super.addView(child)
    }

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.expandable_text_view_with_image, this, true)
        binding = ExpandableTextViewWithImageBinding.bind(this)
        setOnTouchListener(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextViewWithImage,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {
            val categoryText =
                typedArray.getString(R.styleable.CustomTextViewWithImage_category_text)
            movieInfoTag.text = categoryText
            val expandSvg = typedArray.getDrawable(R.styleable.CustomTextViewWithImage_expand_svg)
            val textSize = typedArray.getDimensionPixelSize(
                R.styleable.CustomTextViewWithImage_category_text_size,
                0
            ) / 2
            movieInfoTag.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
            expandSvg?.let {
                expand.setImageDrawable(it)
            }
            val back = context.theme.obtainStyledAttributes(R.style.Theme_MovieSearcher, intArrayOf(
                com.google.android.material.R.attr.colorOnPrimary))
            color = back.getColor(0, 0)
            val shortenSvg = typedArray.getDrawable(R.styleable.CustomTextViewWithImage_shorten_svg)
            shortenSvg?.let {
                shorten.setImageDrawable(it)
            }
        }
        typedArray.recycle()
    }

    var color: Int? = null

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (hasOnClickListeners()) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val background = binding.root.background
                    if (background != ContextCompat.getDrawable(context, R.color.gray)) {
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.gray
                            )
                        )
                    }
                    dispatchSetSelected(true)
                }
                MotionEvent.ACTION_CANCEL -> {
                    dispatchSetSelected(false)
                }
                else -> {

                }
            }
        }
        return false
    }

    fun changeToInitialBackground() {
        binding.root.setBackgroundColor(color ?: Color.GREEN)
    }
}