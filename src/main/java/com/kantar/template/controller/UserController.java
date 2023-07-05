package com.kantar.template.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kantar.template.entity.UserInfo;
import com.kantar.template.request.UserInfoRequest;
import com.kantar.template.response.UserInfoDTO;
import com.kantar.template.service.UserInfoService;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping(value = "/user")
	@ResponseStatus(HttpStatus.CREATED)
	public UserInfoDTO addUser(@RequestBody UserInfoRequest userInfo) {
		return userInfoService.addUser(userInfo);
	}

	@GetMapping(value = "/user")
	@ResponseStatus(HttpStatus.OK)
	public List<UserInfoDTO> users() {
		return userInfoService.users();
	}

}
