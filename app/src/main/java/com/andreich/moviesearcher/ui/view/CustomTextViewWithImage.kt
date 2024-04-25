package com.andreich.moviesearcher.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.ExpandableTextViewWithImageBinding

class CustomTextViewWithImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.iconExpandableStyle,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ExpandableTextViewWithImageBinding

    override fun addView(child: View?) {
        super.addView(child)
    }

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.expandable_text_view_with_image, this, true)
        binding = ExpandableTextViewWithImageBinding.bind(this)
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
            expandSvg?.let {
                expand.setImageDrawable(it)
            }
            val shortenSvg = typedArray.getDrawable(R.styleable.CustomTextViewWithImage_shorten_svg)
            shortenSvg?.let {
                shorten.setImageDrawable(it)
            }
        }
        typedArray.recycle()
    }
}