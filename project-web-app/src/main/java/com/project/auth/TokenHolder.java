package com.project.auth;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope // każdy ma swój token
public class TokenHolder {
    private String token;
    public void clear () {
        this.token = null;
    }
}
