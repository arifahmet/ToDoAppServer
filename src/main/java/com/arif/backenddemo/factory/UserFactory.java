package com.arif.backenddemo.factory;

import com.arif.backenddemo.domain.User;
import com.arif.backenddemo.model.SecurityUser;
import org.springframework.security.core.authority.AuthorityUtils;

public class UserFactory {

  public static SecurityUser create(User user) {
    return new SecurityUser(
      user.getId(),
      user.getUsername(),
      user.getPassword(),
      AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
    );
  }

}
