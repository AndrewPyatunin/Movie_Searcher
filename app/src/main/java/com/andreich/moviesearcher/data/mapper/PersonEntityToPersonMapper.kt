package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.domain.model.Person
import javax.inject.Inject

class PersonEntityToPersonMapper @Inject constructor(): EntityToModelMapper<PersonEntity, Person> {

    override fun map(from: PersonEntity): Person {
        with(from) {
            return Person(
                id, photoUrl, name, enName, profession, enProfession, description
            )
        }
    }
}