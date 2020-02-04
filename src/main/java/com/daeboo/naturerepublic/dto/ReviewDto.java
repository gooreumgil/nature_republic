package com.daeboo.naturerepublic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

    private Long itemId;
    private ItemDto.ReviewForm itemDto;
    private List<MultipartFile> srcs = new ArrayList<>();
    private List<String> remove = new ArrayList<>();
    private int rating;
    private String content;

}
