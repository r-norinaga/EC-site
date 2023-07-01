package katachi.spring.exercise.domain.user.model;

import java.io.Serializable;
import java.time.YearMonth;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;
@Data
@Component

@SessionScope
public class InfoAdministrater implements Serializable {
	StringBuilder sb = new StringBuilder();
	private int orderId;
	private int userId;
	private String addressee;

//	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss.SSS")
//	private LocalDateTime orderDateTime;
	private String creditCardNumber;
	private String creditCardUserName;
	private YearMonth creditCardExpirationMonth;
	private String creditCardVerificationCode;
	private String emailAddress;
	private String shippingAddress;
	private String mobilePhoneNumber;
	private String creditCardNumberAsterisked;
	private String creditCardVerificationCodeAsterisked;


	public String asteriskingCreditCardNumber() {
		if(this.creditCardNumber.length() == 16) {

			sb.append(this.creditCardNumber);
			sb.replace(0, 12, "************");
			this.creditCardNumberAsterisked = sb.toString();
			sb.delete(0, sb.length());

		}
		return this.creditCardNumberAsterisked;

	}

	public String asteriskingCreditCardVerificationCode() {
		if(this.creditCardVerificationCode.length() == 3) {

			sb.append(this.creditCardVerificationCode);
			sb.replace(0, 3, "***");
			this.creditCardVerificationCodeAsterisked = sb.toString();
			sb.delete(0, sb.length());

		}
		return this.creditCardVerificationCodeAsterisked;

	}
}
