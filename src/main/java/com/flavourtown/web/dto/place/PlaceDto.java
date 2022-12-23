package com.flavourtown.web.dto.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flavourtown.domain.place.Place;
import lombok.*;

@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {
    private long id;
    @JsonProperty("place_name")
    private String placeName;
    @JsonProperty("place_url")
    private String placeUrl;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("address_name")
    private String addressName;
    @JsonProperty("road_address_name")
    private String roadAddressName;
    private String phone;
    private String x;
    private String y;

    @JsonProperty("category_group_code")
    private String categoryGroupCode;
    @JsonProperty("category_group_name")
    private String categoryGroupName;
    private String distance;


}

