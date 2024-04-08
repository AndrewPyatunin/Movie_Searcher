package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.data.entity.SeasonEntity
import com.andreich.moviesearcher.domain.pojo.EpisodesDto
import com.andreich.moviesearcher.domain.pojo.SeasonsDto

class SeasonsDtoToSeasonEntityMapper(
    private val episodeMapper: DtoMapper<EpisodesDto, EpisodeEntity>
) : MovieMapper<SeasonsDto, SeasonEntity> {

    override fun map(fromDto: SeasonsDto, item: Int, requestId: Long): SeasonEntity {
        return SeasonEntity(
            movieId = fromDto.movieId,
            number = fromDto.number,
            episodesCount = fromDto.episodesCount,
            episodes = fromDto.episodes.map {
                episodeMapper.map(it)
            },
            enName = fromDto.enName,
            enDescription = fromDto.enDescription,
            name = fromDto.name,
            airDate = fromDto.airDate,
            description = fromDto.description,
            url = fromDto.poster?.url,
            previewUrl = fromDto.poster?.previewUrl,
            id = fromDto.id.toString()
        )
    }
}