package katachi.spring.exercise.domain.user.service;
import katachi.spring.exercise.domain.user.model.Order;
public interface OrderService {
	public void orderPlacement(Order order);
	public void orderPlacementWithoutUserId(Order order);
	public Order getOrder(int orderId);
}
