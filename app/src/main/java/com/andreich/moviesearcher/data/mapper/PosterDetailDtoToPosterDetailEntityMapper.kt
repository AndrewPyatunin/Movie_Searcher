package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PosterDetailEntity
import com.andreich.moviesearcher.domain.pojo.PosterDetailDto
import com.andreich.moviesearcher.domain.pojo.RequestResultDto

class PosterDetailDtoToPosterDetailEntityMapper : MovieMapper<RequestResultDto<PosterDetailDto>, PosterDetailEntity> {

    override fun map(
        fromRequestDto: RequestResultDto<PosterDetailDto>,
        item: Int,
        requestId: Long
    ): PosterDetailEntity {
        return with(fromRequestDto.docs[item]) {
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