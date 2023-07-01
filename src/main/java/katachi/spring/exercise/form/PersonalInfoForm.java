package katachi.spring.exercise.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonalInfoForm implements Serializable {

	private int userId;

	@NotBlank
	private	 String addressee;

	@NotBlank
	@Email
	private String emailAddress;

	@NotBlank
	private String shippingAddress;

	@NotBlank
	@Length(min = 11, max = 11)
	@Pattern(regexp = "^[0-9]+$")
	private String mobilePhoneNumber;


}
