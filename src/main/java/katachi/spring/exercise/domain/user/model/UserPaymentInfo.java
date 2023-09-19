package katachi.spring.exercise.domain.user.model;

import java.time.YearMonth;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserPaymentInfo {
	StringBuilder sb = new StringBuilder();
	private int userId;
	@NotBlank
	private String creditCardNumber;

	@NotBlank
	private String creditCardUserName;

	@NotBlank
	private YearMonth creditCardExpirationMonth;

	@NotBlank
	private String creditCardVerificationCode;
	private String creditCardNumberAsterisked;

	public String asteriskingCreditCardNumber() {
		if(this.creditCardNumber.length() == 16) {

			sb.append(this.creditCardNumber);
			sb.replace(0, 12, "************");
			this.creditCardNumberAsterisked = sb.toString();
			sb.delete(0, sb.length());

		}
		return this.creditCardNumberAsterisked;

	}

}
