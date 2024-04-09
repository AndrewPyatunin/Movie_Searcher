package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.data.entity.SeasonEntity
import com.andreich.moviesearcher.domain.pojo.EpisodeDto
import com.andreich.moviesearcher.domain.pojo.SeasonDto
import javax.inject.Inject

class SeasonDtoToSeasonEntityMapper @Inject constructor(
    private val episodeMapper: DtoMapper<EpisodeDto, EpisodeEntity>
) : MovieMapper<SeasonDto, SeasonEntity> {

    override fun map(fromDto: SeasonDto, item: Int, requestId: Long): SeasonEntity {
        return SeasonEntity(
            movieId = fromDto.movieId ?: -1,
            number = fromDto.number ?: -1,
            episodesCount = fromDto.episodesCount ?: -1,
            episodes = fromDto.episodes.map {
                episodeMapper.map(it)
            },
            enName = fromDto.enName ?: "",
            enDescription = fromDto.enDescription ?: "",
            name = fromDto.name ?: "",
            airDate = fromDto.airDate ?: "",
            description = fromDto.description ?: "",
            url = fromDto.poster?.url ?: "",
            previewUrl = fromDto.poster?.previewUrl ?: "",
            id = fromDto.id ?: ""
        )
    }
}