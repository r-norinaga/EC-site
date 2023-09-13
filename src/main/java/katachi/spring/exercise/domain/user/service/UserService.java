package katachi.spring.exercise.domain.user.service;

import java.util.List;

import katachi.spring.exercise.domain.user.model.User;
import katachi.spring.exercise.domain.user.model.UserPaymentInfo;

public interface UserService {
	public void signup(User user);
	public boolean overlappingCheckName(String userName);
	public boolean overlappingCheckEmail(String emailAddress);


	public User getLoginUser(String userName);
	public User getLoginUserById(int userId);

	public List<UserPaymentInfo> getUserPaymentInfo(int userId);
	public UserPaymentInfo getUserPaymentInfoByCreditCardNumber(String creditCardNumber);
	public void deleteUserPaymentInfo(UserPaymentInfo userPaymentInfo);
	public void userInfoChange(User user);

	public void deleteUserInfo(User user);

	public void insertUserPaymentInfo(UserPaymentInfo userPaymentInfo);
	public void updateUserPaymentInfo(UserPaymentInfo userPaymentInfo);

}
