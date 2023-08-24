package katachi.spring.exercise.domain.user.service;

import java.util.List;

import katachi.spring.exercise.domain.user.model.OrderDetail;

public interface OrderDetailService {

	public void orderPlacement(OrderDetail orderDetail);
	public List<OrderDetail> getOrderDetails(int orderId);
}
