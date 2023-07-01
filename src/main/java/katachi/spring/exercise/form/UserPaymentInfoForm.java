package katachi.spring.exercise.form;

import java.time.YearMonth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data

public class UserPaymentInfoForm {
	private int userId;
	@NotBlank
	@Length(min = 16, max = 16)
	@Pattern(regexp = "^[0-9]+$")
	private String creditCardNumber;
	@NotBlank
	private String creditCardUserName;
	@NotNull
	private YearMonth creditCardExpirationMonth;
	@NotBlank
	private String creditCardVerificationCode;

}
