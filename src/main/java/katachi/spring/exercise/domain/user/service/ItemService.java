package katachi.spring.exercise.domain.user.service;

import java.util.List;

import katachi.spring.exercise.domain.user.model.Item;

public interface ItemService {
	public List<Item> getItemList();
	public Item getItem(int id);
}
