package com.flavourtown.domain.favorite;

import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Favorite {
    // 고유 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "favorite", orphanRemoval = true)
    private List<Place> placeList = new ArrayList<>();

    public Favorite(Member member, String subject) {
        this.member = member;
        this.subject = subject;
    }

//    public void addPlace(Place place) {
//        this.placeList.add(place);
//    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
