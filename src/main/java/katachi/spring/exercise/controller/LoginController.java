package katachi.spring.exercise.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller

public class LoginController {
//	@GetMapping()
//	public String menu() {
//
//		return "login/menu";
//	}
	@GetMapping("/")
	public String getTitle() {

		return "redirect:/item/title";
	}

	@GetMapping("/login")
	public String getLogin(@RequestParam(value="error",required=false) String error, Model model,HttpSession session) {
		AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		System.out.println(ex);
		return "login/login";
	}
}
