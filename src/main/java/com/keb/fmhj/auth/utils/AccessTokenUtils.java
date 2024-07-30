package com.keb.fmhj.auth.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AccessTokenUtils {

    /**
     * 현재 인증된 사용자의 loginId를 가져오는 메소드.
     * @return 인증된 사용자의 loginId, 인증 정보가 없다면 null을 반환.
     */
    public static String isPermission() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }

        return null;
    }
}
