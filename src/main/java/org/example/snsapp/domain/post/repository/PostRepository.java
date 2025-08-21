package org.example.snsapp.domain.post.repository;

import org.example.snsapp.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
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
}
