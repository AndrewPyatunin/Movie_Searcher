package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PosterDetailEntity
import com.andreich.moviesearcher.domain.pojo.PosterDetailDto

class PosterDetailDtoToPosterDetailEntityMapper : MovieMapper<PosterDetailDto, PosterDetailEntity> {

    override fun map(
        fromDto: PosterDetailDto,
        item: Int,
        requestId: Long
    ): PosterDetailEntity {
        return with(fromDto) {
            PosterDetailEntity(
                url = url.toString(),
                height = height ?: 0,
                movieId = movieId ?: 0,
                previewUrl = previewUrl.toString(),
                type = type.toString(),
                width = width ?: 0,
                id = id.toString()
            )
        }
    }
}