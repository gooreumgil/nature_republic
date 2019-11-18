package com.daeboo.naturerepublic.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class News {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;
    private LocalDateTime wroteAt;
    private String imgPath;

    public static News createNews(String title, String content, String imgPath, LocalDateTime wroteAt) {
        News news = new News();
        news.title = title;
        news.content = content;
        news.wroteAt = LocalDateTime.now();
        news.imgPath = imgPath;

        return news;
    }

}
