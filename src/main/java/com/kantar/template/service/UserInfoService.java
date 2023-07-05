package com.kantar.template.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kantar.template.config.UserInfoUserDetails;
import com.kantar.template.entity.UserInfo;
import com.kantar.template.repository.IUserInfoRepository;
import com.kantar.template.request.UserInfoRequest;
import com.kantar.template.response.UserInfoDTO;
import com.kantar.template.util.Utililty;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@Component
public class UserInfoService implements UserDetailsService {

	@Autowired
	private IUserInfoRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ObservationRegistry registry;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = repository.findByEmail(username);

		return userInfo.map(UserInfoUserDetails::new)
				.orElseThrow(() -> Utililty.usernameNotFoundException("Given user not found : " + username));

	}

	public UserInfoDTO addUser(UserInfoRequest userInfoRequest) {
		userInfoRequest.setPassword(encoder.encode(userInfoRequest.getPassword()));

		UserInfo userInfo = UserInfo.builder().name(userInfoRequest.getName()).email(userInfoRequest.getEmail())
				.password(userInfoRequest.getPassword()).roles(userInfoRequest.getRoles()).build();

//		return repository.save(userInfo);

		return Observation.createNotStarted("addUser", registry)
				.observe(() -> Utililty.mapToUserInfoDTO(repository.save(userInfo)));
	}

	public List<UserInfoDTO> users() {
//		return repository.findAll();

		return Observation.createNotStarted("getUsers", registry)
				.observe(() -> repository.findAll().stream().map(Utililty::mapToUserInfoDTO).toList());
	}

}
