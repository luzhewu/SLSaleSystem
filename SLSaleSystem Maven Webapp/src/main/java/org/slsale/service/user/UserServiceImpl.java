package org.slsale.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.user.UserMapper;
import org.slsale.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;

	@Override
	public User getLoginUser(User user) throws Exception {
		return userMapper.getLoginUser(user);
	}

	@Override
	public int loginCodeIsExist(User user) throws Exception {
		return userMapper.loginCodeIsExist(user);
	}

	@Override
	public int modifyUser(User user) throws Exception {
		return userMapper.modifyUser(user);
	}

	@Override
	public int count(User user) throws Exception {
		return userMapper.count(user);
	}

	@Override
	public List<User> getUserList(User user) throws Exception {
		return userMapper.getUserList(user);
	}

}
