package com.kantar.template.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kantar.template.entity.UserInfo;

@Repository	
public interface IUserInfoRepository extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByEmail(String username);

}
