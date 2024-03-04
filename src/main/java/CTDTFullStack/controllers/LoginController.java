package CTDTFullStack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import CTDTFullStack.Models.Account;
import CTDTFullStack.config.AppConfig;
import CTDTFullStack.services.LoginService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	private AppConfig appConfig;
	@Autowired
	public LoginController(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	
//	@GetMapping
//	public String Login(Model model) {
//		String check = loginService.checkToken();
//		if(check != null) {
//			if (!StringUtils.isEmpty(check)) {
//				return "redirect:/admin/index";
//			}
//		}
//		model.addAttribute("account", new Account());
//		return "indexLog";
//	}
	
	@GetMapping
	public String login(Model model) {
		model.addAttribute("account", new Account());
		return "pages/forms/Login/index";
	}
	@PostMapping("/signin")
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes, HttpServletResponse response, HttpSession session) {
        try {
            String token = loginService.loginAndGetToken(username, password);
            // Save the token to the response cookie 	or session as needed
//            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.set(HttpHeaders., token);
//            Cookie cookie = new Cookie("nhaphocvku", token);
//    	    cookie.setHttpOnly(true);
//    	    cookie.setMaxAge(86400);
//    	    cookie.setPath("/admin/**");
//    	    System.out.println(cookie);
//    	    response.addCookie(cookie);
            // Add other necessary headers or cookie handling as required

//            return "redirect:/admin/tuitionFeeList"; // Redirect to the dashboard or any other page upon successful login
//
            session.setAttribute("roles", appConfig.cookieStore().getRoles());
            session.setAttribute("username", appConfig.cookieStore().getUsername());
            return "redirect:/admin"; // Redirect to the dashboard or any other page upon successful login

        } catch (Exception e) {
        	e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Invalid credentials. Please try again.");
            return "redirect:/admin/login"; // Redirect back to the login page with an error message
        }
    }
	
	
	
	
	@PostMapping("/logout")
	public String logout() {
		String rs = loginService.logout();
		if (rs == "OK")
		{
			return "redirect:/admin/login";
		}
		else {
			return "";
		}
	}
}
