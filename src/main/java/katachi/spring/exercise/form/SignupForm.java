package katachi.spring.exercise.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupForm {

	private int userId;
	@NotBlank
	private String userName;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;
	@NotBlank
	private String surname;
	@NotBlank
	private String givenName;
	@NotBlank
	@Email
	private String emailAddress;
	@NotBlank
	private String homeAddress;

	@NotBlank
	@Length(min = 11, max = 11)
	@Pattern(regexp = "^[0-9]+$")
	private String mobilePhoneNumber;

	private String addressee;

	public String settingAddressee() {
		this.addressee = this.surname + " " + this.givenName;
		return this.addressee;
	}


}
