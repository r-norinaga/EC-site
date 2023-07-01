package katachi.spring.exercise.domain.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.OrderDetail;
import katachi.spring.exercise.domain.user.service.OrderDetailService;
import katachi.spring.exercise.repository.OrderDetailMapper;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailMapper orderDetailMapper;

	@Override
	public void orderPlacement(OrderDetail orderDetail){
		orderDetailMapper.insertOne(orderDetail);
	}

}
