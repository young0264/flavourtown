package com.flavourtown.web.dto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    @Size(min = 5, max = 20, message = "제목은 5글자 ~ 15글자 이내로 작성해주세요.")
    private String title; // 제목

    @NotBlank(message = "내용을 입력하세요")
    private String content; // 내용

    private Boolean privateStatus;

//    private List<MultipartFile> imgFiles;

    private LocalDateTime modifiedTime;

    private String postTime;

    @NotNull(message = "음식점을 골라주세요")
    private Long placeId;

    private String placeName;

}
