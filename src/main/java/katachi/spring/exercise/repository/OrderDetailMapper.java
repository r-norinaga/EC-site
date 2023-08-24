package katachi.spring.exercise.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import katachi.spring.exercise.domain.user.model.OrderDetail;

@Mapper
public interface OrderDetailMapper {
	public void insertOne(@Param("orderDetail")OrderDetail orderDetail);
	public List<OrderDetail> findOne(@Param("orderId")int orderId);
}
