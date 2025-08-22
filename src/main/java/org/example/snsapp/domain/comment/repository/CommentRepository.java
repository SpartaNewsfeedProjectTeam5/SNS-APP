package org.example.snsapp.domain.comment.repository;

import org.example.snsapp.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 페이징 처리된 댓글 조회 (User 정보 포함)
    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user " +
            "WHERE c.post.id = :postId")
    Page<Comment> findByCommentIdWithUser(@Param("postId") Long postId, Pageable pageable);

}

