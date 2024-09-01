package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.domain.model.Episode
import javax.inject.Inject

class EpisodeEntityToEpisodeMapper @Inject constructor() : EntityToModelMapper<EpisodeEntity, Episode> {

    override fun map(from: EpisodeEntity): Episode {
        return with(from) {
            Episode(
                number, name, enName, description, airDate
            )
        }
    }
}