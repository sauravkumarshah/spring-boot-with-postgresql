package com.kantar.template.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
	private Integer id;
	private String name;
	private String email;
	private String password;
	private String roles;

}