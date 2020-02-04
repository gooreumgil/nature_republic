package com.daeboo.naturerepublic.serializable;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.ItemSrc;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ItemSerializable implements Serializable {

    private Long id;
    private String nameKor;
    private int price;
    private int discountPrice;
    private String s3Key;

    public ItemSerializable(Item item) {
        this.id = item.getId();
        this.nameKor = item.getNameKor();
        this.price = item.getPrice();
        this.discountPrice = item.getDiscountPrice();
        this.s3Key = item.getItemSrcs().get(0).getS3Key();
    }

    @Override
    public String toString() {
        return "ItemSerializable{" +
                "id=" + id +
                ", nameKor='" + nameKor + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", s3Key='" + s3Key + '\'' +
                '}';
    }
}
