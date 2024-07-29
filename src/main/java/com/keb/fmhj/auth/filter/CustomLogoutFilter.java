package com.keb.fmhj.auth.filter;

import com.keb.fmhj.auth.repository.RefreshRepository;
import com.keb.fmhj.auth.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    // 실제 커스텀 로그아웃 구현
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 로그아웃 검증 - 경로가 logout인지, POST 요청인지
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        // refresh token을 가져옴
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        // refresh token 없으면 BAD REQUEST
        if (refresh == null) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // refresh token 만료되면 BAD REQUEST
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 토큰 종류가 refresh token이 아니면 BEAD REQUEST (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // DB에 refresh token 없으면(이미 로그아웃된 상태) BAD REQUEST
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 실제 로그아웃 진행
        // DB에서 refresh token 제거
        refreshRepository.deleteByRefresh(refresh);

        // refresh token Cookie 값 0
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}