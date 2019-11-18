package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.News;
import com.daeboo.naturerepublic.dto.NewsDto;
import com.daeboo.naturerepublic.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public static String uploadDirectotry = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\upload";

    @Transactional
    public void save(NewsDto.CreateForm newsDto) {

        MultipartFile img = newsDto.getImg();

        StringBuilder fileName = new StringBuilder();
        createFile(img, fileName);

        News news = newsDto.toNews();
        newsRepository.save(news);

    }

    private void createFile(MultipartFile img, StringBuilder fileName) {
        Path fileNameAndPath = Paths.get(uploadDirectotry, img.getOriginalFilename());
        fileName.append(img.getOriginalFilename() + " ");

        try {
            Files.write(fileNameAndPath, img.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PageImpl<NewsDto.Home> findAll(Pageable page) {

        Page<News> all = newsRepository.findAll(page);
        List<NewsDto.Home> result = all.stream().map(news -> {
            return new NewsDto.Home(news);
        }).collect(Collectors.toList());

        return new PageImpl<>(result, page, result.size());

    }
}
