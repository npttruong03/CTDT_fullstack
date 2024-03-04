package CTDTFullStack.controllers;

import java.util.List;

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
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.DanhMucLHPService;
import CTDTFullStack.services.DmhpService;
import CTDTFullStack.services.LoaiHocPhanService;
import ch.qos.logback.core.joran.conditional.IfAction;

@Controller
@RequestMapping("/admin/dmhp")
public class DmhpController {
	@Autowired
	DmhpService dmhpService;

	@Autowired
	LoaiHocPhanService lhpService;
	
	@Autowired
	DanhMucLHPService dMucLHPService;
	MessageResponse message = null;

	@GetMapping
	public String index(ModelMap modelMap) throws Exception {
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		List<DanhMucHP> listDMHP = dmhpService.getDataFromApi();
		modelMap.addAttribute("listDMHP", listDMHP);
		modelMap.addAttribute("listDMLHP", lhpService.quanLyLHP());
		modelMap.addAttribute("DMLHP", dMucLHPService.quanLyDMLHP());
//		if (message != null) {
//			modelMap.addAttribute("message", message);
//			message = null;
//		}
		return "pages/forms/DanhMucHP/dmhp";
	}

	@GetMapping("/create")
	public String showPost(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		DanhMucHP dmhp = new DanhMucHP();
		modelMap.addAttribute("dmhp", dmhp);
		modelMap.addAttribute("dmlhp", lhpService.quanLyLHP());
		modelMap.addAttribute("DMLHP", dMucLHPService.quanLyDMLHP());
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/DanhMucHP/create";
	
	}

	@PostMapping("/create")
	public String post(@ModelAttribute DanhMucHP danhMucHP) {

		try {
			DanhMucHP dmhp = dmhpService.post(danhMucHP);
			if (dmhp == null) {
				message = new MessageResponse();
				message.setMessage("Trùng mã học phần!");
				message.setStatus(false);
				return "redirect:/admin/dmhp/create";
			}
			message = new MessageResponse();
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/dmhp";

		} catch (Exception e) {
			message.setMessage("Thêm mới thất bại!");
			message.setStatus(false);
			return "redirect:/admin/dmhp/create";
		}

	}

	@GetMapping("/update/{id}")
	public String showPut(@PathVariable Integer id, ModelMap modelMap) throws Exception {
		DanhMucHP dmhp = dmhpService.getByid(id);
		modelMap.addAttribute("dmhp", dmhp);
		modelMap.addAttribute("dmlhp", lhpService.quanLyLHP());
		modelMap.addAttribute("DMLHP", dMucLHPService.quanLyDMLHP());
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/DanhMucHP/update";
	}

	@PostMapping("/update/{id}")
	public String capnhatDMLHPPost(@ModelAttribute DanhMucHP danhMucHP, @Validated @PathVariable("id") int id) {
		Boolean rs = false;
		if (dmhpService.put(danhMucHP, id) != null) {
			rs = true;
		}
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/dmhp";
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/dmhp/update/" + id;
	}

}