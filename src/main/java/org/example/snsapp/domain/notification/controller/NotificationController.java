package org.example.snsapp.domain.notification.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.notification.dto.NotificationResponse;
import org.example.snsapp.domain.notification.service.NotificationService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.exception.CustomException;
import org.example.snsapp.global.util.SessionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    /**
     * 로그인 유저의 전체 알람 확인
     *
     * @param request HTTP 요청 정보
     * @return HTTP 상태 코드와 알람 기본 응답 DTO의 List
     */
    @GetMapping("/v1/notifications")
    public ResponseEntity<List<NotificationResponse>> findAllNotificationReceivedByLoginUser(HttpServletRequest request) {
        // 로그인 유저 이메일 확인
        String loginUserEmail = getSessionEmail(request);

        return new ResponseEntity<>(notificationService.findAllNotificationReceivedByEmail(loginUserEmail), HttpStatus.OK);
    }

    /**
     * 알람 삭제
     *
     * @param notificationId 알람 아이디
     * @param request        HTTP 요청 정보
     * @return HTTP 상태 코드
     */
    @DeleteMapping("/v1/notifications/{notificationId}")
    public ResponseEntity<Void> delete(@PathVariable Long notificationId,
                                       HttpServletRequest request) {
        String loginUserEmail = getSessionEmail(request);

        notificationService.delete(notificationId, loginUserEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * HTTPServlet 으로부터 이메일을 받아온다.
     *
     * @param request HTTP 요청 정보
     * @return 이메일
     */
    String getSessionEmail(HttpServletRequest request) {
        return SessionUtils.getLoginUserEmailByServlet(request).
                orElseThrow(() -> new CustomException(ErrorCode.NEED_AUTH));
    }
}
