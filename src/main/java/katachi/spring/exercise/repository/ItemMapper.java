package katachi.spring.exercise.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import katachi.spring.exercise.domain.user.model.Item;

@Mapper
public interface ItemMapper {

	public List<Item> findMany();
	public Item findOne(@Param("id")int id);
}
