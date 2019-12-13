package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
