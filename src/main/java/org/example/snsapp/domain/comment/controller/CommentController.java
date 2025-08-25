package org.example.snsapp.domain.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.snsapp.domain.comment.dto.CommentRequest;
import org.example.snsapp.domain.comment.dto.CommentResponse;
import org.example.snsapp.domain.comment.service.CommentService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.example.snsapp.global.util.SessionUtils; // SessionUtils import
import org.springframework.data.domain.Sort;
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

        String userEmail = SessionUtils.getLoginUserEmailByServlet(httpRequest)
                .orElseThrow(() -> new CustomException(ErrorCode.NEED_AUTH));

        CommentResponse response = commentService.createComment(postId, userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        List<CommentResponse> comments = commentService.getComments(postId, page, size, sort, direction);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            HttpServletRequest httpRequest) {

        String userEmail = SessionUtils.getLoginUserEmailByServlet(httpRequest)
                .orElseThrow(() -> new CustomException(ErrorCode.NEED_AUTH));

        CommentResponse response = commentService.updateComment(postId, commentId, userEmail, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {

        String userEmail = SessionUtils.getLoginUserEmailByServlet(httpRequest)
                .orElseThrow(() -> new CustomException(ErrorCode.NEED_AUTH));

        commentService.deleteComment(postId, commentId, userEmail);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v1/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<CommentResponse> createCommentLike(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {

        String userEmail = SessionUtils.getLoginUserEmailByServlet(httpRequest)
                .orElseThrow(() -> new CustomException(ErrorCode.NEED_AUTH));

        CommentResponse response = commentService.createCommentLike(postId, commentId, userEmail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<CommentResponse> deleteCommentLike(

            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {

        String userEmail = SessionUtils.getLoginUserEmailByServlet(httpRequest)
                .orElseThrow(() -> new CustomException(ErrorCode.NEED_AUTH));

        CommentResponse response = commentService.deleteCommentLike(postId, commentId, userEmail);
        return ResponseEntity.ok(response);
    }
}