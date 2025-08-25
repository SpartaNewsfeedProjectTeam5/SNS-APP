package org.example.snsapp.domain.post.repository;

import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
                SELECT p
                FROM Post p
                JOIN p.user u
                WHERE
                    (:type = 'date' AND p.createdAt >= :start_date AND p.createdAt <= :end_date)
            """)
    Page<Post> search(
            @Param("start_date") LocalDateTime startDate,
            @Param("end_date") LocalDateTime endDate,
            @Param("type") String type,
            Pageable pageable
    );

    @Query("""
                SELECT p
                FROM Post p
                JOIN p.user u
                WHERE
                    (:keyword = '' OR
                    (:type = 'title' AND p.title LIKE %:keyword%) OR
                    (:type = 'content' AND p.content LIKE %:keyword%) OR
                    (:type = 'email' AND u.email LIKE %:keyword%))
            """)
    Page<Post> search(
            @Param("keyword") String keyword,
            @Param("type") String type,
            Pageable pageable
    );

    Page<Post> findAllByUserEmailOrderByCreatedAtDesc(String email, Pageable pageable);

    Page<Post> findByUserIn(List<User> users, Pageable pageable);

    /**
     * 게시물 아이디로 게시물을 조회
     *
     * @param postId 게시물 아이디
     * @return 게시물 엔티티
     */
    default Post findPostByIdOrThrow(Long postId) {
        return findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }
}
