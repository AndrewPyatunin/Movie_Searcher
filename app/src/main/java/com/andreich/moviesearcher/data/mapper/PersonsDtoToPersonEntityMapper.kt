package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.domain.pojo.PersonsDto

class PersonsDtoToPersonEntityMapper : MovieMapper<PersonsDto, PersonEntity> {

    override fun map(fromDto: PersonsDto, item: Int, requestId: Long): PersonEntity {
        return with(fromDto) {
            PersonEntity(
                id = id ?: 0,
                photoUrl = photo,
                name = name,
                enName = enName,
                profession = profession,
                enProfession = enProfession,
                page = item
            )
        }
    }
}