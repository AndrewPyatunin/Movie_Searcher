package com.andreich.moviesearcher.data.mapper

interface MovieMapper<I, O> {

    fun map(fromRequestDto: I, item: Int, requestId: Long): O
}