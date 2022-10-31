package com.flavourtown.domain.post;

import com.flavourtown.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post,Long> , PostRepositoryCustom {

    List<Post> findAllByPlace(Place place);
    List<Post> findAllByPlaceAndPrivateStatus(Place place, boolean isPrivate);

}
