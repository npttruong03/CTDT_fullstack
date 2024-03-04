package CTDTFullStack.controllers;

import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import CTDTFullStack.Models.LinhVuc;
import CTDTFullStack.Models.SinhVien;
import CTDTFullStack.Models.LinhVuc;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.KhoaQuanlyService;
//import CTDTFullStack.Models.Message;
import CTDTFullStack.services.LinhVucService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/linhvuc")
public class LinhVucController {

    private static final Logger logger = Logger.getLogger(LinhVucController.class.getName());
	private MessageResponse message;
    @Autowired
    private LinhVucService linhVucService;


    @GetMapping
    public String getLinhVuc(ModelMap modelMap, @RequestParam(value = "keyword", required = false) String keyword) throws Exception {
        logger.info("Nhận yêu cầu để lấy thông tin lĩnh vực");
        if (keyword == null) {
            logger.info("Lấy thông tin lĩnh vực");
            modelMap.addAttribute("linhvucs", this.linhVucService.getDataFromAPI());
        } else {
            logger.info("Tìm kiếm lĩnh vực với từ khóa: " + keyword);
            // Mã hóa tham số keyword trước khi sử dụng nó trong URL
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            // Sử dụng encodedKeyword khi tạo URL
            modelMap.addAttribute("linhvucs", this.linhVucService.searchDataFromAPI(encodedKeyword));
        }
        return "pages/forms/linhvuc/linhvuc";
    } 
  //create
  	@GetMapping("/create")
      public String addLinhVuc(ModelMap modelMap) throws JsonMappingException, JsonProcessingException
      {
  		LinhVuc linhVuc = new LinhVuc();
      	modelMap.addAttribute("linhvucs", linhVuc);	
      	if (message != null)
  		{
  			modelMap.addAttribute("message", message);
  			message = null;
  		}
      	return "pages/forms/linhvuc/add_linhvuc";
      }
  	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  	  public String createContent(LinhVuc linhvuc, HttpServletRequest request)  {
  		Boolean rs =  linhVucService.post(linhvuc);
  		 message = new MessageResponse();
  		 if(rs) {
  			message.setStatus(true);
  	  		 message.setMessage("Thêm ngành thành công!"); 
  		 }
  		 return "redirect:/admin/linhvuc";

  	}
//  	 --------------------get and edit---------------------
  	 @GetMapping("/edit/{id}")
  	    public String showEdit(@PathVariable int id, ModelMap modelMap) {
  	        LinhVuc linhvuc;
  			try {
  				linhvuc = linhVucService.getById(id);
  		    	modelMap.addAttribute("linhvucs",linhvuc);	
  			} catch (Exception e) {
  				e.printStackTrace();
  			}
  	        return "pages/forms/linhvuc/edit_linhvuc";
  	    }
  	 @PostMapping("/edit/{id}")
  	 public String edit(@PathVariable("id") int id, LinhVuc linhvuc, HttpServletRequest request) {
  		 linhvuc.setId(id);
  		 linhVucService.editLinhVuc(linhvuc);
  	     message = new MessageResponse();
  	     message.setStatus(true);
  	     message.setMessage("Cập nhật ngành thành công!");
  	     return "redirect:/admin/linhvuc";
  	 }
}
