package surfer.backend.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    GUEST, USER;

    @Override
    public String getAuthority() {
        return name();
    }

}
