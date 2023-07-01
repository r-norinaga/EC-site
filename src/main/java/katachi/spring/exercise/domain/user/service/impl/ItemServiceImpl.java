package katachi.spring.exercise.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.Item;
import katachi.spring.exercise.domain.user.service.ItemService;
import katachi.spring.exercise.repository.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;


	@Override
	public List<Item> getItemList(){
		return itemMapper.findMany();
	}

	@Override
	public Item getItem(int id) {
		return itemMapper.findOne(id);
	}
}
