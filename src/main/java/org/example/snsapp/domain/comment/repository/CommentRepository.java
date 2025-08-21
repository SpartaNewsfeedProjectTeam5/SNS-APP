package org.example.snsapp.domain.comment.repository;

import org.example.snsapp.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 페이징 처리된 댓글 조회 (User 정보 포함)
    @EntityGraph(attributePaths = {"user","post"})
    Page<Comment> findByPostId(Long postId, Pageable pageable);

}

