package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.domain.pojo.RequestResultDto
import com.andreich.moviesearcher.domain.pojo.ReviewDto

class ReviewDtoToReviewEntityMapper : MovieMapper<RequestResultDto<ReviewDto>, ReviewEntity> {

    override fun map(
        fromRequestDto: RequestResultDto<ReviewDto>,
        item: Int,
        requestId: Long
    ): ReviewEntity {
        val fromDto = fromRequestDto.docs[item]
        return with(fromDto) {
            ReviewEntity(
                id = id ?: 0,
                movieId = movieId ?: 0,
                title = title.toString(),
                type = type.toString(),
                review = review.toString(),
                date = date.toString(),
                author = author.toString()
            )
        }
    }
}