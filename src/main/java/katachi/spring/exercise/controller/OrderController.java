package katachi.spring.exercise.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import katachi.spring.exercise.domain.user.model.Item;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetail;
import katachi.spring.exercise.domain.user.service.ItemService;
import katachi.spring.exercise.domain.user.service.OrderDetailService;
import katachi.spring.exercise.domain.user.service.OrderService;
import katachi.spring.exercise.form.OrderSearchForm;


@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetail orderDetail;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	private ItemService itemService;

	@GetMapping("/search")
	public String getOrder(Model model, @ModelAttribute OrderSearchForm orderSearchForm, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		return "order/search";
	}

	@PostMapping("/search")
	public String postOrder(Model model, @ModelAttribute @Validated OrderSearchForm orderSearchForm, BindingResult bindingResult, @ModelAttribute Order order, @ModelAttribute OrderDetail orderDetail, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		order = orderService.getOrder(orderSearchForm.getOrderId());
		order.setCreditCardNumberAsterisked(order.asteriskingCreditCardNumber());
		List<OrderDetail> orderDetailList = orderDetailService.getOrderDetails(orderSearchForm.getOrderId());
		List<Item> orderedItemList = new ArrayList<Item>();
		for(OrderDetail singleOrderDetail : orderDetailList) {
			orderedItemList.add(itemService.getItem(singleOrderDetail.getItemId()));
		}

		int totalPriceOfAnOrder = 0;

		for(OrderDetail singleOrderDetail : orderDetailList){

			for(Item singleOrderedItem : orderedItemList) {
				if(singleOrderDetail.getItemId() == singleOrderedItem.getId() ) {
					totalPriceOfAnOrder += singleOrderDetail.getNumber() * singleOrderedItem.getPrice();

				}

			}
		}
		model.addAttribute("order", order);
		model.addAttribute("orderDetailList", orderDetailList);
		model.addAttribute("orderedItemList", orderedItemList);
		model.addAttribute("totalPriceOfAnOrder", totalPriceOfAnOrder);




		return "order/searchResult";
	}
/*
	@GetMapping("/searchResult")
	public String getSearchResult(Model model, @ModelAttribute Order order, @AuthenticationPrincipal UserDetails user){
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
	}
*/
}
