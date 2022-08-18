package com.matdongsan.domain.place;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Place {
    @Id
    private long id;

    private String placeName;
    private String placeUrl;
    private String categoryName;
    private String addressName;
    private String roadAddressName;
    private String phone;
    private String x;
    private String y;

    @Column(columnDefinition = "TEXT")
    private String menus;
    private String facilityInfo;

    public Place(long id, String placeName, String placeUrl, String categoryName, String addressName, String roadAddressName, String phone, String x, String y) {
        this.id = id;
        this.placeName = placeName;
        this.placeUrl = placeUrl;
        this.categoryName = categoryName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.phone = phone;
        this.x = x;
        this.y = y;
    }
    public void setAdditionalInfo(String menu,String facility){
        this.menus = menu;
        this.facilityInfo = facility;
    }
}