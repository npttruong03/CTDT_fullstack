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

import CTDTFullStack.Models.LopHocPhan;
import CTDTFullStack.Models.SinhVien;
import CTDTFullStack.Models.SinhVienLopHocPhan;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.ChuyenNganhService;
import CTDTFullStack.services.DmhpService;
import CTDTFullStack.services.LopHocPhanService;
import CTDTFullStack.services.SinhVienLopHPService;
import CTDTFullStack.services.SinhVienService;

@Controller
@RequestMapping("/admin/chucnang")
public class SinhVienLopHPController {
	@Autowired
	SinhVienLopHPService sinhVienLopHPService;
	@Autowired
	LopHocPhanService lopHocPhanService;
	@Autowired
	ChuyenNganhService chuyenNganhService;
	@Autowired
	SinhVienService sinhVienService;
	@Autowired
	DmhpService dmhpService;
	MessageResponse message = null;
	@GetMapping("/dangkytinchi")
	public String quanLySinhVienLHP(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<SinhVien> listSV = sinhVienService.quanLySinhVien();
		modelMap.addAttribute("listSV", listSV);
		if (message != null) {
			modelMap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/svlhp/index";
	}	
	
	
	@GetMapping("/inbangdiem") 
	public String inBangDiem(ModelMap modelMap) throws JsonMappingException, JsonProcessingException {
		List<SinhVien> listSV = sinhVienService.quanLySinhVien();
		modelMap.addAttribute("listSV", listSV);
		return "pages/forms/inbangdiem/index";
	}
	
//	@GetMapping("/inbangdiem/{masv}")
//	public List<SinhVienLopHocPhan> inBangDiem(@PathVariable String masv) throws JsonMappingException, JsonProcessingException {
//		List<SinhVienLopHocPhan> list = sinhVienLopHPService.inBangDiem(masv);
//		return list;
//	}
}