package kr.spring.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;

@Controller
public class MemberAdminController {
	private static final Logger logger = LoggerFactory.getLogger(MemberAdminController.class);
	
	private int rowCount=10;
	private int pageCount=10;	
	
	@Autowired
	private MemberService memberService;
	
	//==========회원목록==========//
	//전체 회원 조회
	@RequestMapping("/admin/admin_list.do")
	public ModelAndView processAll(@RequestParam(value="pageNum", defaultValue="1")int currentPage,
								@RequestParam(value="keyfield", defaultValue="")String keyfield,
								@RequestParam(value="keyword", defaultValue="")String keyword) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//총 글의 개수 또는 검색된 글의 개수
		int count = memberService.selectRowCount(map);
		
		logger.debug("<<count>>: "+count);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "admin_list.do");
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		
		List<MemberVO> list = null;
		if(count > 0)
			list = memberService.selectList(map);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin_memList");
		mav.addObject("count",count);
		mav.addObject("list", list);
		mav.addObject("page", page.getPage());
		
		return mav;
	}
	
	//탈퇴 회원 조회
		@RequestMapping("/admin/delete_list.do")
		public ModelAndView processDel(@RequestParam(value="pageNum", defaultValue="1")int currentPage,
									@RequestParam(value="keyfield", defaultValue="")String keyfield,
									@RequestParam(value="keyword", defaultValue="")String keyword) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("keyfield", keyfield);
			map.put("keyword", keyword);
			
			//총 글의 개수 또는 검색된 글의 개수
			int count = memberService.selectDelCount(map);
			
			logger.debug("<<count>>: "+count);
			
			//페이지 처리
			PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "admin_list.do");
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			List<MemberVO> list = null;
			if(count > 0)
				list = memberService.selectDelList(map);
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("admin_delList");
			mav.addObject("count",count);
			mav.addObject("list", list);
			mav.addObject("page", page.getPage());
			
			return mav;
		}
	
}
