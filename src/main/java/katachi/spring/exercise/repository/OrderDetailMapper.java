package katachi.spring.exercise.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import katachi.spring.exercise.domain.user.model.OrderDetail;

@Mapper
public interface OrderDetailMapper {
	public void insertOne(@Param("orderDetail")OrderDetail orderDetail);
}
