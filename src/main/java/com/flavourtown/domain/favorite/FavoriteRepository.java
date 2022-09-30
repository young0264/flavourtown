package com.flavourtown.domain.favorite;

import com.flavourtown.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findAllByMember(Member member);

    Favorite findTopByMember(Member member);

    Favorite findByIdAndMember(Long favoriteId, Member member);
}
