package org.example.snsapp.global.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.snsapp.global.constant.Const;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {
    /**
     * 세션 유저 이메일 조회
     *
     * @param session 현재 세션
     * @return 로그인된 유저의 이메일
     */
    public static Optional<String> getLoginUserEmailBySession(HttpSession session) {
        if (session == null) return Optional.empty();
        return Optional.of((String) session.getAttribute(Const.LOGIN_USER));
    }

    /**
     * 로그인 유저 이메일 확인
     *
     * @param request HTTP 정보
     * @return 로그인된 유저의 이메일
     */
    public static Optional<String> getLoginUserEmailByServlet(HttpServletRequest request) {
        return getLoginUserEmailBySession(request.getSession(false));
    }
}