package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.domain.pojo.EpisodeDto
import javax.inject.Inject

class EpisodeDtoToEpisodeEntityMapper @Inject constructor() : DtoMapper<EpisodeDto, EpisodeEntity> {

    override fun map(fromDto: EpisodeDto): EpisodeEntity {
        return EpisodeEntity(
            number = fromDto.number ?: -1,
            name = fromDto.name ?: "",
            enName = fromDto.enName ?: "",
            url = fromDto.episodePoster?.url ?: "",
            previewUrl = fromDto.episodePoster?.previewUrl ?: "",
            duration = fromDto.duration ?: -1,
            date = fromDto.date ?: "",
            description = fromDto.description ?: "",
            airDate = fromDto.airDate ?: "",
            enDescription = fromDto.enDescription ?: ""
        )
    }
}