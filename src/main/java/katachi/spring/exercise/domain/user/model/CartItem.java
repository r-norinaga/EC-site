package katachi.spring.exercise.domain.user.model;

import lombok.Data;

@Data
public class CartItem {
	private Item item;
	private int number;



	public boolean increment(CartItem ci) {
		if(number < this.item.getStock()) {
			number++;
			return true;
		}else {
			return false;
		}
	}

	public boolean decrement() {
		if(number > 0) {
			number--;
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean equals(Object ci) {
		if(((CartItem)ci).item.getId() == this.item.getId()) {
			return true;
		}
		return false;
	}

	public int calculateSum(){

		return item.getPrice() * number;
	}

}
