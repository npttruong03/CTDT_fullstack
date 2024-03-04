package CTDTFullStack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import CTDTFullStack.Models.KhoaQuanLy;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.KhoaQuanlyService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/khoaquanly")
public class KhoaQuanLyController {

	private MessageResponse messageResponse = null;
	@Autowired
	private KhoaQuanlyService khoaQuanlyService;
	private MessageResponse message;
	
	@GetMapping()
	public String getAll(ModelMap modelmap) throws JsonMappingException, JsonProcessingException {
		modelmap.addAttribute("khoaquanly", this.khoaQuanlyService.getDataFromAPI());
		if (message != null)
		{
			modelmap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/KhoaQuanLy/khoaquanly";
	}
	
	@GetMapping("/creat")
	public String creat(ModelMap modelMap, KhoaQuanLy khoaQuanLy) {
		modelMap.addAttribute("khoaquanly", khoaQuanLy);
		return "pages/forms/KhoaQuanLy/addkhoaquanly";
	}
	@RequestMapping(value = "/creat", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String creatKhoaQuanLy(KhoaQuanLy khoaQuanLy, HttpServletRequest request){
       	 khoaQuanlyService.creat(khoaQuanLy);
       	return "redirect:/admin/khoaquanly";
	}
	
//	@RequestMapping(value = "/creat", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public String creatNDchuongtrinh(@ModelAttribute("ndchuongtrinh") NDchuongtrinh nDchuongtrinh, HttpServletRequest request){
//        try {
//       	 nDchuongtrinhservice.creat(nDchuongtrinh);
//			message = new MessageResponse();
//			message.setStatus("success");
//			message.setMessage("Thêm Nội dung thành công!");
//			   return "redirect:/admin/NDchuongtrinh";
//
//		} catch (Exception e) {
//			message = new MessageResponse();
//			message.setStatus("fail");
//			message.setMessage("Thêm Nội dung không thành công!");
//			return "redirect:/admin/NDchuongtrinh/creat";
//		}
//	}
	
	@GetMapping("/edit/{id}")
	public String showEdit(ModelMap modelMap, @PathVariable("id") int id) throws Exception {
		KhoaQuanLy khoaQuanLy = this.khoaQuanlyService.getByID(id);
//		if(nDchuongtrinh.getId() == null) {
//			throw new Exception("không tìm thấy id nội dung" + id);
//		}
		modelMap.addAttribute("khoaquanly", khoaQuanLy);
		return "pages/forms/KhoaQuanLy/editkhoaquanly";
	}
	
	@PostMapping("/edit/{id}")
	public String editKhoaQuanLy(@PathVariable("id") int id, @Validated KhoaQuanLy khoaQuanLy) throws Exception {
		khoaQuanLy.setId(id);
		khoaQuanlyService.editKhoaquanly(khoaQuanLy, id);
		return "redirect:/admin/khoaquanly";
	}
}
