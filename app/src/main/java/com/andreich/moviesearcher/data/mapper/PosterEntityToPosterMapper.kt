package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PosterEntity
import com.andreich.moviesearcher.domain.model.Poster
import javax.inject.Inject

class PosterEntityToPosterMapper @Inject constructor() : EntityToModelMapper<PosterEntity, Poster> {

    override fun map(from: PosterEntity): Poster {
        return with(from) {
            Poster(
                url, height, movieId, previewUrl, type, width, id
            )
        }
    }
}