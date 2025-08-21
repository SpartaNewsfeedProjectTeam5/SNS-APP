package org.example.snsapp.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.snsapp.global.constant.Const;
import org.example.snsapp.global.enums.ErrorCode;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login", "/users/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        // 화이트 리스트 검사
        if (!isWhiteList(requestURI)) {
            HttpSession httpSession = httpServletRequest.getSession(false);

            if (httpSession == null || httpSession.getAttribute(Const.LOGIN_USER) == null) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorCode.NEED_AUTH.getMessage());
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * URI가 White List에 걸리는지 검사하는 메서드
     *
     * @param requestURI 검사할 URI
     * @return White List에 걸리면 true 반환
     */
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

}