package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
