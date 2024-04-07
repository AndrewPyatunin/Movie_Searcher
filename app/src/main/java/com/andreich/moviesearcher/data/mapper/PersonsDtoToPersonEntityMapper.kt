package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.domain.pojo.PersonsDto

class PersonsDtoToPersonEntityMapper : DtoMapper<PersonsDto, PersonEntity> {

    override fun map(fromDto: PersonsDto): PersonEntity {
        return PersonEntity(
            id = fromDto.id,
            photoUrl = fromDto.photo,
            name = fromDto.name,
            enName = fromDto.enName,
            profession = fromDto.profession,
            enProfession = fromDto.enProfession
        )
    }
}