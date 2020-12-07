package com.dk98126.mireabankapp.utils;

import com.dk98126.mireabankapp.model.BankUserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ControllerUtils {
    public static void addFieldError(BindingResult bindingResult, String objectName, String field, String message) {
        bindingResult.addError(new FieldError(objectName, field, message));
    }

    public static String redirectIfAuthenticated(String pageToRedirect, String successPage) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
            return "redirect:" + pageToRedirect;
        else return successPage;
    }

    public static String getNameFromSecurityContextHolder() {
        return getAuthenticationFromSecurityContextHolder().getName();
    }

    public static Authentication getAuthenticationFromSecurityContextHolder() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
