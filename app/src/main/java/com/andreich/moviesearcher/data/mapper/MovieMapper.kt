package com.andreich.moviesearcher.data.mapper

interface MovieMapper<I, O> {

    fun map(fromDto: I, item: Int, requestId: Long): O
}