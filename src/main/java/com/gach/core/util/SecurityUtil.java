package com.gach.core.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class to get current authenticated user information from JWT token.
 */
@Component
public class SecurityUtil {

    /**
     * Get the email/username of the currently authenticated user from JWT token.
     * @return The email of the authenticated user, or null if not authenticated
     */
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && 
            !(authentication instanceof AnonymousAuthenticationToken) && 
            authentication.isAuthenticated()) {
            return authentication.getName();
        }
        
        return null;
    }

    /**
     * Check if the current user email matches the requested email.
     * This is for security - ensure user can only update their own data.
     * @param requestEmail The email from the request
     * @return true if matches, false otherwise
     * @throws IllegalAccessException if user is not authenticated
     */
    public static boolean isAuthorizedForEmail(String requestEmail) throws IllegalAccessException {
        String currentEmail = getCurrentUserEmail();
        
        if (currentEmail == null) {
            throw new IllegalAccessException("User is not authenticated");
        }
        
        return currentEmail.equalsIgnoreCase(requestEmail);
    }

    /**
     * Verify authorization - throws exception if not authorized.
     * @param requestEmail The email to check access for
     * @throws IllegalAccessException if not authorized
     */
    public static void verifyAuthorization(String requestEmail) throws IllegalAccessException {
        if (!isAuthorizedForEmail(requestEmail)) {
            throw new IllegalAccessException("You are not authorized to perform this action. You can only access your own data.");
        }
    }

    /**
     * Check if user is authenticated
     * @return true if authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && 
               !(authentication instanceof AnonymousAuthenticationToken) && 
               authentication.isAuthenticated();
    }
}
