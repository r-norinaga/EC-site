package katachi.spring.exercise.domain.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.User;
import katachi.spring.exercise.domain.user.model.UserPaymentInfo;
import katachi.spring.exercise.domain.user.service.UserService;
import katachi.spring.exercise.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper userMapper;




	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void signup(User user) {
		String rawPassword = user.getPassword();
		user.setPassword(passwordEncoder.encode(rawPassword));
		userMapper.insertOne(user);
	}

	@Override
	public boolean overlappingCheckName(String userName) {
		if(userMapper.overlappingCheckName(userName) == null) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public boolean overlappingCheckEmail(String emailAddress) {
		if(userMapper.overlappingCheckEmail(emailAddress) == null) {
			return false;
		} else {
			return true;
		}
	}


	@Override
	public User getLoginUser(String userName) {
		return userMapper.findLoginUser(userName);
	}

	@Override
	public User getLoginUserById(int userId) {
		return userMapper.findLoginUserById(userId);
	}

	@Override
	public UserPaymentInfo getUserPaymentInfo(int userId) {
		return userMapper.findUserPaymentInfo(userId);
	}

	@Override
	public void userInfoChange(User user) {
		String rawPassword = user.getPassword();
		user.setPassword(passwordEncoder.encode(rawPassword));
		userMapper.updateUserInfo(user);
	}

	@Override
	public void insertUserPaymentInfo(UserPaymentInfo userPaymentInfo) {
		userMapper.insertUserPaymentInfo(userPaymentInfo);
	}

	@Override
	public void updateUserPaymentInfo(UserPaymentInfo userPaymentInfo) {
		userMapper.updateUserPaymentInfo(userPaymentInfo);
	}
}
