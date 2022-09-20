package kr.spring.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.product.service.ProductService;
import kr.spring.product.vo.ProductVO;
import kr.spring.util.PagingUtil;


@Controller
public class ProductAdminController {
	private static final Logger logger = LoggerFactory.getLogger(ProductAdminController.class);
	
	private int rowCount = 10;
	private int pageCount = 10;
	
	@Autowired
	private ProductService productService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public ProductVO initCommand() {
		return new ProductVO();
	}
	
	//==========상품 목록(관리자용)==========//
	@RequestMapping("/product/admin_plist.do")
	public ModelAndView adminList(@RequestParam(value="pageNum",defaultValue="1")int currentPage,
								  @RequestParam(value="keyfield",defaultValue="")String keyfield,
								  @RequestParam(value="keyword",defaultValue="")String keyword) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		//status가 0이면 표시/미표시 항목 모두 체크
		map.put("p_status", 0);
		
		//상품의 총 개수 또는 검색된 상품의 개수
		int count = productService.selectProductCount(map);
		
		logger.debug("<<count>>: "+count);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "admin_plist.do");
		
		List<ProductVO> list = null;
		
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = productService.selectProductList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("productAdminList");
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("page", page.getPage());
		
		return mav;
	}
	
	//==========상품 등록(관리자용)==========//
	//상품 등록 폼 호출
	@GetMapping("/product/admin_write.do")
	public String form() {
		return "productAdminWrite";
	}
	
	//폼에서 전송된 데이터 처리
	@PostMapping("/product/admin_write.do")
	public String submit(@Valid ProductVO vo, BindingResult result, HttpServletRequest request, HttpSession session, Model model) {
		logger.debug("<<상품등록>>: "+vo);
		
		//상품 대표사진 유효성 체크
		if(vo.getUpload()==null || vo.getUpload().isEmpty()) {
			result.rejectValue("upload", "required");
		}
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors())
			return form();
		
		//상품 등록 처리
		productService.insertProduct(vo);
		
		//View에 표시할 메시지 지정
		model.addAttribute("message","상품등록이 완료되었습니다.");
		model.addAttribute("url",request.getContextPath()+"/product/admin_plist.do");
		
		return "common/resultView";
	}
}
