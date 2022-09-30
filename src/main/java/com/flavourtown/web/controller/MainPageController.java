package com.flavourtown.web.controller;

import com.flavourtown.domain.post.SearchType;
import com.flavourtown.service.PlaceService;
import com.flavourtown.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainPageController {

    private final PostService postService;
    private final PlaceService placeService;

    @GetMapping("/")
    public String mainPageMapping(Model model) {
        model.addAttribute("searchType", SearchType.values());
        model.addAttribute("top5Post", postService.findTop5Post());
        model.addAttribute("top5Place", placeService.findTop5Place());
        return "index";
    }

    @GetMapping("/error-page")
    public String errorNotFoundPage() {
        return "error-page";
    }
}
