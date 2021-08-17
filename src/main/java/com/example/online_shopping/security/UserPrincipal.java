package com.example.online_shopping.security;

import com.example.online_shopping.database.domain.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.online_shopping.database.domain.enumeration.STATUS.ACTIVE;

public class UserPrincipal implements UserDetails {

    private Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + this.user.getRole());
        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    public String getRole() {
        return this.user.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.getStatus() == ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.getStatus() == ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.getStatus() == ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getStatus() == ACTIVE;
    }
}
