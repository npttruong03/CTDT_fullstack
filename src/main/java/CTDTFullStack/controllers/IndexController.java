package CTDTFullStack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import CTDTFullStack.services.ChuongtrinhService;
import CTDTFullStack.services.LinhVucService;
import CTDTFullStack.services.NganhService;

@Controller
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private LinhVucService linhVucService;
    @Autowired
    private ChuongtrinhService chuongtrinhService;
	@GetMapping
	public String index(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		modelMap.addAttribute("linhvucs", this.linhVucService.getDataFromAPI());
		modelMap.addAttribute("chuongtrinhs", this.chuongtrinhService.GetAll());
		return "index";
	}

	
}
