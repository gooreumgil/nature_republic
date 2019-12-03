package com.daeboo.naturerepublic;

import com.daeboo.naturerepublic.controller.AdminController;
import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.Birthday;
import com.daeboo.naturerepublic.domain.embeded.PhoneNumber;
import com.daeboo.naturerepublic.dto.CategoryDto;
import com.daeboo.naturerepublic.dto.CategoryItemDto;
import com.daeboo.naturerepublic.repository.CategoryItemRepository;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.MemberRepository;
import com.daeboo.naturerepublic.service.CategoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    @Autowired
    private MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LinkedHashMap<String, String> sortList() {
        LinkedHashMap<String, String> sortList = new LinkedHashMap<>();
        sortList.put("item.likesCount,DESC", "인기상품순");
        sortList.put("item.registerAt,ASC", "등록일순");
        sortList.put("item.price,ASC", "낮은가격순");
        sortList.put("item.price,DESC", "높은가격순");

        return sortList;
    }

//    @Bean
//    public ApplicationRunner applicationRunner() {
//        return new ApplicationRunner() {
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//                Address address = new Address("seoul", "street", "12345");
//                Birthday birthday = new Birthday(19890707);
//                PhoneNumber phoneNumber = new PhoneNumber("010", "1111", "2222");
//
//                List<String> roles = new ArrayList<>();
//                roles.add("ADMIN");
//                roles.add("USER");
//
//                Member member = Member.createMember("admin", "admin", "admin@admin.com", address, birthday, phoneNumber, roles);
//
//                memberRepository.save(member);
//            }
//        };
//    }

}
