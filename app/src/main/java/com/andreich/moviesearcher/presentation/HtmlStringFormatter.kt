package com.andreich.moviesearcher.presentation

import androidx.core.text.HtmlCompat

interface HtmlStringFormatter : (String) -> CharSequence

class HtmlStringFormatterImpl : HtmlStringFormatter {

    override fun invoke(text: String) =
        HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
}