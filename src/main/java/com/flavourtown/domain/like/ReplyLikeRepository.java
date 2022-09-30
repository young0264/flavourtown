package com.flavourtown.domain.like;

import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    boolean existsByMemberAndReply(Member member, Reply reply);

    ReplyLike findByMemberAndReply(Member member, Reply reply);
}
