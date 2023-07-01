package katachi.spring.exercise.domain.user.model;

import lombok.Data;

@Data
public class User {
	private int userId;
	private String userName;
	private String password;
	private String surname;
	private String givenName;
	private String emailAddress;
	private String homeAddress;

	private String mobilePhoneNumber;
	private String addressee;
}
