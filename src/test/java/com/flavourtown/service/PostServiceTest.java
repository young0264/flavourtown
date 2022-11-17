//package com.flavourtown.service;
//
//import com.flavourtown.FlavourtownApplication;
//import com.flavourtown.domain.account.AccountRepository;
//import com.flavourtown.domain.member.Member;
//import com.flavourtown.domain.member.MemberRepository;
//import com.flavourtown.domain.post.Post;
//import com.flavourtown.domain.post.PostRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.stereotype.Service;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.BootstrapWith;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest
////@Transactional
////@ActiveProfiles("test")
//@TestPropertySource(locations="classpath:application-test.properties")
//@AutoConfigureMockMvc
////@WebMvcTest
////@SpringBootTest(classes = FlavourtownApplication.class)
//@Service
////@DataJpaTest
////@ExtendWith(SpringExtension.class)
////@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class PostServiceTest {
////    public String createPost(@Valid PostDto postDto, BindingResult bindingResult,
////                             Model model, Principal principal, RedirectAttributes redirectAttributes,
////                             @AuthenticationPrincipal SecurityUser securityUser)
//
////    savePost(String userName, Member user, PostDto postDto)
//
////    @Autowired
////    private PostService postService;
////
//    @Autowired
//    private MemberRepository memberRepository;
//
////    @Autowired
////    private AccountRepository accountRepository;
//
////    @Autowired
////    private PostRepository postRepository;
//
//
//    @Test
//    @DisplayName("post list 조회")
//    void showPostList() {
//        Post post1 = new Post();
////        postRepository.save(post1);
////        List<Post> posts = postRepository.findAll();
////        for (Post post : posts) {
////            String postTitle = post.getPostTime();
////            System.out.println(postTitle);
////        }
//    }
//
//
//    @Test
//    @DisplayName("게시글 등록")
//    void createPostTest() {
//        System.out.println(1);
//        Member member = new Member();
//        System.out.println(2);
//        member.setNickname("테스트닉네임");
//        System.out.println(3);
//        assertThat("테스트닉네임").isEqualTo(member.getNickname());
//        List<Member> all = memberRepository.findAll();
//        for (Member member1 : all) {
//            System.out.println(member1.getNickname());
//
//        }
////        memberRepository.save(member);
////        System.out.println(123);
////        System.out.println("member : " + member.getNickname());
//    }
////        Optional<Member> member = memberRepository.findByNickname("1");
////        List<Account> all = accountRepository.findAll();
//
////        List<Post> all = postRepository.findAll();
////        all.add(new Post());
////        System.out.println(all.size());
//
////        Post post = postService.savePost("1", member.get(), new PostDto(1L, "test제목", "test내용", Boolean.TRUE, null, LocalDateTime.now().toString(), 1L, "test식당"));
////        System.out.println(post.getTitle());
////        assertThat(post).isNotNull();
//        //
////        Post post = new Post();
////        post.setTitle("테스트제목");
////        post.setContent("테스트내용");
////        assertThat(post.getTitle()).isEqualTo("테스트제목");
////    }
//}
//
//
//
