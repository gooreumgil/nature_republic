package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.time.LocalDateTime;

public class NewsDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateForm {

        private String title;
        private String content;
        private MultipartFile img;
        private LocalDateTime wroteAt;

        public News toNews() {
            String imgPath = img.getOriginalFilename() + " ";
            wroteAt = LocalDateTime.now();
            return News.createNews(title, content, imgPath, wroteAt);
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Home {
        private String title;
        private String content;
        private String imgPath;
        private LocalDateTime wroteAt;

        public Home(News news) {
            this.title = news.getTitle();
            this.content = news.getContent();
            this.imgPath = news.getImgPath();
            this.wroteAt = news.getWroteAt();
        }
    }
}
