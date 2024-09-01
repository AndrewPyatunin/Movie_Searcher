package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.domain.model.Review
import javax.inject.Inject

class ReviewEntityToReviewMapper @Inject constructor() : EntityToModelMapper<ReviewEntity, Review> {

    override fun map(from: ReviewEntity): Review {
        return with(from) {
            Review(id, movieId, title, type, review, date, author)
        }
    }
}