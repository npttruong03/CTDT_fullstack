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

import CTDTFullStack.Models.DanhMucHP;
import CTDTFullStack.Models.HPThayThe;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.DanhMucLHPService;
import CTDTFullStack.services.DmhpService;
import CTDTFullStack.services.HPTTService;
import ch.qos.logback.core.joran.conditional.IfAction;

@Controller
@RequestMapping("/admin/hptt")
public class HPTTController {
	@Autowired
	DmhpService dmhpService;
	
	@Autowired
	HPTTService hpttService;

	@Autowired
	DanhMucLHPService danhMucLHPService;
	MessageResponse message = null;

	@GetMapping
	public String index(ModelMap modelMap) throws Exception {
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/hptt/index";
	}

	@GetMapping("/create")
	public String showPost(ModelMap modelMap) throws Exception {
		HPThayThe hptt = new HPThayThe();
		modelMap.addAttribute("hptt", hptt);
		modelMap.addAttribute("dmhp", dmhpService.getDataFromApi());
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/hptt/create";
	
	}

	@PostMapping("/create")
	public String post(@ModelAttribute HPThayThe hptt) {
		System.out.println(hptt);
		try {
			
			HPThayThe hptta =  hpttService.post(hptt);
			if(hptta==null) {
				message.setMessage("Thêm mới thất bại! Học phần này đã có học phần thay thế, vui lòng sửa đổi hoặc chọn học phần cần thay thế khác");
				message.setStatus(false);
				return "redirect:/admin/hptt/create";
			}
			message = new MessageResponse();
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/hptt";

		} catch (Exception e) {
			message.setMessage("Thêm mới thất bại!");
			message.setStatus(false);
			return "redirect:/admin/hptt/create";
		}

	}

	@GetMapping("/update/{id}")
	public String showPut(@PathVariable Integer id, ModelMap modelMap) throws Exception {
		HPThayThe hptt = hpttService.getByid(id);
		modelMap.addAttribute("hptt", hptt);
		modelMap.addAttribute("dmhp", dmhpService.getDataFromApi());
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/hptt/update";
	}

	@PostMapping("/update/{id}")
	public String put(@ModelAttribute HPThayThe hptt, @Validated @PathVariable("id") int id) {
		Boolean rs = false;
		if (hpttService.put(hptt, id) != null) {
			rs = true;
		}
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/hptt";
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/hptt/update/" + id;
	}

}
