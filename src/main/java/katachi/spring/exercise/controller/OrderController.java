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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String postOrder(Model model, @ModelAttribute @Validated OrderSearchForm orderSearchForm, BindingResult bindingResult, @ModelAttribute Order order, @ModelAttribute OrderDetail orderDetail, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		if(orderService.getOrder(orderSearchForm.getOrderId()) == null){
			order = null;
		}else {
			order = orderService.getOrder(orderSearchForm.getOrderId());
			order.setCreditCardNumberAsterisked(order.asteriskingCreditCardNumber());
		}

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

		redirectAttributes.addFlashAttribute("order", order);
		redirectAttributes.addFlashAttribute("orderDetailList", orderDetailList);
		redirectAttributes.addFlashAttribute("orderedItemList", orderedItemList);
		redirectAttributes.addFlashAttribute("totalPriceOfAnOrder", totalPriceOfAnOrder);



		return "redirect:/order/searchResult";
	}

	@GetMapping("/searchResult")
	public String getSearchResult(Model model, @ModelAttribute("order")Order order, @ModelAttribute("orderDetailList")List<OrderDetail> orderDetailList, @ModelAttribute("orderedItemList")List<Item> orderedItemList, @ModelAttribute("totalPriceOfAnOrder")int totalPriceOfAnOrder, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user){
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		model.addAttribute("order", order);
		model.addAttribute("orderDetailList", orderDetailList);
		model.addAttribute("orderedItemList", orderedItemList);
		model.addAttribute("totalPriceOfAnOrder", totalPriceOfAnOrder);


		return "order/searchResult";
	}

	@PostMapping(value="/searchResult", params="orderCancellation")
	public String postSearchResultOrderCancellation(Model model, @ModelAttribute Order order, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		redirectAttributes.addFlashAttribute("order", order);



		return "redirect:/order/orderCancellation";
	}

	@GetMapping("/orderCancellation")
	public String getOrderCancelation(Model model, @ModelAttribute("order") Order order, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		model.addAttribute("order", order);


		return "order/orderCancellationConfirmation";
	}

	@PostMapping("/orderCancellation")
	public String postOrderCancelation(Model model, @ModelAttribute("order") Order order, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		orderService.cancelOrder(order.getUserId(), order.getOrderId());
		orderDetailService.cancelOrder(order.getOrderId());
		model.addAttribute("order", order);

		return "order/orderCancellationAcheived";
	}

	@PostMapping(value="/searchResult", params="orderedItemCancellation")
	public String postSearchResultOrderDetailCancellation(Model model, @ModelAttribute("order") Order order, @ModelAttribute("orderDetail")OrderDetail orderDetail, @ModelAttribute("item")Item item, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		redirectAttributes.addFlashAttribute("order", order);
		redirectAttributes.addFlashAttribute("orderDetail", orderDetail);
		redirectAttributes.addFlashAttribute("item", item);



		return "redirect:/order/orderedItemCancellation";
	}

	@GetMapping("/orderedItemCancellation")
	public String getOrderDetailCancelation(Model model, @ModelAttribute("order") Order order, @ModelAttribute("orderDetail")OrderDetail orderDetail, @ModelAttribute Item item, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		item = itemService.getItem(orderDetail.getItemId());
		model.addAttribute("order", order);
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("item", item);

		return "order/orderedItemCancellationConfirmation";
	}

	@PostMapping("/orderedItemCancellation")
	public String postOrderDetailCancelation(Model model, @ModelAttribute("order")Order order, @ModelAttribute("orderDetail")OrderDetail orderDetail, @ModelAttribute("item")Item item, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


//		orderService.cancelOrder(order.getUserId(), order.getOrderId());
		orderDetailService.cancelOrderedItem(orderDetail);
		List<OrderDetail> orderDetailList =orderDetailService.getOrderDetails(orderDetail.getOrderId());
		if(orderDetailList.size() == 0) {
			redirectAttributes.addFlashAttribute("order", order);
			return postOrderCancelation(model, order, redirectAttributes, user);
		}
		model.addAttribute("order", order);
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("item", item);
		return "order/orderedItemCancellationAcheived";
	}

}
