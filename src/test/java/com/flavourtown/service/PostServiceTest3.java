package com.flavourtown.service;

import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Transactional
@ActiveProfiles("test")
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostServiceTest {
//    public String createPost(@Valid PostDto postDto, BindingResult bindingResult,
//                             Model model, Principal principal, RedirectAttributes redirectAttributes,
//                             @AuthenticationPrincipal SecurityUser securityUser)

//    savePost(String userName, Member user, PostDto postDto)

    @Autowired
    private  PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 등록")
    void createPostTest() {
        System.out.println(1);
//        Member member = memberRepository.findByNickname("1").orElse(null);
        Member member = new Member();
        System.out.println(2);
        member.setNickname("테스트닉네임");
        System.out.println(3);
        memberRepository.save(member);
        System.out.println(123);
        System.out.println("member : " + member.getNickname());

//        Optional<Member> member = memberRepository.findByNickname("1");
//        List<Account> all = accountRepository.findAll();

//        List<Post> all = postRepository.findAll();
//        all.add(new Post());
//        System.out.println(all.size());

//        Post post = postService.savePost("1", member.get(), new PostDto(1L, "test제목", "test내용", Boolean.TRUE, null, LocalDateTime.now().toString(), 1L, "test식당"));
//        System.out.println(post.getTitle());
//        assertThat(post).isNotNull();
    }

    @Test
    void abc() {
    }

}