package com.flavourtown.domain.place;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.flavourtown.domain.place.QPlace.place;

@RequiredArgsConstructor
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Place> findByTop5Place() {
        JPAQuery<Place> placeQuery = jpaQueryFactory
                .selectFrom(place)
                .orderBy(place.posts.size().desc())
                .limit(5);

        return placeQuery.fetch();
    }
}
