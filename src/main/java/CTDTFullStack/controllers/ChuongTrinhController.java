package CTDTFullStack.controllers;

import java.net.URLEncoder;
import java.util.List;
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
import CTDTFullStack.Models.ChuyenNganh;
import CTDTFullStack.Models.KhoaQuanLy;
import CTDTFullStack.Models.Nganh;
import CTDTFullStack.config.AppConfig;
import CTDTFullStack.response.MessageResponse;
import CTDTFullStack.services.ChuongtrinhService;
import CTDTFullStack.services.ChuyenNganhService;
import CTDTFullStack.services.KhoaQuanlyService;
import CTDTFullStack.services.NganhService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/chuongtrinh")
public class ChuongTrinhController {
	@Autowired
	private ChuongtrinhService chuongtrinhService;
	@Autowired
	private NganhService nganhService;
	@Autowired
	private ChuyenNganhService chuyenNganhService;
	@Autowired
	private KhoaQuanlyService khoaQuanlyService;
	private MessageResponse message = null;
    private static final Logger logger = Logger.getLogger(ChuongTrinhController.class.getName());

    @GetMapping()
    public String getChuongTrinh(ModelMap modelMap, @RequestParam(value = "keyword", required = false) String keyword)
            throws Exception {
        logger.info("Nhận yêu cầu để lấy thông tin chương trình");

        List<Chuongtrinh> chuongtrinhs;     

        if (keyword == null) {
            logger.info("Lấy thông tin chương trình");            
            chuongtrinhs = this.chuongtrinhService.GetAll();
        } else {
            logger.info("Tìm kiếm chương trình với từ khóa: " + keyword);
            // Mã hóa tham số keyword trước khi sử dụng nó trong URL
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            // Sử dụng encodedKeyword khi tạo URL
            chuongtrinhs = this.chuongtrinhService.SearchCT(encodedKeyword);
        }

        // Populate KhoaQuanLy information for each Chuongtrinh entity
        for (Chuongtrinh chuongtrinh : chuongtrinhs) {
            int nganhId = chuongtrinh.getId_nganh();
            int ChuyennganhId = chuongtrinh.getId_chuyen_nganh();
            Nganh nganh = nganhService.getById(nganhId);
            ChuyenNganh chuyennganh = chuyenNganhService.getByID(ChuyennganhId);
            chuongtrinh.setChuyenNganh(chuyennganh);          
            chuongtrinh.setNganh(nganh);
        }       
     

        modelMap.addAttribute("chuongtrinhs", chuongtrinhs);

        if (message != null) {
            modelMap.addAttribute("message", message);
            message = null;
        }

        return "pages/forms/chuongtrinh/chuongtrinh";
    }

    
	//create
		@GetMapping("/create")
	    public String addChuongtrinh(ModelMap modelMap) throws JsonMappingException, JsonProcessingException
	    {
			Chuongtrinh chuongtrinh = new Chuongtrinh();
	    	modelMap.addAttribute("chuongtrinhc", chuongtrinh);
	    	modelMap.addAttribute("nganh", this.nganhService.GetAll());
	    	modelMap.addAttribute("chuyennganhs", this.chuyenNganhService.getAllChuyenNganhAPI());
	    	
	    	if (message != null)
			{
				modelMap.addAttribute("message", message);
				message = null;
			}
	    	return "pages/forms/chuongtrinh/add_chuongtrinh";
	    }
		@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		  public String createContent(Chuongtrinh chuongtrinh, HttpServletRequest request)  {
			 chuongtrinhService.post(chuongtrinh);
			 message = new MessageResponse();
			 
			 message.setMessage("Thêm ngành thành công!");
			 message.setStatus(true);
			 return "redirect:/admin/chuongtrinh";
		    }
		
//		 --------------------get and edit---------------------
		 @GetMapping("/edit/{id}")
		    public String showEdit(@PathVariable int id, ModelMap modelMap) {
		        Chuongtrinh chuongtrinh;
				try {
					chuongtrinh = chuongtrinhService.getById(id);
			        modelMap.addAttribute("chuongtrinh", chuongtrinh);
			        modelMap.addAttribute("nganh", this.nganhService.GetAll());
			    	modelMap.addAttribute("chuyennganhs", this.chuyenNganhService.getAllChuyenNganhAPI());
			    	modelMap.addAttribute("khoa", this.khoaQuanlyService.getDataFromAPI());


			        
				} catch (Exception e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}

		        return "pages/forms/chuongtrinh/edit_chuongtrinh";
		    }


		 @PostMapping("/edit/{id}")
		 public String edit(@PathVariable("id") int id, Chuongtrinh chuongtrinh, HttpServletRequest request) {
			 chuongtrinh.setId(id);
		     chuongtrinhService.editChuongtrinh(chuongtrinh);
		     message = new MessageResponse();
		     message.setStatus(true);
		     message.setMessage("Cập nhật chương trình thành công!");
		     return "redirect:/admin/chuongtrinh";
		 }
//		 --------------------search----------------
		 
	}