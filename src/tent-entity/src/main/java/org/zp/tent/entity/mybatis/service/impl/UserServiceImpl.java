package org.zp.tent.entity.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zp.tent.entity.mybatis.dao.UserMapper;
import org.zp.tent.entity.mybatis.domain.User;
import org.zp.tent.entity.mybatis.service.UserServiceI;

@Service("userService")
public class UserServiceImpl implements UserServiceI {
	@Autowired
	private UserMapper userMapper;

	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}

	@Override
	public User getUserById(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> getAllUser() {
		return userMapper.getAllUser();
	}
}