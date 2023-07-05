package com.kantar.template.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kantar.template.config.UserInfoUserDetails;
import com.kantar.template.entity.UserInfo;
import com.kantar.template.repository.IUserInfoRepository;
import com.kantar.template.util.Utililty;

@Component
public class UserInfoService implements UserDetailsService {

	@Autowired
	private IUserInfoRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = repository.findByEmail(username);

		return userInfo.map(UserInfoUserDetails::new)
				.orElseThrow(() -> Utililty.usernameNotFoundException("Given user not found : " + username));

	}

}
