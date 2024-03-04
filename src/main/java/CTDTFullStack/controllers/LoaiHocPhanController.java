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

import CTDTFullStack.Models.LoaiHocPhan;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.LoaiHocPhanService;

@Controller
@RequestMapping("/admin/loaihocphan")
public class LoaiHocPhanController {
	@Autowired
	LoaiHocPhanService loaiHocPhanService;
	MessageResponse message = null;
	@GetMapping
	public String quanLyLHP(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<LoaiHocPhan> listLHP = loaiHocPhanService.quanLyLHP();
		modelMap.addAttribute("listLHP", listLHP);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/loaihocphan/index";
	}
	
	@GetMapping("/create")
	public String themLHPGET(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		LoaiHocPhan lhp = new LoaiHocPhan();
		modelMap.addAttribute("lhp", lhp);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/loaihocphan/create";
	}
	
	@PostMapping("/create")
	public String themLHPPOST(@ModelAttribute LoaiHocPhan loaiHocPhan) throws JsonMappingException, JsonProcessingException {
		Boolean rs = loaiHocPhanService.themLHP(loaiHocPhan);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/loaihocphan"; 
		}
		message.setMessage("Thêm mới thất bại!");
		message.setStatus(false);
		return "redirect:/admin/loaihocphan/create"; 
		
	}
	
	@GetMapping("/update/{id}")
	public String capnhatLHP(@PathVariable Integer id, ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		LoaiHocPhan lhp = loaiHocPhanService.layLHP(id);
		modelMap.addAttribute("lhp", lhp);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/loaihocphan/update";
	}
	
	@PostMapping("/update") 
	public String capnhatLHPPost(@ModelAttribute LoaiHocPhan loaiHocPhan) {
		Boolean rs = loaiHocPhanService.capnhatLHP(loaiHocPhan);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/loaihocphan"; 
		}
		message.setMessage("Cập nhật thất bại do loại học phần đang được sử dụng");
		message.setStatus(false);
		return "redirect:/admin/loaihocphan/update/" + loaiHocPhan.getId(); 
	}
}
