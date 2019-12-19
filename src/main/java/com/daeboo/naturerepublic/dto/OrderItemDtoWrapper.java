package com.daeboo.naturerepublic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class OrderItemDtoWrapper {

    private Long memberId;
    private Integer usePoints;
    private Integer deliveryPrice;
    private int savePoints;
    private String addressee;
    private String phoneNumber;
    private String mainAddress;
    private String detailAddress;
    private String memo;
    List<OrderItemDto.Create> orderItemDtos = new ArrayList<>();

}
