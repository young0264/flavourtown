package com.flavourtown.web.controller.post;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AuthUser;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.post.Post;

import com.flavourtown.domain.post.PostRepository;

import com.flavourtown.domain.post.SearchType;
import com.flavourtown.domain.reply.Reply;
import com.flavourtown.infra.security.SecurityUser;
import com.flavourtown.service.AccountService;
import com.flavourtown.service.LikeApiService;
import com.flavourtown.service.PostService;
import com.flavourtown.service.ReplyService;
import com.flavourtown.web.dto.post.PostDto;
import com.flavourtown.web.dto.reply.ReplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AccountService accountService;
    private final ReplyService replyService;

    private final PostRepository postRepository;

    private final LikeApiService likeApiService;


    /**
     * 게시글 상세 조회
     */
    @GetMapping("/post/{id}/detail")
    public String showDetailPost(@PathVariable long id,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 RedirectAttributes redirectAttributes,
                                 Model model, @ModelAttribute("replyDto") ReplyDto replyDto,
                                 @AuthUser Account account, @AuthenticationPrincipal SecurityUser securityUser) {

        Post post = postService.findById(id);
//        String postImage = postService.callImage(id);
//        log.info("se name : " + securityUser.getUsername());

        if (!post.isPrivateStatus()) {
            if (securityUser == null || !account.getMember().getNickname().equals(post.getMember().getNickname())) {
                redirectAttributes.addFlashAttribute("accessError", "비공개 글에는 접근할 수 없습니다.");
                return "redirect:/post";
            }
        }
        postService.refreshTime1(post);
        replyService.refreshTime(post.getReplyList());

        Page<Reply> paging = replyService.getReplyList(page, id);

        model.addAttribute("post", post);
//        model.addAttribute("postImage", postImage);
        model.addAttribute("paging", paging);

        if (account != null) {
            boolean likeFlag = likeApiService.existPostLikeFlag(account.getMember(), post);
            model.addAttribute("likeFlag", likeFlag);
        }

        return "post/post-detail";
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping("/post/list")
    public String showAllPosts(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "title") String searchType,
                               Model model,
                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 12) Pageable pageable) {

        model.addAttribute("searchType", SearchType.values());

        // 페이징
        Page<Post> paging = postService.getList(keyword, page, searchType, pageable);
        model.addAttribute("paging", paging);

        //게시글 리스트
        List<Post> postList = postService.findAll();
        model.addAttribute("postList", postList);
        postService.refreshTime(postList);

        return "post/post-list";
    }


    /**
     * 게시글 등록
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/new")
    public String newPost(Model model) {
        model.addAttribute("postCreateDto", new PostDto());
        return "post/post-newForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/new")
    public String createPost(@Valid PostDto postDto, BindingResult bindingResult,
                             Model model, Principal principal){
        if (bindingResult.hasErrors()) {
            model.addAttribute("postCreateDto", postDto);
            return "post/post-newForm";
        }
        Member currentMember = accountService.findAccountByUsername(principal.getName()).getMember();
        Post newPost = postService.savePost(currentMember , postDto);

        return String.format("redirect:/post/%s/detail",newPost.getId());
    }


    /**
     * 게시글 수정
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/modify/{id}")
    public String updatePost(@PathVariable Long id, Model model) throws IOException {
        Post post = postRepository.findById(id).orElse(null);
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .placeName(post.getPlace().getPlaceName())
                .build();
        model.addAttribute("postUpdateDto", postDto);
        return "post/post-updateForm";
    }

    @PutMapping("post/update/{id}")
    public String updatePost(@PathVariable Long id, PostDto postDto) {
        Post post = postService.findById(id);
        postService.updatePost(post,postDto);
        postRepository.save(post);
        return "redirect:/post/{id}/detail";
    }

    /**
     * 게시글 삭제
     */
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/post/list";
    }

}
