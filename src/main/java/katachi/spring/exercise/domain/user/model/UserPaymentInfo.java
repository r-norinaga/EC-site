package katachi.spring.exercise.domain.user.model;

import java.time.YearMonth;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserPaymentInfo {
	private int userId;
	@NotBlank
	private String creditCardNumber;

	@NotBlank
	private String creditCardUserName;

	@NotBlank
	private YearMonth creditCardExpirationMonth;

	@NotBlank
	private String creditCardVerificationCode;


}
