package com.flavourtown.service;

import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.domain.post.SearchType;
import com.flavourtown.domain.reply.ReplyTime;
import com.flavourtown.web.dto.post.PostCreateDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PlaceService placeService;

    //    private final ImageUtil imageUtil;
    private final PostRepository postRepository;
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Post> findAllByPlaceAndPrivateStatus(Place place) {
        return postRepository.findAllByPlaceAndPrivateStatus(place, true);
    }

    public Post savePost(Member member, PostCreateDto dto) {
//        String imageUrls = imageUtil.saveFiles(dto.getImgFiles());
        Place place = placeService.findPlace(dto.getPlaceId());
        String newTypeTime = convertDateTime(LocalDateTime.now());

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .privateStatus(dto.getPrivateStatus())
                .author(member)
//                .imageUrls(imageUrls)
                .createdTime(LocalDateTime.now())
                .modifiedTime(null)
                .postTime(newTypeTime)
                .build();
        post.addPlace(place);

        return postRepository.save(post);
    }

//    public PostUpdateDto updatePost(Post post, PostUpdateDto postUpdateDto) {
//
//    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    // 게시글 전체 조회. 페이징으로
    @Transactional(readOnly = true)
    public Page<Post> getList(String keyword, int page, String searchType, Pageable pageable) {

        searchType = searchType.toLowerCase();

        if (searchType.equals(SearchType.TITLE.getKey())) {
            return postRepository.searchTitle(keyword, pageable);
        } else if (searchType.equals(SearchType.CONTENT.getKey())) {
            return postRepository.searchContent(keyword, pageable);
        } else if (searchType.equals(SearchType.AUTHOR.getKey())) {
            return postRepository.searchAuthor(keyword, pageable);
        }

        return postRepository.findAll(pageable);
    }

    public List<Post> findTop5Post() {
        return postRepository.findPostTop5();
    }

    // 추가 부분

    public Post refreshTime1(Post post) {
        if (post.getModifiedTime() == null) {
            post.insertPostTime(convertDateTime(post.getCreatedTime()));
        }else{
            post.insertPostTime(convertDateTime(post.getModifiedTime()));
        }
        return post;
    }
    @Transactional(readOnly = true)
    public void refreshTime(List<Post> postList) {
        for (Post post : postList) {
            if (post.getModifiedTime() == null) {
                post.insertPostTime(convertDateTime(post.getCreatedTime()));
            } else {
                post.insertPostTime(convertDateTime(post.getModifiedTime()));
            }
        }
    }

    public static String convertDateTime(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        log.info("post timenow : " + now.toString());

        long diffTime = localDateTime.until(now, ChronoUnit.SECONDS);

        if (diffTime < ReplyTime.SEC) {
            return diffTime + "초 전";
        }
        diffTime = diffTime / ReplyTime.SEC;
        if (diffTime < ReplyTime.MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / ReplyTime.MIN;
        if (diffTime < ReplyTime.HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / ReplyTime.HOUR;
        if (diffTime < ReplyTime.DAY) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / ReplyTime.DAY;
        if (diffTime < ReplyTime.MONTH) {
            return diffTime + "개월 전";
        }
        diffTime = diffTime / ReplyTime.MONTH;
        return diffTime + "년 전";
    }


    /**
     * 이미지 불러오기
     */
    public String callImage(long id) {
        String imgSource = "";
        if (id % 3 == 0) {
            imgSource = "jjajang.jpg";
        } else if (id % 3 == 1) {
            imgSource = "jongro.jpg";
        } else {
            imgSource = "sushi.jpg";
        }
        return imgSource;
    }
}
