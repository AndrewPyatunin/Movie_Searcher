package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.data.entity.SeasonEntity
import com.andreich.moviesearcher.domain.model.Episode
import com.andreich.moviesearcher.domain.model.Season
import javax.inject.Inject

class SeasonEntityToSeasonMapper @Inject constructor(
    private val episodeMapper: EntityToModelMapper<EpisodeEntity, Episode>
) : EntityToModelMapper<SeasonEntity, Season> {

    override fun map(from: SeasonEntity): Season {
        return with(from) {
            Season(
                id,
                movieId,
                number,
                airDate,
                enName,
                episodes.map { episodeMapper.map(it) },
                episodesCount,
                name,
                source
            )
        }
    }
}