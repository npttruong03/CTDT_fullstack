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
import CTDTFullStack.Models.NDCT;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.ChuongtrinhService;
import CTDTFullStack.services.DanhMucLHPService;
import CTDTFullStack.services.DinhHuongService;
import CTDTFullStack.services.DmhpService;
import CTDTFullStack.services.HPTTService;
import CTDTFullStack.services.KKTService;
import CTDTFullStack.services.LoaiHocPhanService;
import CTDTFullStack.services.MoiQuanHeHPService;
import CTDTFullStack.services.NDCTService;
import ch.qos.logback.core.joran.conditional.IfAction;

@Controller
@RequestMapping("/admin/ndct")
public class NDCTController {
	@Autowired
	NDCTService ndctService;

	@Autowired
	KKTService kktService;
	
	@Autowired
	DmhpService dmhpService;
	
	@Autowired
	DinhHuongService dinhHuongService;
	
	@Autowired
	MoiQuanHeHPService moiQuanHeHPService;
	
	@Autowired
	HPTTService hpttService;
	
	@Autowired
	ChuongtrinhService chuongtrinhService;
	
	@Autowired
	DanhMucLHPService dMucLHPService;
	MessageResponse message = null;

	@GetMapping
	public String index(ModelMap modelMap) throws Exception {
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		List<NDCT> listND = ndctService.getDataFromApi();
		modelMap.addAttribute("listND", listND);
		modelMap.addAttribute("DMLHP", dMucLHPService.quanLyDMLHP());
//		if (message != null) {
//			modelMap.addAttribute("message", message);
//			message = null;
//		}
		return "pages/forms/ndct/index";
	}

	@GetMapping("/create")
	public String showPost(ModelMap modelMap) throws Exception {
		NDCT ndct = new NDCT();
		modelMap.addAttribute("ndct", ndct);
		modelMap.addAttribute("mhp", dmhpService.getDataFromApi());
		modelMap.addAttribute("DMLHP", dMucLHPService.quanLyDMLHP());
		modelMap.addAttribute("kkt", kktService.getDataFromApi());
		modelMap.addAttribute("dh", dinhHuongService.getDataFromApi());
		modelMap.addAttribute("mqh", moiQuanHeHPService.quanLyMQHHP());
		modelMap.addAttribute("hptt", hpttService.getDataFromApi());
		modelMap.addAttribute("ct", chuongtrinhService.GetAll());
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/ndct/create";
	
	}

	@PostMapping("/create")
	public String post(@ModelAttribute NDCT ndct) {

		try {
			NDCT nd = ndctService.post(ndct);
			message = new MessageResponse();
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			return "redirect:/admin/ndct";

		} catch (Exception e) {
			message.setMessage("Thêm mới thất bại!");
			message.setStatus(false);
			return "redirect:/admin/ndct/create";
		}

	}

	@GetMapping("/update/{id}")
	public String showPut(@Validated @PathVariable("id") int id, ModelMap modelMap) throws Exception {
		NDCT ndct = ndctService.getByid(id);
		modelMap.addAttribute("ndct", ndct);
		modelMap.addAttribute("mhp", dmhpService.getDataFromApi());
		modelMap.addAttribute("DMLHP", dMucLHPService.quanLyDMLHP());
		modelMap.addAttribute("kkt", kktService.getDataFromApi());
		modelMap.addAttribute("dh", dinhHuongService.getDataFromApi());
		modelMap.addAttribute("mqh", moiQuanHeHPService.quanLyMQHHP());
		modelMap.addAttribute("hptt", hpttService.getDataFromApi());
		modelMap.addAttribute("ct", chuongtrinhService.GetAll());
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/ndct/update";
	}

	@PostMapping("/update/{id}")
	public String capnhatDMLHPPost(@ModelAttribute NDCT ndct, @Validated @PathVariable("id") int id) {
		Boolean rs = false;
		if (ndctService.put(ndct, id) != null) {
			rs = true;
		}
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			return "redirect:/admin/ndct";
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		return "redirect:/admin/ndct/update/" + id;
	}

}
