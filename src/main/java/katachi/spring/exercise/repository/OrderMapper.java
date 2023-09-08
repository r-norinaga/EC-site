package katachi.spring.exercise.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import katachi.spring.exercise.domain.user.model.Order;

@Mapper
public interface OrderMapper {

	@Options(useGeneratedKeys=true, keyColumn="order_id")
//	@Insert("insert into orders(order_id, user_id, email_address, shipping_address, billing_address, credit_card_number, credit_card_user_name, credit_card_expiration_month, credit_card_verification_code, order_date_time) values(#{order.orderId}, #{order.userId}, #{order.emailAddress}, #{order.shippingAddress}, #{order.billingAddress}, #{order.creditCardNumber}, #{order.creditCardUserName}, #{order.creditCardExpirationMonth}, #{order.creditCardVerificationCode}, #{order.orderDateTime})")
	public void insertOne(@Param("order")Order order);
	public void insertOneWithoutUserId(@Param("order")Order order);
	public Order getOrder(@Param("orderId")int orderId);
	public void cancelOrder(@Param("userId")int userId, @Param("orderId")int orderId);
}
