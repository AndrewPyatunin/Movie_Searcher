package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.domain.pojo.ReviewDto
import javax.inject.Inject

class ReviewDtoToReviewEntityMapper @Inject constructor() : MovieMapper<ReviewDto, ReviewEntity> {

    override fun map(
        fromDto: ReviewDto,
        item: Int,
        requestId: Long
    ): ReviewEntity {
        return with(fromDto) {
            ReviewEntity(
                id = id ?: 0,
                movieId = movieId ?: 0,
                title = title ?: "",
                type = type ?: "",
                review = review ?: "",
                date = date ?: "",
                author = author ?: "",
                page = item
            )
        }
    }
}