package com.flavourtown.web.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostUpdateDto {

    private Long id;

    private String title;

    private String content;

//    private List<MultipartFile> imgFiles;

    //    private LocalDateTime modifiedTime;
    private String placeName;

    private Boolean privateStatus;
}
