package katachi.spring.exercise.domain.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component
@SessionScope
public class Cart implements Serializable {

	private List <CartItem> cartItems = new ArrayList<CartItem>();

	private Cart cart;

	public boolean addToCart(CartItem cartItem2) {
		this.getCartItems();
		// TODO 自動生成されたメソッド・スタブ
		if(cartItems.contains(cartItem2)) {
			return cartItems.get(cartItems.indexOf(cartItem2)).increment(cartItem2);

		}else {
			cartItems.add(cartItem2);
			return true;
		}
	}

	public boolean changeItemNumber(CartItem cartItem2) {
		this.getCartItems();
		// TODO 自動生成されたメソッド・スタブ
		if(cartItems.contains(cartItem2)) {
			cartItems.get(cartItems.indexOf(cartItem2)).setNumber(cartItem2.getNumber());
			return true;
		}

		else {
//			cartItems.add(cartItem2);
			return false;
		}
	}

	public boolean decreaseCart(CartItem cartItem2) {
		if(cartItems.contains(cartItem2)) {
			return cartItems.get(cartItems.indexOf(cartItem2)).decrement();
		}else {
			return false;
		}

	}

	public void removeCart(CartItem cartItem2) {
		if(cartItems.contains(cartItem2)) {
			cartItems.remove(cartItems.indexOf(cartItem2));
		}
	}

	public int totalPrice() {
		int totalPrice = 0;
		for(CartItem item : cartItems) {
			totalPrice += item.calculateSum();
		}
		return totalPrice;
	}

	 public void clearCart() { // (2)
		 cartItems.removeAll(cartItems);
	    }


}
