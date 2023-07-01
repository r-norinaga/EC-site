package katachi.spring.exercise.domain.user.service;

import katachi.spring.exercise.domain.user.model.User;
import katachi.spring.exercise.domain.user.model.UserPaymentInfo;

public interface UserService {
	public void signup(User user);
	public boolean overlappingCheckName(String userName);
	public boolean overlappingCheckEmail(String emailAddress);


	public User getLoginUser(String userName);
	public User getLoginUserById(int userId);

	public UserPaymentInfo getUserPaymentInfo(int userId);
	public void userInfoChange(User user);

	public void insertUserPaymentInfo(UserPaymentInfo userPaymentInfo);
	public void updateUserPaymentInfo(UserPaymentInfo userPaymentInfo);

}
