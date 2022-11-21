package com.flavourtown.service;

import com.flavourtown.domain.like.PostLike;
import com.flavourtown.domain.like.PostLikeRepository;
import com.flavourtown.domain.like.ReplyLike;
import com.flavourtown.domain.like.ReplyLikeRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.reply.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeApiService {

    private final PostLikeRepository postLikeRepository;
    private final ReplyLikeRepository replyLikeRepository;

    private final PostService postService;
    private final ReplyService replyService;

    /* Post Like 시작 */

    public PostLike createNewPostLike(Member member, Post currentPost) {
        PostLike newPostLike = PostLike.builder()
                .member(member)
                .post(currentPost)
                .build();
        currentPost.getPostLike().add(newPostLike);
        member.getPostLike().add(newPostLike);
        return postLikeRepository.save(newPostLike);
    }

    public void deletePostLike(Member member, Post currentPost, PostLike postLike) {
        currentPost.getPostLike().remove(postLike);
        member.getPostLike().remove(postLike);
        postLikeRepository.delete(postLike);
    }

    public boolean existPostLikeFlag(Member member, Post post) {
        return postLikeRepository.existsByMemberAndPost(member, post);
    }

    public boolean modifyLikeStatus(Member member, Long id, String sort) {
        switch (sort) {
            case "post":
                Post currentPost = postService.findById(id);
                if (existPostLikeFlag(member, currentPost)) {
                    PostLike currentPostLike = postLikeRepository.findByMemberAndPost(member, currentPost);
                    deletePostLike(member, currentPost, currentPostLike);
                    return false;
                } else {
                    createNewPostLike(member, currentPost);
                    return true;
                }
            case "reply":
                Reply currentReply = replyService.getReply(id);
                if (existReplyLikeFlag(member, currentReply)) {
                    ReplyLike currentReplyLike = replyLikeRepository.findByMemberAndReply(member, currentReply);
                    replyLikeRepository.delete(currentReplyLike);
                    return false;
                } else {
                    createNewReplyLike(member, currentReply);
                    return true;
                }
        }
        return false;
    }

    /* Post Like 끝 */

    /* Reply Like 시작 */

    public void createNewReplyLike(Member member, Reply currentReply) {
        ReplyLike newPostLike = ReplyLike.builder()
                .member(member)
                .reply(currentReply)
                .build();
        replyLikeRepository.save(newPostLike);
    }

    public boolean existReplyLikeFlag(Member member, Reply reply) {
        return replyLikeRepository.existsByMemberAndReply(member, reply);
    }


    /* Reply Like 끝 */
}
