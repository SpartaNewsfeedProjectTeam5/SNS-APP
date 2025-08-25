package org.example.snsapp.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.snsapp.global.constant.Const;
import org.example.snsapp.global.enums.ErrorCode;
import org.springframework.util.PatternMatchUtils;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class LoginFilter implements Filter {
    // 인증을 하지 않아도될 URL Path 배열
    private static final String[] WHITE_LIST = {"/api/v1/auth/login", "/api/v1/auth/signup"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                httpResponse.setStatus(ErrorCode.NEED_AUTH.getStatus().value());
                httpResponse.setContentType("application/json;charset=UTF-8");
                httpResponse.getWriter().write(
                        new ObjectMapper().writeValueAsString(
                                Map.of("error", ErrorCode.NEED_AUTH.getStatus(),
                                        "message", ErrorCode.NEED_AUTH.getMessage())
                        )
                );
                return; //필터 체인 종료
            }
        }
        chain.doFilter(request, response);

    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}