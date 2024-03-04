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

import CTDTFullStack.Models.DanhMucHP;
import CTDTFullStack.Models.LopHocPhan;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.DmhpService;
import CTDTFullStack.services.LopHocPhanService;


@Controller
@RequestMapping("/admin/lophocphan")
public class LopHocPhanController {
	@Autowired
	LopHocPhanService lopHocPhanService;
	@Autowired
	DmhpService dmhpService;
	MessageResponse message = null;
	@GetMapping
	public String quanLyLopHopPhan(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<LopHocPhan> listLHP = lopHocPhanService.quanLyLopHocPhan();
		modelMap.addAttribute("listLHP", listLHP);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/lophocphan/index";
	}
	
	@GetMapping("/create")
	public String themLopHocPhan(ModelMap modelMap) throws Exception {
		LopHocPhan lopHocPhan = new LopHocPhan();
		List<DanhMucHP> dmhp = dmhpService.getDataFromApi();
		modelMap.addAttribute("lopHocPhan", lopHocPhan);
		modelMap.addAttribute("dmhp", dmhp);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/lophocphan/create";
	}
	
	@PostMapping("/create")
	public String themLopHocPhan(@ModelAttribute LopHocPhan lopHocPhan) throws JsonMappingException, JsonProcessingException {
		System.out.println(lopHocPhan);
		Boolean rs = lopHocPhanService.themLopHocPhan(lopHocPhan);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/lophocphan"; 
		}
		message.setMessage("Thêm mới thất bại!");
		message.setStatus(false);
		return "redirect:/admin/lophocphan/create"; 
		
	}
	
	@GetMapping("/update/{id}")
	public String capnhatLopHocPhan(@PathVariable Integer id, ModelMap modelMap) throws Exception {
		LopHocPhan lopHocPhan = lopHocPhanService.layLopHocPhan(id);
		modelMap.addAttribute("lopHocPhan", lopHocPhan);
		List<DanhMucHP> dmhp = dmhpService.getDataFromApi();
		modelMap.addAttribute("dmhp", dmhp);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/lophocphan/update";
	}
	
	@PostMapping("/update") 
	public String capnhatLopHocPhan(@ModelAttribute LopHocPhan lopHocPhan) {
		Boolean rs = lopHocPhanService.capnhatLopHocPhan(lopHocPhan);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/lophocphan"; 
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/lophocphan/update/" + lopHocPhan.getId(); 
	}
}
