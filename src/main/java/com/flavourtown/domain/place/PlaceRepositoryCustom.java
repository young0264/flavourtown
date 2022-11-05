package com.flavourtown.domain.place;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaceRepositoryCustom {
    List<Place> findByTop5Place();
}
