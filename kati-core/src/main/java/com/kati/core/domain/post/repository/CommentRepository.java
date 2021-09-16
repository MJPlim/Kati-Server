package com.kati.core.domain.post.repository;

import com.kati.core.domain.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countAllByUserId(Long userId);

}
