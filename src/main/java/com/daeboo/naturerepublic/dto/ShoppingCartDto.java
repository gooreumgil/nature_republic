package com.daeboo.naturerepublic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ShoppingCartDto {

    private Long itemId;
    private int count;

}
