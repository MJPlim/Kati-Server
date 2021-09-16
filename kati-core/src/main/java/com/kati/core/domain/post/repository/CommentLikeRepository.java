package com.kati.core.domain.post.repository;

import com.kati.core.domain.post.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>{

}
