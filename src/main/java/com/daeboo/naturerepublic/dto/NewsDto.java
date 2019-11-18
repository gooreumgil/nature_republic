package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.time.LocalDateTime;

public class NewsDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class CreateForm {

        private String title;
        private Blob content;
        private MultipartFile img;
        private LocalDateTime wroteAt;

//        public News toNews() {
//            News news = News.createNews(title, content, img, wroteAt);
//        }
    }

}
