package org.slsale.service.user;

import javax.annotation.Resource;

import org.slsale.dao.user.UserMapper;
import org.slsale.pojo.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;

	@Override
	public User login(User user) throws Exception {
		return userMapper.login(user);
	}

	@Override
	public int register(User user) throws Exception {
		return userMapper.register(user);
	}

}
