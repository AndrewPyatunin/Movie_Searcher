package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.domain.pojo.EpisodesDto

class EpisodesDtoToEpisodeEntityMapper : DtoMapper<EpisodesDto, EpisodeEntity> {

    override fun map(fromDto: EpisodesDto): EpisodeEntity {
        return EpisodeEntity(
            number = fromDto.number,
            name = fromDto.name,
            enName = fromDto.enName,
            url = fromDto.episodePoster?.url,
            previewUrl = fromDto.episodePoster?.previewUrl,
            duration = fromDto.duration,
            date = fromDto.date,
            description = fromDto.description,
            airDate = fromDto.airDate,
            enDescription = fromDto.enDescription
        )
    }
}