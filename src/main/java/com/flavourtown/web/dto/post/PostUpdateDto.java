package com.flavourtown.web.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateDto {

    private Long id;

    private String title;

    private String content;

    private String placeName;

    private Boolean privateStatus;


}
