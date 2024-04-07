package com.andreich.moviesearcher.data.mapper

interface DtoMapper<I, O> {

    fun map(fromDto: I): O
}