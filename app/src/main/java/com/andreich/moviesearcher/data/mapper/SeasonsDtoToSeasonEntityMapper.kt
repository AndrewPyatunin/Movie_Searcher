package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.data.entity.SeasonEntity
import com.andreich.moviesearcher.domain.pojo.EpisodesDto
import com.andreich.moviesearcher.domain.pojo.RequestResultDto
import com.andreich.moviesearcher.domain.pojo.SeasonsDto

class SeasonsDtoToSeasonEntityMapper(
    private val episodeMapper: DtoMapper<EpisodesDto, EpisodeEntity>
) : MovieMapper<RequestResultDto<SeasonsDto>, SeasonEntity> {

    override fun map(fromRequestDto: RequestResultDto<SeasonsDto>, item: Int, requestId: Long): SeasonEntity {
        val fromDto = fromRequestDto.docs[item]
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