package org.example.snsapp.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.comment.dto.CommentLikeResponse;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;


    // 댓글 작성
    @Transactional
    public CommentResponse createComment(Long postId, String userEmail, CommentRequest request) {
        User user = findUserByEmail(userEmail);
        Post post = findPostById(postId);

        Comment comment = Comment.createComment(user, post, request.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public List<CommentResponse> getComments(Long postId, int page, int size, String sort, String direction) {
        findPostById(postId);

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        Page<Comment> commentPage = commentRepository.findByPostId(postId, pageable);

        return commentPage.getContent()
                .stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, String userEmail, CommentRequest request) {
        Comment comment = validateCommentAccess(postId, commentId, userEmail);

        comment.updateContent(request.getContent());
        return CommentResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, String userEmail) {
        Comment comment = validateCommentAccess(postId, commentId, userEmail);


        commentRepository.delete(comment);
    }

    @Transactional
    public CommentLikeResponse createCommentLike(Long postId, Long commentId, String userEmail) {
        CommentLikeValidation validation = validateCommentLikeAccess(postId, commentId, userEmail);

        if (!likeRepository.findByUserAndTypeAndTypeId(validation.user(), LikeContentType.COMMENT, commentId).isEmpty()) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }

        Like commentLike = Like.createCommentLike(validation.user(), commentId);
        likeRepository.save(commentLike);

        return CommentLikeResponse.likeCreated();
    }

    @Transactional
    public CommentLikeResponse deleteCommentLike(Long postId, Long commentId, String userEmail) {
        CommentLikeValidation validation = validateCommentLikeAccess(postId, commentId, userEmail);

        if (likeRepository.findByUserAndTypeAndTypeId(validation.user(), LikeContentType.COMMENT, commentId).isEmpty()) {
            throw new CustomException(ErrorCode.LIKE_NOT_FOUND);
        }

        likeRepository.deleteByUserAndTypeAndTypeId(validation.user(), LikeContentType.COMMENT, commentId);
        return CommentLikeResponse.likeRemoved();
    }

    private record CommentLikeValidation(User user) {
    }


    private CommentLikeValidation validateCommentLikeAccess(Long postId, Long commentId, String userEmail) {
        findPostById(postId);
        findCommentById(commentId);
        User user = findUserByEmail(userEmail);
        return new CommentLikeValidation(user);
    }

    private Comment validateCommentAccess(Long postId, Long commentId, String userEmail) {
        findPostById(postId);
        Comment comment = findCommentById(commentId);
        validateCommentAuthor(comment, userEmail);
        return comment;
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    }

    private void validateCommentAuthor(Comment comment, String userEmail) {
        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
        }
    }
}


