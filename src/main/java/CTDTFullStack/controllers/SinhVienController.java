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

import CTDTFullStack.Models.Nganh;
import CTDTFullStack.Models.SinhVien;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.ChuyenNganhService;
import CTDTFullStack.services.NganhService;
import CTDTFullStack.services.SinhVienService;

@Controller
@RequestMapping("/admin/sinhvien")
public class SinhVienController {
	@Autowired
	SinhVienService sinhVienService;
	@Autowired
	NganhService nganhService;
	@Autowired
	ChuyenNganhService chuyenNganhService;
	MessageResponse message = null;
	@GetMapping
	public String quanLySinhVien(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<SinhVien> listSV = sinhVienService.quanLySinhVien();
		modelMap.addAttribute("listSV", listSV);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/sinhvien/index";
	}
	
	@GetMapping("/create")
	public String themSinhVien(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		SinhVien sinhVien = new SinhVien();
		List<Nganh> listNganh = nganhService.GetAll();
		modelMap.addAttribute("sinhVien", sinhVien);
		modelMap.addAttribute("listNganh", listNganh);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/sinhvien/create";
	}
	
	@PostMapping("/create")
	public String themSinhVien(@ModelAttribute SinhVien sinhVien) throws JsonMappingException, JsonProcessingException {
		Boolean rs = sinhVienService.themSinhVien(sinhVien);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/sinhvien"; 
		}
		message.setMessage("Thêm mới thất bại!");
		message.setStatus(false);
		return "redirect:/admin/sinhvien/create"; 
		
	}
	
	@GetMapping("/update/{id}")
	public String capnhatSinhVien(@PathVariable Integer id, ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		SinhVien sv = sinhVienService.laySinhVien(id);
		List<Nganh> listNganh = nganhService.GetAll();
		modelMap.addAttribute("sinhVien", sv);
		modelMap.addAttribute("listNganh", listNganh);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/sinhvien/update";
	}
	
	@PostMapping("/update") 
	public String capnhatSinhVien(@ModelAttribute SinhVien sinhVien) {
		Boolean rs = sinhVienService.capnhatSinhVien(sinhVien);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/sinhvien"; 
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/sinhvien/update/" + sinhVien.getId(); 
	}
}
