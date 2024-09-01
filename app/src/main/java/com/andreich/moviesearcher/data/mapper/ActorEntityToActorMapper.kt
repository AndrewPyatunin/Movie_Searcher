package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.ActorEntity
import com.andreich.moviesearcher.domain.model.Actor
import com.andreich.moviesearcher.domain.model.Movies
import com.andreich.moviesearcher.domain.model.Spouses
import javax.inject.Inject

class ActorEntityToActorMapper @Inject constructor() : EntityToModelMapper<ActorEntity, Actor> {

    override fun map(from: ActorEntity): Actor {
        return Actor(
            from.id,
            from.name,
            from.enName,
            from.photo,
            from.sex,
            from.growth,
            from.birthday,
            from.death,
            from.age,
            from.birthPlace,
            from.deathPlace,
            from.spouses.map {
                Spouses(
                    it.id,
                    it.name,
                    it.divorced,
                    it.children,
                    it.relation,
                )
            },
            from.countAwards,
            from.profession,
            from.facts,
            from.movies.map {
                Movies(
                    it.id,
                    it.name,
                    it.alternativeName,
                    it.rating,
                    it.general,
                    it.description,
                    it.enProfession
                )
            }
        )
    }
}