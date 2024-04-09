package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.SeasonEntity
import com.andreich.moviesearcher.domain.model.Season
import javax.inject.Inject

class SeasonEntityToSeasonMapper @Inject constructor() : EntityToModelMapper<SeasonEntity, Season> {

    override fun map(from: SeasonEntity): Season {
        return with(from) {
            Season(
                movieId, number, episodesCount, episodes, enName, name, airDate, description, enDescription, url, previewUrl, id
            )
        }
    }
}