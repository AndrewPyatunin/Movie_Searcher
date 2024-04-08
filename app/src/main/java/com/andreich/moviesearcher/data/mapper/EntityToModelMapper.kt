package com.andreich.moviesearcher.data.mapper

interface EntityToModelMapper<I, O> {

    fun map(from: I): O
}