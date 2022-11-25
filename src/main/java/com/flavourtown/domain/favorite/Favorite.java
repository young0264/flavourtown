package com.flavourtown.domain.favorite;

import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY) //
    private Member member;

    @OneToMany(mappedBy = "favorite", orphanRemoval = true)
    private List<Place> placeList = new ArrayList<>();

    public Favorite(Member member, String subject) {
        this.member = member;
        this.subject = subject;
    }

    public void addPlace(Place place) {
        this.placeList.add(place);
        place.insertFavorite(this);
    }


    public void insertMember(Member member) {
        this.member = member;
    }
    public void insertSubject(String subject) {
        this.subject = subject;
    }
}
