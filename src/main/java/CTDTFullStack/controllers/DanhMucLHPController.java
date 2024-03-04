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

import CTDTFullStack.Models.DanhMucLHP;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.DanhMucLHPService;

@Controller
@RequestMapping("/admin/danhmuclhp")
public class DanhMucLHPController {
	@Autowired
	DanhMucLHPService dmlhpService;
	MessageResponse message = null;
	@GetMapping
	public String quanLyDMLHP(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<DanhMucLHP> listDMLHP = dmlhpService.quanLyDMLHP();
		modelMap.addAttribute("listDMLHP", listDMLHP);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/danhmuclhp/index";
	}
	
	@GetMapping("/create")
	public String themDMLHPGET(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		DanhMucLHP dmlhp = new DanhMucLHP();
		modelMap.addAttribute("dmlhp", dmlhp);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/danhmuclhp/create";
	}
	
	@PostMapping("/create")
	public String themDMLHPPOST(@ModelAttribute DanhMucLHP danhMucLHP) throws JsonMappingException, JsonProcessingException {
		Boolean rs = dmlhpService.themDMLHP(danhMucLHP);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/danhmuclhp"; 
		}
		message.setMessage("Thêm mới thất bại!");
		message.setStatus(false);
		return "redirect:/admin/danhmuclhp/create"; 
		
	}
	
	@GetMapping("/update/{id}")
	public String capnhatDMLHP(@PathVariable Integer id, ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		DanhMucLHP dmlhp = dmlhpService.layDMLHP(id);
		modelMap.addAttribute("dmlhp", dmlhp);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/danhmuclhp/update";
	}
	
	@PostMapping("/update") 
	public String capnhatDMLHPPost(@ModelAttribute DanhMucLHP danhMucLHP) {
		Boolean rs = dmlhpService.capnhatDMLHP(danhMucLHP);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/danhmuclhp"; 
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/danhmuclhp/update/" + danhMucLHP.getId(); 
	}
}
