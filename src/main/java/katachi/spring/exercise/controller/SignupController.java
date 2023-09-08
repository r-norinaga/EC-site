package katachi.spring.exercise.controller;
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

import katachi.spring.exercise.domain.user.model.User;
import katachi.spring.exercise.domain.user.model.UserPaymentInfo;
import katachi.spring.exercise.domain.user.service.UserService;
import katachi.spring.exercise.form.SignupForm;
import katachi.spring.exercise.form.UserPaymentInfoForm;

@Controller
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String getSignup(Model model, @ModelAttribute SignupForm signupForm ,  @AuthenticationPrincipal UserDetails user) {

		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		return "user/signup";
	}

	@GetMapping("/userMenu")
	public String userMenu() {

		return "user/userMenu";
	}

	@PostMapping("/signup")
	public String postSignup(Model model, @ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, @AuthenticationPrincipal UserDetails user) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}
		String spaceUserName = signupForm.getUserName().replace("　", "");
		String spacePassword = signupForm.getPassword().replace("　", "");
		String spaceSurname = signupForm.getSurname().replace("　", "");
		String spaceGivenName = signupForm.getGivenName().replace("　", "");
		String spaceEmailAddress = signupForm.getEmailAddress().replace("　", "");
		String spaceHomeAddress = signupForm.getHomeAddress().replace("　", "");

		if(spaceUserName.equals("")) {
			bindingResult.rejectValue("userName", "space");
		}
		if(spacePassword.equals("")) {
			bindingResult.rejectValue("password", "space");
		}
		if(spaceSurname.equals("")) {
			bindingResult.rejectValue("surname", "space");
		}
		if(spaceGivenName.equals("")) {
			bindingResult.rejectValue("givenName", "space");
		}
		if(spaceEmailAddress.equals("")) {
			bindingResult.rejectValue("emailAddress", "space");
		}
		if(spaceHomeAddress.equals("")) {
			bindingResult.rejectValue("homeAddress", "space");
		}

		String emailAddress = signupForm.getEmailAddress();
		if(emailAddress != null) {
			if(userService.overlappingCheckEmail(emailAddress)) {
				bindingResult.rejectValue("emailAddress", "nameDuplicate");
			}
		}

		String userName = signupForm.getUserName();
		if(userName != null) {
			if(userService.overlappingCheckName(userName)) {
				bindingResult.rejectValue("userName", "nameDuplicate");
			}
		}

		if (bindingResult.hasErrors()) {
			// NG:ユーザー登録画面に戻ります
			return getSignup(model, signupForm, user);
			} else {
				signupForm.setAddressee(signupForm.settingAddressee());
				User user2 = modelMapper.map(signupForm, User.class);
				userService.signup(user2);
				return "redirect:/login";
			}
	}

	@GetMapping("/userInfo")
	public String getUserInfo(Model model, @AuthenticationPrincipal UserDetails user, @ModelAttribute SignupForm signupForm, String err) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		User loginUser = userService.getLoginUser(user.getUsername());
		model.addAttribute("loginUserName", user.getUsername());
		if(err != "err") {
			signupForm = modelMapper.map(loginUser, SignupForm.class);
			model.addAttribute(signupForm);
		}/* else{
//			signupForm = modelMapper.map(loginUser, SignupForm.class);
//			model.addAttribute(signupForm);
//		}*/
//		System.out.println(signupForm);

//		signupForm = modelMapper.map(loginUser, SignupForm.class);
//		model.addAttribute(signupForm);
		return "user/userInformation";
	}

	@PostMapping("/updateUserInfo")
	public String postUpdateUserInfo(Model model, @AuthenticationPrincipal UserDetails user, @ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}

		User loginUser = userService.getLoginUser(user.getUsername());

		String spacePassword = signupForm.getPassword().replace("　", "");
		String spaceSurname = signupForm.getSurname().replace("　", "");
		String spaceGivenName = signupForm.getGivenName().replace("　", "");
		String spaceEmailAddress = signupForm.getEmailAddress().replace("　", "");
		String spaceHomeAddress = signupForm.getHomeAddress().replace("　", "");

		if(spacePassword.equals("")) {
			bindingResult.rejectValue("password", "space");
		}
		if(spaceSurname.equals("")) {
			bindingResult.rejectValue("surname", "space");
		}
		if(spaceGivenName.equals("")) {
			bindingResult.rejectValue("givenName", "space");
		}
		if(spaceEmailAddress.equals("")) {
			bindingResult.rejectValue("emailAddress", "space");
		}
		if(spaceHomeAddress.equals("")) {
			bindingResult.rejectValue("homeAddress", "space");
		}

		if (bindingResult.hasErrors()) {
			// NG:ユーザー登録画面に戻ります
			return getUserInfo(model, user, signupForm, "err");
			} else {
				User userForUpdating = modelMapper.map(signupForm, User.class);
				userService.userInfoChange(userForUpdating);
				return "redirect:/item/itemList";
			}

	}

	@GetMapping("/userPaymentInfo")
	public String getUserPaymentInfo(Model model, @AuthenticationPrincipal UserDetails user, @ModelAttribute UserPaymentInfoForm userPaymentInfoForm) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		User loginUser = userService.getLoginUser(user.getUsername());
		model.addAttribute("loginUserName", user.getUsername());

			List<UserPaymentInfo> userPaymentInfoList = userService.getUserPaymentInfo(loginUser.getUserId());
			if(userPaymentInfoList.size() != 0) {
				model.addAttribute("userPaymentInfoList", userPaymentInfoList);
			}else {
//				userPaymentInfoForm = new UserPaymentInfoForm();
//				userPaymentInfoForm.setUserId(loginUser.getUserId());
//				model.addAttribute("userPaymentInfoForm", userPaymentInfoForm);
			}
			model.addAttribute("userPaymentInfoForm", userPaymentInfoForm);




		return "user/userPaymentInfoList";
	}


	@PostMapping("/userPaymentInfo")
	public String postUserPaymentInfo(Model model, @AuthenticationPrincipal UserDetails user, @ModelAttribute UserPaymentInfoForm userPaymentInfoForm, /* @RequestParam("creditCardNumber")String creditCardNumber,*/ RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		User loginUser = userService.getLoginUser(user.getUsername());
		model.addAttribute("loginUserName", user.getUsername());

		redirectAttributes.addFlashAttribute("userPaymentInfoForm", userPaymentInfoForm);



// NG:ユーザー登録画面に戻ります
//			return getUserPaymentInfo(model, user, userPaymentInfoForm, "err");
//
//		UserPaymentInfo userPaymentInfo = userService.getUserPaymentInfoByCreditCardNumber(creditCardNumber);
//		userPaymentInfoForm = modelMapper.map(userPaymentInfo, UserPaymentInfoForm.class);
//		model.addAttribute("userPaymentInfoForm" , userPaymentInfoForm);


		return "redirect:/signup/EditUserPaymentInfo";
	}

	@GetMapping("/EditUserPaymentInfo")
	public String getUserPaymentInfoEdition(Model model, @AuthenticationPrincipal UserDetails user, @ModelAttribute("userPaymentInfoForm") UserPaymentInfoForm userPaymentInfoForm, RedirectAttributes redirectAttributes, String err) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		User loginUser = userService.getLoginUser(user.getUsername());
		model.addAttribute("loginUserName", user.getUsername());


		model.addAttribute("userPaymentInfoForm", userPaymentInfoForm);


//		if(err != "err") {
//
//		}



		return "user/userPaymentInfoForm";
	}

	@PostMapping(value="/EditUserPaymentInfo", params="edition")
	public String postUserPaymentInfoEdition(Model model, @AuthenticationPrincipal UserDetails user, @ModelAttribute @Validated UserPaymentInfoForm userPaymentInfoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		User loginUser = userService.getLoginUser(user.getUsername());
		model.addAttribute("loginUserName", user.getUsername());

		String spaceCreditCardNumber = userPaymentInfoForm.getCreditCardNumber().replace("　", "");
		String spaceCreditCardUserName = userPaymentInfoForm.getCreditCardUserName().replace("　", "");

		String spaceCreditCardVerificationCode = userPaymentInfoForm.getCreditCardVerificationCode().replace("　", "");

		if(spaceCreditCardNumber.equals("")) {
			bindingResult.rejectValue("creditCardNumber", "space");
		}
		if(spaceCreditCardUserName.equals("")) {
			bindingResult.rejectValue("creditCardUserName", "space");
		}
		if(spaceCreditCardVerificationCode.equals("")) {
			bindingResult.rejectValue("creditCardVerificationCode", "space");
		}



		if (bindingResult.hasErrors()) {
			// NG:ユーザー登録画面に戻ります
			return getUserPaymentInfoEdition(model, user, userPaymentInfoForm, redirectAttributes, "err");
			} else {
				if(userService.getUserPaymentInfo(loginUser.getUserId()) == null) {
					UserPaymentInfo userPaymentInfo = modelMapper.map(userPaymentInfoForm, UserPaymentInfo.class);
					userService.insertUserPaymentInfo(userPaymentInfo);
				}else {
					List<UserPaymentInfo> userPaymentInfoList = userService.getUserPaymentInfo(loginUser.getUserId());
					if(userPaymentInfoList.contains(userPaymentInfoForm)) {

					}
					UserPaymentInfo userPaymentInfo = modelMapper.map(userPaymentInfoForm, UserPaymentInfo.class);
					userService.updateUserPaymentInfo(userPaymentInfo);
				}
			}

		return "redirect:/item/itemList";
	}

	@PostMapping(value="/EditUserPaymentInfo", params="deletion")
	public String postUserPaymentInfoDeletion(Model model, @AuthenticationPrincipal UserDetails user, @ModelAttribute @Validated UserPaymentInfoForm userPaymentInfoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(user != null) {
			model.addAttribute("loginUserName", user.getUsername());
		}


		User loginUser = userService.getLoginUser(user.getUsername());
		model.addAttribute("loginUserName", user.getUsername());
		UserPaymentInfo userPaymentInfo = modelMapper.map(userPaymentInfoForm, UserPaymentInfo.class);
		userService.deleteUserPaymentInfo(userPaymentInfo);

		return "redirect:/item/itemList";
	}



}
