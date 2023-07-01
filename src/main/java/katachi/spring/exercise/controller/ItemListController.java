package katachi.spring.exercise.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import katachi.spring.exercise.domain.user.model.Cart;
import katachi.spring.exercise.domain.user.model.CartItem;
import katachi.spring.exercise.domain.user.model.InfoAdministrater;
import katachi.spring.exercise.domain.user.model.Item;
import katachi.spring.exercise.domain.user.model.Order;
import katachi.spring.exercise.domain.user.model.OrderDetail;
import katachi.spring.exercise.domain.user.model.User;
import katachi.spring.exercise.domain.user.model.UserPaymentInfo;
import katachi.spring.exercise.domain.user.service.ItemService;
import katachi.spring.exercise.domain.user.service.OrderDetailService;
import katachi.spring.exercise.domain.user.service.OrderService;
import katachi.spring.exercise.domain.user.service.UserService;
import katachi.spring.exercise.form.PaymentInfoForm;
import katachi.spring.exercise.form.PersonalInfoForm;
import katachi.spring.exercise.scope.CustomScope;

@Controller
@RequestMapping("/item")
@SessionAttributes("infoAdministrater")
public class ItemListController {

	@Autowired
	CustomScope customScope;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ItemService itemService;

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	@Autowired
	Cart cart;


	@Autowired
	InfoAdministrater infoAdministrater;

	@Autowired
	UserService userService;

	@Autowired
	OrderService orderService;

	@Autowired
	Order order;

	@Autowired
	OrderDetail orderDetail;

	@Autowired
	OrderDetailService orderDetailService;

//	@Autowired
//	BeanUtils beanUtils;

	@GetMapping("/title")
	public String getTitle() {

		return "itemList/titlePage";
	}


	@GetMapping("/itemList")
	public String getItemList(Model model, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		List<Item> itemList = itemService.getItemList();
		model.addAttribute("itemList", itemList);
		return "itemList/itemList";
	}

	@GetMapping("cart")
	public String getCart(Model model, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		return "itemList/cart";
	}


	@PostMapping(value="/cart", params="add")
	public String postAdd(Model model, @RequestParam("id")int id, @AuthenticationPrincipal UserDetails user) {

		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		//カートに追加したい商品をセッションに登録する
		Item item = itemService.getItem(id);
		CartItem cartItem = new CartItem();
		cartItem.setItem(item);
		cartItem.setNumber(1);
		if(cart.addToCart(cartItem) == true) {
			model.addAttribute("msg" , "一件追加しました。");
		}else {
			model.addAttribute("msg" , "在庫数を超過したため追加できませんでした。");
		}

		return getItemList(model, user);
	}

	@PostMapping(value="/cart", params="changeNumber")
	public String postChangeNumber(Model model, @RequestParam("id")int id, @RequestParam("number")int number, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		//カートに追加したい商品をセッションに登録する
		model.addAttribute("loginUserName", user.getUsername());
		Item item = itemService.getItem(id);
		CartItem cartItem = new CartItem();
		cartItem.setItem(item);
		cartItem.setNumber(number);
		cart.changeItemNumber(cartItem);

		model.addAttribute("msg", "該当商品の個数を変更しました。");

		System.out.println(session);

		return getItemList(model, user);
	}

	@PostMapping(value="/cart", params="remove")
	public String removeCart(Model model, @RequestParam("id")int id, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		Item item = itemService.getItem(id);
		CartItem cartItem = new CartItem();
		cartItem.setItem(item);
		cart.removeCart(cartItem);
		model.addAttribute("msg" , "一つの商品をカートから削除しました");
		return getItemList(model, user);
	}

	@GetMapping("/personalInfo")
	public String getPersonalInfo(Model model, @ModelAttribute PersonalInfoForm personalInfoForm, @AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes, String err) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		PersonalInfoForm personalInfoFormPrevious = modelMapper.map(infoAdministrater, PersonalInfoForm.class);
		if(personalInfoFormPrevious.getAddressee() == null) {
			if(cart.getCartItems().isEmpty()){
				String errorMsg = "カートの中身が空なので購入画面に遷移できません";
				redirectAttributes.addFlashAttribute("errorMsg", errorMsg);

				return "redirect:/item/itemList" ;
			}

			HttpSession session =request.getSession();
			SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
			Authentication authentication =	securityContext.getAuthentication();


				if(authentication != null) {
					User loginUser = userService.getLoginUser(user.getUsername());
					if(err!="error") {
						personalInfoForm = modelMapper.map(loginUser, PersonalInfoForm.class);
						personalInfoForm.setShippingAddress(loginUser.getHomeAddress());
						if(loginUser.getAddressee() == null) {
							personalInfoForm.setAddressee(loginUser.getSurname() + " " + loginUser.getGivenName());
						}else {
							personalInfoForm.setAddressee(loginUser.getAddressee());
						}

					}else {
						err = "";
					}
				}




		}else {

			personalInfoForm = personalInfoFormPrevious;

		}


		model.addAttribute("personalInfoForm", personalInfoForm);
		return "itemList/personalInfo";
	}

	@PostMapping("/personalInfo")
	public String postPersonalInfo(Model model, @ModelAttribute @Validated PersonalInfoForm personalInfoForm, BindingResult bindingResult, @AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		String spaceAddressee = personalInfoForm.getAddressee().replace("　", "");
		String spaceEmailAddress = personalInfoForm.getEmailAddress().replace("　", "");
		String spaceShippingAddress = personalInfoForm.getShippingAddress().replace("　", "");

		if(spaceAddressee.equals("")) {
			bindingResult.rejectValue("addressee", "space");
		}

		if(spaceEmailAddress.equals("")) {
			bindingResult.rejectValue("emailAddress", "space");
		}

		if(spaceShippingAddress.equals("")) {
			bindingResult.rejectValue("shippingAddress", "space");
		}

		if(bindingResult.hasErrors()) {
			return getPersonalInfo(model,  personalInfoForm,  user, redirectAttributes, "error");
		}else {
			BeanUtils.copyProperties(personalInfoForm, infoAdministrater);
			return "redirect:/item/paymentInfo";
		}

	}


	@GetMapping("/paymentInfo")
	public String getPaymentInfo(Model model, @ModelAttribute PaymentInfoForm paymentInfoForm, @AuthenticationPrincipal UserDetails user,  RedirectAttributes redirectAttributes, String err) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		User loginUser = userService.getLoginUser(user.getUsername());
		PaymentInfoForm paymentInfoFormPrevious = modelMapper.map(infoAdministrater, PaymentInfoForm.class);

		if(paymentInfoFormPrevious.getCreditCardNumber() == null) {
			UserPaymentInfo userPaymentInfo = userService.getUserPaymentInfo(loginUser.getUserId());


			if(err!="error") {
				if(userPaymentInfo != null) {
					paymentInfoForm = modelMapper.map(userPaymentInfo, PaymentInfoForm.class);

				}else {
					paymentInfoForm = new PaymentInfoForm();
					paymentInfoForm.setUserId(loginUser.getUserId());

				}

			}else {
				err = "";
			}
		}else {
			paymentInfoForm = paymentInfoFormPrevious;
		}




		model.addAttribute("paymentInfoForm", paymentInfoForm);
		return "itemList/paymentInfo";



	}

	@PostMapping("/paymentInfo")
	public String postPaymentInfo(Model model, @ModelAttribute @Validated PaymentInfoForm paymentInfoForm, BindingResult bindingResult, @AuthenticationPrincipal UserDetails user,  RedirectAttributes redirectAttributes) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		String spaceCreditCardNumber = paymentInfoForm.getCreditCardNumber().replace("　", "");
		String spaceCreditCardUserName = paymentInfoForm.getCreditCardUserName().replace("　", "");
		String spaceCreditCardVerificationCode = paymentInfoForm.getCreditCardVerificationCode().replace("　", "");

		if(spaceCreditCardNumber.equals("")) {
			bindingResult.rejectValue("creditCardNumber" , "space");
		}

		if(spaceCreditCardUserName.equals("")) {
			bindingResult.rejectValue("creditCardUserName" , "space");
		}

		if(spaceCreditCardVerificationCode.equals("")) {
			bindingResult.rejectValue("creditCardVerificationCode" , "space");
		}

		if(bindingResult.hasErrors()) {
			return getPaymentInfo(model, paymentInfoForm, user, redirectAttributes, "error");
		}else {
			infoAdministrater.setCreditCardNumber(paymentInfoForm.getCreditCardNumber());
			infoAdministrater.setCreditCardUserName(paymentInfoForm.getCreditCardUserName());
			infoAdministrater.setCreditCardExpirationMonth(paymentInfoForm.getCreditCardExpirationMonth());
			infoAdministrater.setCreditCardVerificationCode(paymentInfoForm.getCreditCardVerificationCode());


			infoAdministrater.setCreditCardNumberAsterisked(infoAdministrater.asteriskingCreditCardNumber());
			infoAdministrater.setCreditCardVerificationCodeAsterisked(infoAdministrater.asteriskingCreditCardVerificationCode());
			return "redirect:/item/confirmation";
		}

	}

	@PostMapping(value="/paymentInfo", params="goingBack")
	public String postPaymentInfoGoingBack(Model model,  @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		return "redirect:/item/personalInfo";
	}

	@GetMapping("/confirmation")
	public String getConfirmation(Model model, @ModelAttribute Order order, @AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes){
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		System.out.println(session);

		model.addAttribute("order", order);

		return "itemList/confirmation";
	}

	@PostMapping("/confirmation")
	public String postConfirmation(Model model, @ModelAttribute Order order, @AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes){

		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		order = modelMapper.map(infoAdministrater, Order.class);

		model.addAttribute("order", order);


		return "redirect:/item/orderPlacement";
	}



/*
	@PostMapping(value="/confirmation" , params="goingBack")
	public String confirmationGoingBack(Model model, @ModelAttribute PersonalInfoForm personalInfoForm, @AuthenticationPrincipal UserDetails user) {
		return "redirect:/item/itemList";
	}
*/


	@PostMapping(value="/orderPlacement", params="goingBack")
	public String postOrderPlacementGoingBack(Model model, @ModelAttribute Order order, @AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		redirectAttributes.addFlashAttribute("order", order);

		return "redirect:/item/paymentInfo";
	}

	@GetMapping("/orderPlacement")
	public String getOrderPlacement(Model model, @ModelAttribute Order order, @AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes){
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}



		order = modelMapper.map(infoAdministrater, Order.class);

		model.addAttribute("order", order);

		return postOrderPlacement(model, order, user);
	}

	@PostMapping(value="/orderPlacement", params="orderPlacement")
	public String postOrderPlacement(Model model, @ModelAttribute Order order, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		order = modelMapper.map(infoAdministrater, Order.class);

		model.addAttribute("order", order);
		order.setOrderDateTime(LocalDateTime.now());
		orderService.orderPlacement(order);

		for(CartItem products: cart.getCartItems()) {
			orderDetail.setItemId(products.getItem().getId());
			orderDetail.setNumber(products.getNumber());
			orderDetail.setOrderId(order.getOrderId());
			orderDetail.setOrderDateTime(order.getOrderDateTime());
			orderDetailService.orderPlacement(orderDetail);

		}

		cart.clearCart();
		session.removeAttribute("infoAdministrater");

		return "/itemList/orderPlacement";
	}

	@GetMapping("/cancellation")
	public String getPurchaseCancellation(Model model, @ModelAttribute Order order, @AuthenticationPrincipal UserDetails user, SessionStatus sessionStatus) {
		System.out.println(session.getAttribute("user"));
		System.out.println(session.getAttribute("infoAdministrater"));
		UserDetails user2 = user;
		session.invalidate();
		session.setAttribute("user", user2);

		return 	"redirect:/item/itemList";
	}

}
