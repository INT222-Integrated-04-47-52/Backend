package sit.int222.mongkolthorn.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenSecurityUtil {

    private TokenSecurityUtil() {

    }

    public static Long getCurrentAccountId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        Long accountId = (Long) principal;

        return accountId;
    }
}
