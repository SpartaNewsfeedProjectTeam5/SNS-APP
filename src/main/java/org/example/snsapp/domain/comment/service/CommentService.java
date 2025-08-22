package org.example.snsapp.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.snsapp.domain.comment.dto.CommentRequest;
import org.example.snsapp.domain.comment.dto.CommentResponse;
import org.example.snsapp.domain.comment.entity.Comment;
import org.example.snsapp.domain.comment.repository.CommentRepository;
import org.example.snsapp.domain.like.entity.Like;
import org.example.snsapp.domain.like.repository.LikeRepository;
import org.example.snsapp.domain.post.entity.Post;
import org.example.snsapp.domain.post.repository.PostRepository;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.repository.UserRepository;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.enums.LikeContentType;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 댓글 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    /**
     * 댓글 작성
     */
    @Transactional
    public CommentResponse createComment(Long postId, String userEmail, CommentRequest request) {
        User user = findUserByEmail(userEmail);
        Post post = findPostById(postId);

        Comment comment = Comment.createComment(user, post, request.getContent());
        Comment savedComment = commentRepository.save(comment);

        post.increaseCommentCount();

        return CommentResponse.create(savedComment);
    }

    /**
     * 게시글의 댓글 목록 조회 (페이징, 정렬)
     */
    public List<CommentResponse> getComments(Long postId, int page, int size, String sort, Sort.Direction direction) {
        findPostById(postId); // 게시글 존재 여부 확인

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<Comment> commentPage = commentRepository.findByCommentIdWithUser(postId, pageable);

        return commentPage.getContent()
                .stream()
                .map(CommentResponse::create)
                .collect(Collectors.toList());
    }

    /**
     * 댓글 수정 (작성자만 가능)
     */
    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, String userEmail, CommentRequest request) {
        Comment comment = validateCommentAccess(postId, commentId, userEmail);
        comment.updateContent(request.getContent());
        return CommentResponse.create(comment);
    }

    /**
     * 댓글 삭제 (작성자만 가능)
     */
    @Transactional
    public void deleteComment(Long postId, Long commentId, String userEmail) {
        Comment comment = validateCommentAccess(postId, commentId, userEmail);
        Post post = findPostById(postId);
        post.decreaseCommentCount();

        commentRepository.delete(comment);
    }

    /**
     * 댓글 좋아요 추가
     */
    @Transactional
    public CommentResponse createCommentLike(Long postId, Long commentId, String userEmail) {
        validateCommentLikeBase(postId, commentId);
        User user = findUserByEmail(userEmail);
        Comment comment = findCommentById(commentId);

        // 자기 댓글에 좋아요 금지
        if(MatchAuthorEmail(comment,userEmail))
            throw new CustomException(ErrorCode.COMMENT_LIKE_PERMISSION_ERROR);


        // 중복 좋아요 체크
        if (likeRepository.findByUserAndTypeAndTypeId(user, LikeContentType.COMMENT, commentId).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }

        // 좋아요 수 카운트
        comment.increaseLikeCount();

        Like commentLike = Like.createCommentLike(user, commentId);
        likeRepository.save(commentLike);

        return CommentResponse.create(comment);
    }

    /**
     * 댓글 좋아요 삭제
     */
    @Transactional
    public CommentResponse deleteCommentLike(Long postId, Long commentId, String userEmail) {
        validateCommentLikeBase(postId, commentId);
        User user = findUserByEmail(userEmail);
        Comment comment = findCommentById(commentId);

        // 좋아요 존재 여부 체크
        if (likeRepository.findByUserAndTypeAndTypeId(user, LikeContentType.COMMENT, commentId).isEmpty()) {
            throw new CustomException(ErrorCode.LIKE_NOT_FOUND);
        }

        // 좋아요 수 카운트
        comment.decreaseLikeCount();

        likeRepository.deleteByUserAndTypeAndTypeId(user, LikeContentType.COMMENT, commentId);
        return CommentResponse.create(comment);
    }

    // === 검증 메서드들 ===

    /**
     * 댓글 좋아요 기본 검증 (게시글, 댓글 존재 여부)
     */
    private void validateCommentLikeBase(Long postId, Long commentId) {
        findPostById(postId);
        findCommentById(commentId);
    }

    /**
     * 댓글 접근 권한 검증 (작성자 확인 포함)
     */
    private Comment validateCommentAccess(Long postId, Long commentId, String userEmail) {
        findPostById(postId);
        Comment comment = findCommentById(commentId);
        validateCommentAuthor(comment, userEmail);
        return comment;
    }

    // === 조회 메서드들 ===

    /**
     * 댓글 ID로 댓글 조회
     */
    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    /**
     * 이메일로 사용자 조회
     */
    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 게시글 ID로 게시글 조회
     */
    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    /**
     * 댓글 작성자 권한 확인
     */
    private void validateCommentAuthor(Comment comment, String userEmail) {
        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
        }
    }

    private boolean MatchAuthorEmail(Comment comment, String userEmail) {
        return Objects.equals(comment.getUser().getEmail(), userEmail);
    }
}