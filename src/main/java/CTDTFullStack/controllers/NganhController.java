package CTDTFullStack.controllers;

import java.net.URLEncoder;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import CTDTFullStack.Models.Chuongtrinh;
import CTDTFullStack.Models.LinhVuc;
import CTDTFullStack.Models.Nganh;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.KhoaQuanlyService;
import CTDTFullStack.services.LinhVucService;
import CTDTFullStack.services.NganhService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/nganh")
public class NganhController {
	@Autowired
	private NganhService nganhService;
	@Autowired
	private LinhVucService linhVucService;
	@Autowired
	private KhoaQuanlyService khoaQuanlyService;
	private MessageResponse message;
    private static final Logger logger = Logger.getLogger(NganhController.class.getName());

	@GetMapping()  
    public String getNganh(ModelMap modelMap, @RequestParam(value = "keyword", required = false) String keyword)
            throws Exception {
        logger.info("Nhận yêu cầu để lấy thông tin ngành");

        if (keyword == null) {
            logger.info("Lấy thông tin ngành");
            modelMap.addAttribute("nganhs", this.nganhService.GetAll());

        } else {
            logger.info("Tìm kiếm ngành với từ khóa: " + keyword);
            // Mã hóa tham số keyword trước khi sử dụng nó trong URL
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            // Sử dụng encodedKeyword khi tạo URL
            modelMap.addAttribute("nganhs", this.nganhService.SearchNganh(encodedKeyword));
        }
        if (message != null) {
            modelMap.addAttribute("message", message);
            message = null;
        }

        return "pages/forms/nganh/nganh";
    }
	
	//create
	@GetMapping("/create")
    public String addNganh(ModelMap modelMap) throws JsonMappingException, JsonProcessingException
    {
		Nganh nganh = new Nganh();

    	modelMap.addAttribute("nganh", nganh);
    	modelMap.addAttribute("linhvuc", this.linhVucService.getDataFromAPI());
    	modelMap.addAttribute("khoa", this.khoaQuanlyService.getDataFromAPI());
    	
    	if (message != null)
		{
			modelMap.addAttribute("message", message);
			message = null;
		}
    	return "pages/forms/nganh/add_nganh";
    }
	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	  public String createContent(Nganh nganh, HttpServletRequest request)  {
		
		 nganhService.post(nganh);
		 message = new MessageResponse();

		 message.setStatus(true);
		 message.setMessage("Thêm ngành thành công!");
		 return "redirect:/admin/nganh";
	    } 
	
//	 --------------------get and edit---------------------
	 @GetMapping("/edit/{id}")
	    public String showEdit(@PathVariable int id, ModelMap modelMap) {
	        Nganh nganh;
			try {
				nganh = nganhService.getById(id);
		        modelMap.addAttribute("nganh", nganh);
		    	modelMap.addAttribute("linhvuc", this.linhVucService.getDataFromAPI());
		    	modelMap.addAttribute("khoa", this.khoaQuanlyService.getDataFromAPI());


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

	        return "pages/forms/nganh/edit_nganh"; 
	    }

	 @PostMapping("/edit/{id}")
	 public String edit(@PathVariable("id") int id, Nganh nganh, HttpServletRequest request) {
	     nganh.setId(id);
	     
	     // Assuming editNganh returns true on success and false on failure
	     if (nganhService.editNganh(nganh) != null) {
	         message = new MessageResponse();
	         message.setStatus(true);
	         message.setMessage("Cập nhật ngành thành công!");
	     } else {
	         message = new MessageResponse();
	         message.setStatus(false);
	         message.setMessage("Cập nhật ngành không thành công!");
	     }

	     return "redirect:/admin/nganh"; 
	 }

}
