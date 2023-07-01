package katachi.spring.exercise.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import katachi.spring.exercise.domain.user.model.User;
import katachi.spring.exercise.domain.user.model.UserPaymentInfo;
@Mapper
public interface UserMapper {
	public void insertOne(@Param("user")User user);
	public String overlappingCheckName(@Param("userName")String userName);
	public String overlappingCheckEmail(@Param("emailAddress")String emailAddress);

	public User findLoginUserById(int userId);
	public User findLoginUser(String userName);

	public UserPaymentInfo findUserPaymentInfo(@Param("userId")int userId);

	public void updateUserInfo(@Param("user")User user);

	public void insertUserPaymentInfo(@Param("userPaymentInfo")UserPaymentInfo userPaymentInfo);

	public void updateUserPaymentInfo(@Param("userPaymentInfo")UserPaymentInfo userPaymentInfo);
}


