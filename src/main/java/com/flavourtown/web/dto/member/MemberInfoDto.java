package com.flavourtown.web.dto.member;

import com.flavourtown.domain.member.MemberAge;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {

    @NotBlank
    private String introduce;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String gender;

    private MemberAge memberAge;
}
