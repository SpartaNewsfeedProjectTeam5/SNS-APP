package org.example.snsapp.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 값 관련
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "VAL-001", "입력값이 유효하지 않습니다."),

    // 게시물 관련
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "PST-001", "게시물을 찾을 수 없습니다."),

    // 댓글 관련
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CMT-001", "댓글을 찾을 수 없습니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "CMT-002", "댓글에 대한 권한이 없습니다."),

    // 유저 관련
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USR-001", "이미 가입된 이메일입니다."),
    AUTH_ERROR(HttpStatus.UNAUTHORIZED, "USR-002", "아이디 또는 비밀번호가 잘못되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR-003", "유저를 찾을 수 없습니다."),
    NEED_AUTH(HttpStatus.UNAUTHORIZED, "USR-004", "로그인이 필요한 서비스 입니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "USR-005", "권한이 없어 요청을 수행할 수 없습니다."),
    CURRENT_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "USR-006", "현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다."),
    CURRENT_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "USR-007", "현재 비밀번호가 일치하지 않습니다."),
    DELETED_USER(HttpStatus.NOT_FOUND, "USR-008", "탈퇴한 회원입니다."),

    // 좋아요 관련
    ALREADY_LIKED(HttpStatus.CONFLICT, "LIKE-001", "이미 좋아요를 눌렀습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "LIKE-002", "좋아요를 누르지 않았습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}