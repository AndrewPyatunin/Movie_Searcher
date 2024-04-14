package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PosterEntity
import com.andreich.moviesearcher.domain.pojo.PosterDto
import javax.inject.Inject

class PosterDtoToPosterEntityMapper @Inject constructor() : MovieMapper<PosterDto, PosterEntity> {

    override fun map(
        fromDto: PosterDto,
        item: Int,
        requestId: String
    ): PosterEntity {
        return with(fromDto) {
            PosterEntity(
                url = url ?: "",
                height = height ?: 0,
                movieId = movieId ?: 0,
                previewUrl = previewUrl ?: "",
                type = type ?: "",
                width = width ?: 0,
                id = id ?: "",
                page = item
            )
        }
    }
}