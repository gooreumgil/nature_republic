package com.daeboo.naturerepublic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDtoWrapper {

    private Long orderId;
    private Long memberId;
    private List<ReviewDto> reviewDtos = new ArrayList<>();

}
