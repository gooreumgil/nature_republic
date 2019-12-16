package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.service.LikeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likeReview")
public class LikeReviewController {

    private final LikeReviewService likeReviewService;

    @PostMapping
    public String likeReviewSave(Long reviewId, Long memberId, int yOffset, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        likeReviewService.save(reviewId, memberId);

        redirectAttributes.addFlashAttribute("tab", "reviews");
        redirectAttributes.addFlashAttribute("yOffset", yOffset);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @DeleteMapping("/delete")
    public String likeReviewDelete(Long reviewId, Long memberId, int yOffset, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        likeReviewService.deleteByReviewIdAndMemberId(reviewId, memberId);

        redirectAttributes.addFlashAttribute("tab", "reviews");
        redirectAttributes.addFlashAttribute("yOffset", yOffset);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
