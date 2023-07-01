package katachi.spring.exercise.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import katachi.spring.exercise.domain.user.service.ItemService;

@RestController
public class ItemRestController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ItemService itemService;

/*	@GetMapping("/get/cart")
	public Cart getCartItems() {
			List<CartItem> cartItems;
	}
*/

}
