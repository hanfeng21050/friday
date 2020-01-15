package com.hf.friday.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hf.friday.model.SysPermission;
import com.hf.friday.model.SysUser;
import com.hf.friday.util.StringUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginUser extends SysUser implements UserDetails {

    private List<SysPermission> permissions;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getStatus() != Status.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
