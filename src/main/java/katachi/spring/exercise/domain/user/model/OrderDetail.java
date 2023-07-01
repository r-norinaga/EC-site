package katachi.spring.exercise.domain.user.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OrderDetail {
	private int orderDetailId;
	private int orderId;
	private int itemId;
	private int number;
	private LocalDateTime orderDateTime;
}
