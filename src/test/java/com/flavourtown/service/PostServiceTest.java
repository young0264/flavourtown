package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;

import com.flavourtown.domain.place.Place;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.dto.post.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AccountRepository accountRepository;

    Long thisId;

    @Test
    @DisplayName("회원 가입")
    void testBefore() {
        Account account = Account.builder()
                .username("testUser")
                .password("testPwd")
                .email("test@naver.com")
                .build();

        MemberInfoDto memberInfoDto = MemberInfoDto.builder()
                .introduce("테스트")
                .birth(new Date())
                .gender("man")
                .memberAge(MemberAge.MEMBER_AGE_20S)
                .build();
        accountRepository.save(account);
        Member newMember = memberService.createNewMember(account, memberInfoDto);

        assertThat(newMember.getNickname()).isEqualTo("testUser");
        assertThat(newMember.getAccount().getPassword()).isEqualTo("testPwd");
        assertThat(newMember.getGender()).isEqualTo("man");
    }

    //savePost(String userName, Member user, PostDto postDto)
    @Test
    @DisplayName("게시글 등록")
    void t1() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        PostDto postDto = PostDto.builder()
                .placeId(26981291L) //등록되어 있는 place Id 사용
                .title("testTitle")

                .content("testContent")
                .privateStatus(true)
                .build();
        Post post = postService.savePost(member, postDto);

        thisId = post.getId();
        assertThat(post.getTitle()).isEqualTo("testTitle");
        assertThat(post.getContent()).isEqualTo("testContent");
        assertThat(post.isPrivateStatus()).isEqualTo(true);
        assertThat(post.getPlace().getId()).isEqualTo(26981291L);
    }

    @Test
    @DisplayName("게시글 수정")
    void t2() {
        t1();
        Post post = postRepository.findById(thisId).orElse(null);

        PostDto postDto = PostDto.builder()
                .title("testUpdateTitle")
                .content("testUpdateContent")
                .privateStatus(true)
                .build();

        postService.updatePost(post, postDto);
        assertThat(post.getTitle()).isEqualTo("testUpdateTitle");
        assertThat(post.getContent()).isEqualTo("testUpdateContent");
        assertThat(post.isPrivateStatus()).isEqualTo(true);
        System.out.println("update title : " + post.getTitle());
    }

    @Test
    @DisplayName("게시글 삭제")
    void t3() {
        t1();
        System.out.println("this id 3 : " + thisId);
        Post post = postRepository.findById(thisId).orElse(null);
        Place place = post.getPlace();

        List<Post> posts = post.getPlace().getPosts();

        for (Post post1 : posts) {
            System.out.println("posts 3 : " + post1.getTitle());
        }

        postService.delete(thisId);
        assertThat(postService.findById(thisId)).isNull();
        assertThat(place.getPosts().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("post 삭제 후 해당post 남아있는지 확인")
    void t4() {

    }
}