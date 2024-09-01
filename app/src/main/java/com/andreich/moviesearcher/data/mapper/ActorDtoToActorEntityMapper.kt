package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.ActorEntity
import com.andreich.moviesearcher.data.entity.MoviesEntity
import com.andreich.moviesearcher.data.entity.SpousesEntity
import com.andreich.moviesearcher.domain.pojo.ActorDto
import javax.inject.Inject

class ActorDtoToActorEntityMapper @Inject constructor() : MovieMapper<ActorDto, ActorEntity> {

    override fun map(fromDto: ActorDto, item: Int, requestId: String): ActorEntity {
        return ActorEntity(
            fromDto.id ?: 0,
            fromDto.name ?: "",
            fromDto.enName,
            fromDto.photo,
            fromDto.sex,
            fromDto.growth,
            fromDto.birthday,
            fromDto.death,
            fromDto.age,
            fromDto.birthPlace?.map {
                it.value.toString()
            } ?: emptyList(),
            fromDto.deathPlace ?: emptyList(),
            fromDto.spouses?.map {
                SpousesEntity(
                    it.id ?: 0,
                    it.name ?: "",
                    it.divorced,
                    it.children,
                    it.relation,
                )
            } ?: emptyList(),
            fromDto.countAwards,
            fromDto.profession?.map {
                it.value ?: ""
            } ?: emptyList(),
            fromDto.facts?.map {
                it.value.toString()
            } ?: emptyList(),
            fromDto.movies?.map {
                MoviesEntity(
                    it.id ?: 0,
                    it.name ?: "",
                    it.alternativeName,
                    it.rating,
                    it.general,
                    it.description,
                    it.enProfession,
                )
            } ?: emptyList(),
            item,
            fromDto.movies?.map {
                it.id ?: 0
            } ?: emptyList()
        )
    }
}