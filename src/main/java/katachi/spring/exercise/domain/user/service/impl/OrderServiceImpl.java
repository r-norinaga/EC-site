package katachi.spring.exercise.domain.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.service.OrderService;
import katachi.spring.exercise.repository.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;

	@Override
	public void orderPlacement(Order order) {
		 orderMapper.insertOne(order);
	}

	@Override
	public void orderPlacementWithoutUserId(Order order) {
		orderMapper.insertOneWithoutUserId(order);
	}

	@Override
	public Order getOrder(int orderId) {
		return orderMapper.getOrder(orderId);
	}
}
