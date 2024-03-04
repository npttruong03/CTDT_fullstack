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
import CTDTFullStack.Models.DinhHuong;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.DinhHuongService;

@Controller
@RequestMapping("/admin/dinhhuong")
public class DinhHuongController {
	@Autowired
	private DinhHuongService dinhHuongService;
	MessageResponse message = null;
	
	@GetMapping
	public String index(ModelMap modelMap) {
		if(message!=null) {
			modelMap.addAttribute("message",message );
			message=null;
		}
		List<DinhHuong> listdhs;
		try {
			listdhs = dinhHuongService.getDataFromApi();
			modelMap.addAttribute("listdhs", listdhs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "pages/forms/dinhhuong/index";
	}
	@GetMapping("/create")
	public String showCreate(ModelMap modelMap) {
		DinhHuong dh = new DinhHuong();
		modelMap.addAttribute("dh", dh);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/dinhhuong/create";
	}
	@PostMapping("/create")
	public String create(@ModelAttribute DinhHuong dh) {
		try {
			DinhHuong dinhHuong = dinhHuongService.post(dh);
			message = new MessageResponse();
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/dinhhuong";

		} catch (Exception e) {
			message.setMessage("Thêm mới thất bại!");
			message.setStatus(false);
			return "redirect:/admin/dinhhuong/create";
		}
	}
	
	@GetMapping("/update/{id}")
	public String showPut(@PathVariable Integer id, ModelMap modelMap) throws Exception {
		DinhHuong dh = dinhHuongService.getByid(id);
		modelMap.addAttribute("dh", dh);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/dinhhuong/update";
	}

	@PostMapping("/update/{id}")
	public String capnhatDMLHPPost(@ModelAttribute DinhHuong dh, @Validated @PathVariable("id") int id) {
		Boolean rs = false;
		if (dinhHuongService.put(dh, id) != null) {
			rs = true;
		}
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/dinhhuong";
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/dinhhuong/update/" + id;
	}
}
