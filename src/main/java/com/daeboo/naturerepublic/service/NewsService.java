package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsService {


//    public void save(NewsDto.CreateForm newsDto) {
//
//        newsDto.toNews();
//
//    }
}
