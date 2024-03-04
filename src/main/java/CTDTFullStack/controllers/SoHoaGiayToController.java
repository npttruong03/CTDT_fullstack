package CTDTFullStack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import CTDTFullStack.Models.SoHoaGiayTo;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.SoHoaGiayToService;

@Controller
@RequestMapping("/admin/sohoagiayto")
public class SoHoaGiayToController {
	@Autowired
	SoHoaGiayToService soHoaGiayToService;
	MessageResponse message = null;
	@GetMapping
	public String quanLySoHoaGiayTo(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<SoHoaGiayTo> listSHGT = soHoaGiayToService.quanLySoHoaGiayTo();
		modelMap.addAttribute("listSHGT", listSHGT);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/sohoagiayto/index";
	}
	
	@GetMapping("/create")
	public String themSoHoaGiayToGET(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/sohoagiayto/create";
	}
	
	@PostMapping("/create")
	public String themSoHoaGiayToPOST(@ModelAttribute SoHoaGiayTo soHoaGiayTo) throws JsonMappingException, JsonProcessingException {
		Boolean rs = soHoaGiayToService.themSoHoaGiayTo(soHoaGiayTo);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/sohoagiayto"; 
		}
		message.setMessage("Thêm mới thất bại!");
		message.setStatus(false);
		return "redirect:/admin/sohoagiayto/create"; 
		
	}
	
	@GetMapping("/update/{id}")
	public String capnhatSoHoaGiayTo(@PathVariable Integer id, ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		SoHoaGiayTo shgt = soHoaGiayToService.laySoHoaGiayTo(id);
		modelMap.addAttribute("shgt", shgt);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/sohoagiayto/update";
	}
	
	@PostMapping("/update") 
	public String capnhatSoHoaGiayTo(@ModelAttribute SoHoaGiayTo soHoaGiayTo) {
		Boolean rs = soHoaGiayToService.capnhatSoHoaGiayTo(soHoaGiayTo);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/sohoagiayto"; 
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/sohoagiayto/update/" + soHoaGiayTo.getId(); 
	}
}
