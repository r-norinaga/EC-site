package katachi.spring.exercise.form;

import java.io.Serializable;
import java.time.YearMonth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PaymentInfoForm implements Serializable {
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
