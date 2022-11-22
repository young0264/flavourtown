package com.flavourtown.domain.like;

import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.reply.Reply;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Builder
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReplyLike {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER )
    private Reply reply;

    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Include
    private Member member;

    


}
