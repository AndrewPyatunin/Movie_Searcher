package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.domain.pojo.PersonDto
import javax.inject.Inject

class PersonDtoToPersonEntityMapper @Inject constructor() : MovieMapper<PersonDto, PersonEntity> {

    override fun map(fromDto: PersonDto, item: Int, requestId: String): PersonEntity {
        return with(fromDto) {
            PersonEntity(
                id = id ?: 0,
                photoUrl = photo ?: "",
                name = name ?: "",
                enName = enName ?: "",
                description = description ?: "",
                profession = profession ?: "",
                enProfession = enProfession ?: "",
                page = item
            )
        }
    }
}