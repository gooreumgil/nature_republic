package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.QItem;
import com.daeboo.naturerepublic.dto.ItemSearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daeboo.naturerepublic.domain.QItem.item;

public interface ItemDslRepository {

    Page<Item> findItem(ItemSearchDto itemSearchDto, Pageable pageable);

}

@Repository
class ItemDslRepositoryImpl extends QuerydslRepositorySupport implements ItemDslRepository {

    public ItemDslRepositoryImpl() {
        super(Item.class);
    }

    @Override
    public Page<Item> findItem(ItemSearchDto itemSearchDto, Pageable pageable) {
        JPQLQuery<Item> query = from(item);
        query = this.getQuerydsl().applyPagination(pageable, query);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(Strings.isNotEmpty(itemSearchDto.getName())) {
            booleanBuilder.and(item.nameKor.like("%" + itemSearchDto.getName() + "%"));
        }

        List<Item> itemList = query.where(booleanBuilder).fetch();
        long count = query.where(booleanBuilder).fetchCount();

        return new PageImpl<>(itemList, pageable, count);
    }
}
