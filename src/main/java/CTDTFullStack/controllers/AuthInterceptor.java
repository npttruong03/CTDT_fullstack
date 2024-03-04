package CTDTFullStack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import CTDTFullStack.config.AppConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
	private AppConfig appConfig;
	
	@Autowired
	public AuthInterceptor(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = getCookie();
		
		if(isValidCookie(token)) {
			return true;
		} else {
			response.sendRedirect("/admin/login");
			return false;
		}
	}
	
    private String getCookie() {
        // Trích xuất token từ cookie hoặc header của request
        String cookie = appConfig.cookieStore().getCookie();
        if (cookie != null)
        {
        	return cookie;
        }
        return null;
    }
    
    private boolean isValidCookie(String cookie) {
        // Thực hiện kiểm tra token có hợp lệ hay không (ví dụ: kiểm tra chữ ký, hạn sử dụng, v.v.)
        return cookie != null && cookie.length() > 10;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Không cần thực hiện gì sau khi xử lý request
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Không cần thực hiện gì sau khi hoàn thành xử lý request
    }
}
