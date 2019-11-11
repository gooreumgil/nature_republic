package com.daeboo.naturerepublic;

import com.daeboo.naturerepublic.controller.AdminController;
import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.Birthday;
import com.daeboo.naturerepublic.domain.embeded.PhoneNumber;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ItemRepository itemRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Transactional
    public ApplicationRunner applicationRunner() {

        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {

//                Member member1 = Member.createMember("member1", "password", "email1@email.com",
//                        new Address("city1", "street1", "12345"),
//                        new Birthday(19890407),
//                        new PhoneNumber("010", "1111", "2222"));
//
//
//                Member member2 = Member.createMember("member2", "password", "email2@email.com",
//                        new Address("city2", "street2", "12345"),
//                        new Birthday(19890407),
//                        new PhoneNumber("010", "1111", "2222"));
//
//                memberRepository.save(member1);
//                memberRepository.save(member2);
//
//                createCategoryAndSave("클렌징");
//                createCategoryAndSave("팩&마스크");
//                createCategoryAndSave("베이스 메이크업");
//                createCategoryAndSave("포인트 메이크업");
//                createCategoryAndSave("선케어");
//                createCategoryAndSave("바디&헤어");
//                createCategoryAndSave("남성");
//                createCategoryAndSave("화장소품");
//                createCategoryAndSave("세트");

//                List<Category> categories = new ArrayList<>();
//                String[] categoryNames = {"클렌징", "남성"};
//
//                for (String categoryName : categoryNames) {
//                    Category category = categoryRepository.findByName(categoryName).get();
//                    categories.add(category);
//                }
//
//                categoryRepository.findByName("클렌징");
//
//                Item item1 = Item.createItem("상품1", "item1", 15000, 1000, "제품이다1", 50, categories);
//                Item item2 = Item.createItem("상품2", "item2", 20000, 1000, "제품이다2", 100, categories);
//
//                itemRepository.save(item1);
//                itemRepository.save(item2);


//                boolean mkdir = new File(AdminController.uploadDirectory).mkdir();

            }

            private void createCategoryAndSave(String name) {
                Category category = Category.createCategory(name);
                categoryRepository.save(category);
            }
        };
    }

}
