package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.*;
import com.daeboo.naturerepublic.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final CategoryItemService categoryItemService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final LikesService likesService;
    private final QnaService qnaService;
    private final ReviewService reviewService;
    private final LikeReviewService likeReviewService;

    // TODO 이 망할 거 바꾸어야 하는
    private final LinkedHashMap<String, String> sortList;

//    private final List<CategoryDto.NewLine> categoryList;

    @GetMapping("/search")
    public String itemSearch(@PageableDefault(size = 12, direction = Sort.Direction.DESC, sort = "nameKor") Pageable pageable, ItemSearchDto itemSearchDto, Model model) {

        Page<ItemDto.Search> searches = itemService.itemSearch(pageable, itemSearchDto);

        model.addAttribute("itemSearchDtos", searches);
        return "item/search";

    }

    @GetMapping
    public String itemIndex(Model model, @PageableDefault(size = 12, page = 0, direction = Sort.Direction.DESC, sort = "item.likesCount") Pageable pageable,
                            String currentCategory, Integer offset) {

        model.addAttribute("offset", offset);

        if (currentCategory == null) {
            currentCategory = "ALL";
        }

        List<Category> all = categoryService.findAll();
        List<CategoryDto.NewLine> categoryList = all.stream().map(category -> {
            return new CategoryDto.NewLine(category);
        }).collect(Collectors.toList());

        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("categories", categoryList);

        // sort List
        model.addAttribute("sorts", sortList);

        //  Best Items
        Page<CategoryItem> bestItems = categoryItemService
                .findALLByCategoryName("ALL", PageRequest.of(0, 4, Sort.Direction.DESC, "item.likesCount"));
        Page<CategoryItemDto.ListView> topBest = bestItems.map(CategoryItemDto.ListView::new);

        model.addAttribute("populars", topBest);

        // Item Count
        int itemCount = categoryItemService.countAllByCategoryName(currentCategory);
        model.addAttribute("itemCount", itemCount);

        // Item List
        Page<CategoryItem> itemList = categoryItemService.findALLByCategoryName(currentCategory, pageable);
        Page<CategoryItemDto.ListView> itemPages = itemList.map(CategoryItemDto.ListView::new);
        model.addAttribute("categoryItems", itemPages);

        return "item/index";

    }

    @GetMapping("/detail")
    public String itemDetail(@ModelAttribute("qnaDto") QnaDto.RequestForm qnaDto,
                             @ModelAttribute("qnaCommentDto") QnaDto.RequestComment qnaCommentDto,
                             @ModelAttribute("commentUpdateDto") CommentDto.RequestCommentUpdate commentUpdateDto,
                             Long id, String currentCategory, HttpServletRequest request,
                             Principal principal, Model model) {

        String tab = null;
        int offsetY = 0;

        // tab 셋팅
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {

            Object tabName = inputFlashMap.get("tab");
            Object yOffset = inputFlashMap.get("yOffset");

            if (tabName != null) {
                tab = tabName.toString();
            }

            if (yOffset != null) {
                offsetY = Integer.parseInt(yOffset.toString());
            }

        }

        // yOffset 셋팅

        if (tab == null) {
            tab = "info";
        }

        model.addAttribute("tab", tab);
        model.addAttribute("yOffset", offsetY);

        // QNA
        Page<Qna> qnaList = qnaService.findAllByItemId(id, PageRequest.of(0, 10, Sort.Direction.DESC, "wroteAt"));
        Page<QnaDto.ItemDetail> itemDetails = qnaList.map(QnaDto.ItemDetail::new);

        model.addAttribute("qnaReponseDtos", itemDetails);

        if (currentCategory == null) {
            currentCategory = "ALL";
        }

        // like 누른 상품인지 아닌지 체크
        boolean likeTrueOrFalse = false;

        if (principal != null) {
            Member member = memberService.findByName(principal.getName());
            Likes likes = likesService.findByMemberIdAndItemId(member.getId(), id);

            model.addAttribute("memberId", member.getId());

            if (likes != null) {
                likeTrueOrFalse = true;
            } else {
                likeTrueOrFalse = false;
            }
        }

        model.addAttribute("like", likeTrueOrFalse);

        // 상품정보
        Item findItem = itemService.findById(id);
        ItemDto.Detail detail = new ItemDto.Detail(findItem);

        // 구매후기
        Page<Review> reviewList = reviewService.findAllByItemId(findItem.getId(), PageRequest.of(0, 10, Sort.Direction.DESC, "wroteAt"));
        Page<ReviewResponseDto> reviewDtos = reviewList.map(ReviewResponseDto::new);

        model.addAttribute("item", detail);
        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("reviewDtos", reviewDtos);

        String mainCategory = detail.getMainCategory();
        Page<CategoryItem> allByCategoryName = categoryItemService.findALLByCategoryName(mainCategory, PageRequest.of(0, 4, Sort.Direction.DESC, "item.likesCount"));
        Page<CategoryItemDto.ListView> allByCategoryNamePages = allByCategoryName.map(CategoryItemDto.ListView::new);

        model.addAttribute("categoryBests", allByCategoryNamePages);
        model.addAttribute("newLineChar", '\n');

        return "item/detail";

    }

    @PostMapping("likes")
    public String addLikes(@RequestParam("itemId") Long itemId, @RequestParam("type") String type, Principal principal, HttpServletRequest request) {

        Item item = itemService.findById(itemId);

        String name = principal.getName();
        Member member = memberService.findByName(name);

        if (type.equals("add")) {
            likesService.addLikes(item, member);
        } else if (type.equals("remove")) {
            likesService.remove(member.getId(), item);
            item.minusLikes();
        }

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;

    }




}
