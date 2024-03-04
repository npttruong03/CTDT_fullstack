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

import CTDTFullStack.Models.ChuyenNganh;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.ChuyenNganhService;
import CTDTFullStack.services.KhoaQuanlyService;
import CTDTFullStack.services.LinhVucService;
import CTDTFullStack.services.NganhService;
import jakarta.servlet.http.HttpServletRequest;

@Controller 
@RequestMapping("/admin/chuyennganh")
public class ChuyenNganhControler {
	@Autowired
	private ChuyenNganhService chuyenNganhService;
	private MessageResponse message;
	
	@Autowired
	private NganhService nganhService;
	
	@Autowired
	private LinhVucService linhVucService;
	
	@Autowired 
	private KhoaQuanlyService khoaQuanlyService;
	
	
	@GetMapping()
	public String getAll(ModelMap modelmap) throws JsonMappingException, JsonProcessingException {
		modelmap.addAttribute("chuyennganh", this.chuyenNganhService.getAllChuyenNganhAPI());
		if (message != null)
		{
			modelmap.addAttribute("message", message);
			message = null;
		}
		return "pages/forms/ChuyenNganh/chuyennganh";
	}
	
	@GetMapping("/create")
	public String creat(ModelMap modelMap, ChuyenNganh chuyenNganh) throws Exception {
		modelMap.addAttribute("chuyennganh", chuyenNganh);
		modelMap.addAttribute("nganhs", nganhService.GetAll());
		modelMap.addAttribute("linhvucs", linhVucService.getDataFromAPI());
		modelMap.addAttribute("khoaquanlys", khoaQuanlyService.getDataFromAPI());
		return "pages/forms/ChuyenNganh/addchuyennganh";
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String creatChuyenNganh(ChuyenNganh chuyenNganh, HttpServletRequest request){
       	 Boolean rs = chuyenNganhService.create(chuyenNganh);
       	 if (rs) {
       		 message = new MessageResponse();
       		 message.setMessage("Thêm mới chuyên ngành thành công!");
       		 message.setStatus(rs);
       		 return "redirect:/admin/chuyennganh";
       	 }
       	message = new MessageResponse();
  		 message.setMessage("Thêm mới chuyên ngành thất bại!");
  		 message.setStatus(rs);
       	 return "redirect:/admin/chuyennganh/create";
	}
	
	@GetMapping("/edit/{id}")
	public String showEdit(ModelMap modelMap, @PathVariable("id") int id, @Validated ChuyenNganh chuyenNganh) throws Exception {	   
		chuyenNganh = this.chuyenNganhService.getByID(id);
	    // Add common attributes
	    modelMap.addAttribute("chuyennganhs", chuyenNganh);
	    modelMap.addAttribute("nganhs", nganhService.GetAll());
	    modelMap.addAttribute("linhvucs", linhVucService.getDataFromAPI());
	    modelMap.addAttribute("khoaquanlys", khoaQuanlyService.getDataFromAPI());

	    return "pages/forms/ChuyenNganh/editchuyennganh";
	}

	
	@PostMapping("/edit/{id}")
	public String editNDchuongtrinh(@PathVariable("id") int id, @Validated ChuyenNganh chuyennganh) throws Exception {
		chuyennganh.setId(id);
		Boolean rs = chuyenNganhService.editChuyenNganh(chuyennganh, id);
		if (rs == true) {
			message = new MessageResponse();
	  		message.setMessage("Cập nhật chuyên ngành thành công!");
	  		message.setStatus(rs);
	  		return "redirect:/admin/chuyennganh";
		}
		message = new MessageResponse();
  		message.setMessage("cập nhật chuyên ngành thất bại do chuyên ngành đang được sử dụng");
  		message.setStatus(rs);
  		return "redirect:/admin/chuyennganh/edit/" + id;
	}
}
