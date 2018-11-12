package com.arif.backenddemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
public class SecurityUser implements UserDetails {

  private Long id;
  private String username;
  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Boolean accountNonExpired = true;
  private Boolean accountNonLocked = true;
  private Boolean credentialsNonExpired = true;
  private Boolean enabled = true;

  public SecurityUser() {
    super();
  }

  public SecurityUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    this.setId(id);
    this.setUsername(username);
    this.setPassword(password);
    this.setAuthorities(authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  @JsonIgnore
  public Boolean getAccountNonExpired() {
    return this.accountNonExpired;
  }

  public void setAccountNonExpired(Boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.getAccountNonExpired();
  }

  @JsonIgnore
  public Boolean getAccountNonLocked() {
    return this.accountNonLocked;
  }

  public void setAccountNonLocked(Boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.getAccountNonLocked();
  }

  @JsonIgnore
  public Boolean getCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.getCredentialsNonExpired();
  }

  @JsonIgnore
  public Boolean getEnabled() {
    return this.enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean isEnabled() {
    return this.getEnabled();
  }
}
