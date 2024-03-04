package CTDTFullStack.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import CTDTFullStack.Models.DanhMucHP;
import CTDTFullStack.Models.MoiQuanHeHP;
import CTDTFullStack.config.AppConfig;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.DmhpService;
import CTDTFullStack.services.MoiQuanHeHPService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/mqhhocphan")
public class MoiQuanHeHPController {
	@Autowired
	MoiQuanHeHPService moiQuanHeHPService;
	@Autowired
	DmhpService dmhpService;
	MessageResponse message = null;
	
	private final AppConfig appConfig;

	@Autowired
	public MoiQuanHeHPController(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	@GetMapping
	public String quanLyLHP(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<MoiQuanHeHP> listMQHHP = moiQuanHeHPService.quanLyMQHHP();
		modelMap.addAttribute("listMQHHP", listMQHHP);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/mqhhocphan/index";
	}
	
	@GetMapping("/create")
	public String themMQHHPGET(ModelMap modelMap) throws Exception {
		MoiQuanHeHP mqh = new MoiQuanHeHP();
		String cookieValue = appConfig.cookieStore().getCookie();
		modelMap.addAttribute("cookieValue", cookieValue);
		modelMap.addAttribute("mqh", mqh);
		List<DanhMucHP> listDMHP = dmhpService.getDataStt();
		modelMap.addAttribute("listDMHP", listDMHP);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/mqhhocphan/create";
	}
	
	@PostMapping("/create")
	public void themMQHHPPOST(@RequestBody MoiQuanHeHP mqh, HttpServletResponse response) throws IOException {
		Boolean rs = moiQuanHeHPService.themMQHHP(mqh);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Thêm mới thành công!");
			message.setStatus(true);
			response.setStatus(200);
			return;
		}
		message.setMessage("Thêm mới thất bại!");
		message.setStatus(false);
		response.setStatus(500);
	}
	
	@GetMapping("/update/{id}")
	public String capnhatMQHHP(@PathVariable Integer id, ModelMap modelMap) throws Exception {
		MoiQuanHeHP mqh = moiQuanHeHPService.layMQHHP(id);
		String cookieValue = appConfig.cookieStore().getCookie();
		modelMap.addAttribute("cookieValue", cookieValue);
	
		modelMap.addAttribute("id", mqh.getId());
		modelMap.addAttribute("ma_hp", mqh.getMa_hoc_phan());
		modelMap.addAttribute("hoctruoc", mqh.getHoc_truoc());
		modelMap.addAttribute("tienquyet", mqh.getTien_quyet());
		modelMap.addAttribute("songhanh", mqh.getSong_hanh());
		modelMap.addAttribute("stt", mqh.isStt());
		List<DanhMucHP> listDMHP = dmhpService.getDataStt();
		modelMap.addAttribute("listDMHP", listDMHP);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/mqhhocphan/update";
	}
	
	@PostMapping("/update") 
	public void capnhatMQHHPPost(@RequestBody MoiQuanHeHP mqh, HttpServletResponse response) {
		Boolean rs = moiQuanHeHPService.capnhatMQHHP(mqh);
		message = new MessageResponse();
		if (rs) {
			message.setMessage("Cập nhật thành công!");
			message.setStatus(true);
			response.setStatus(200);
			return;
		}
		message.setMessage("Cập nhật thất bại!");
		message.setStatus(false);
		response.setStatus(500);
	}
	
}
