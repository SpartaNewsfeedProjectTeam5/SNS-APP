package org.example.snsapp.domain.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.comment.dto.CommentLikeResponse;
import org.example.snsapp.domain.comment.dto.CommentRequest;
import org.example.snsapp.domain.comment.dto.CommentResponse;
import org.example.snsapp.domain.comment.service.CommentService;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.global.constant.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/v1/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request,
            HttpServletRequest httpRequest) {

        String userEmail = getCurrentUserEmail(httpRequest);
        CommentResponse response = commentService.createComment(postId, userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        List<CommentResponse> comments = commentService.getComments(postId, page, size, sort, direction);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/v2/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            HttpServletRequest httpRequest) {

        String userEmail = getCurrentUserEmail(httpRequest);
        CommentResponse response = commentService.updateComment(postId, commentId, userEmail, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/v2/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {

        String userEmail = getCurrentUserEmail(httpRequest);
        commentService.deleteComment(postId, commentId, userEmail);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v1/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<CommentLikeResponse> createCommentLike(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {

        String userEmail = getCurrentUserEmail(httpRequest);
        CommentLikeResponse response = commentService.createCommentLike(postId, commentId, userEmail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<CommentLikeResponse> deleteCommentLike(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {

        String userEmail = getCurrentUserEmail(httpRequest);
        CommentLikeResponse response = commentService.deleteCommentLike(postId, commentId, userEmail);
        return ResponseEntity.ok(response);
    }


    private String getCurrentUserEmail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Object userObj = session.getAttribute(Const.LOGIN_USER);
        if (userObj == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        return userObj instanceof User ? ((User) userObj).getEmail() : userObj.toString();
    }
}
