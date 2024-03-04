package CTDTFullStack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import CTDTFullStack.Models.DanhMucHP;
import CTDTFullStack.Models.KKT;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.KKTService;

@Controller
@RequestMapping("/admin/kkt")
public class KKTController {
	@Autowired
	private KKTService kktService;
	MessageResponse message = null;
	
	@GetMapping
	public String index(ModelMap modelMap) {
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/khoikienthuc/index";
	}
	
	@GetMapping("/create")
	public String showPost(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		KKT kkt = new KKT();
		modelMap.addAttribute("kkt", kkt);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/khoikienthuc/create";
	
	}

	@PostMapping("/create")
	public String post(@ModelAttribute KKT kkt) {

		try {
			kktService.post(kkt);
			message = new MessageResponse();
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/kkt";

		} catch (Exception e) {
			message.setMessage("Thêm mới thất bại!");
			message.setStatus(false);
			return "redirect:/admin/kkt/create";
		}

	}

	@GetMapping("/update/{id}")
	public String showPut(@PathVariable Integer id, ModelMap modelMap) throws Exception {
		KKT kkt = kktService.getByid(id);
		modelMap.addAttribute("kkt", kkt);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/khoikienthuc/update";
	}

	@PostMapping("/update/{id}")
	public String capnhatDMLHPPost(@ModelAttribute KKT kkt, @Validated @PathVariable("id") int id) {
		Boolean rs = false;
		if (kktService.put(kkt, id) != null) {
			rs = true;
		}
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/kkt";
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/kkt/update/" + id;
	}

	

}
