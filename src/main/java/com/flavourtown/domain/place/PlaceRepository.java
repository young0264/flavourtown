package com.flavourtown.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface PlaceRepository extends JpaRepository<Place,Long>, PlaceRepositoryCustom {
    boolean existsById(long id);
}
