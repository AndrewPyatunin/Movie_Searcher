package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.domain.pojo.ReviewDto

class ReviewDtoToReviewEntityMapper : MovieMapper<ReviewDto, ReviewEntity> {

    override fun map(
        fromDto: ReviewDto,
        item: Int,
        requestId: Long
    ): ReviewEntity {
        return with(fromDto) {
            ReviewEntity(
                id = id ?: 0,
                movieId = movieId ?: 0,
                title = title.toString(),
                type = type.toString(),
                review = review.toString(),
                date = date.toString(),
                author = author.toString(),
                page = item
            )
        }
    }
}