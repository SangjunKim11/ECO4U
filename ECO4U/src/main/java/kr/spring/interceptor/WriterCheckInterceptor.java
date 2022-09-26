package kr.spring.interceptor;

import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.spring.community.service.CommunityService;
import kr.spring.community.vo.CommunityVO;
import kr.spring.member.vo.MemberVO;

public class WriterCheckInterceptor 
                        implements HandlerInterceptor{
	private static final Logger logger =
			LoggerFactory.getLogger(WriterCheckInterceptor.class);
	
	@Autowired
	private CommunityService communityService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			           HttpServletResponse response,
			           Object handler)throws Exception {
		logger.debug("<<로그인 회원번호와 작성자 회원번호 일치 여부 체크>>");
		//작성자의 회원번호 구하기
		int c_num = Integer.parseInt(
				request.getParameter("c_num")); 
		CommunityVO community = 
				communityService.selectCommunity(c_num);
		
		//로그인 회원번호 구하기
		HttpSession session = request.getSession();
		MemberVO user = 
			 (MemberVO)session.getAttribute("user");
		
		logger.debug("<<로그인 회원번호>> : " + user.getMem_num());
		logger.debug("<<작성자 회원번호>> : " + community.getMem_num());
		
		//로그인 회원번호와 작성자 회원번호 일치 여부 체크
		if(user==null || 
			user.getMem_num()!= community.getMem_num()) {
			if(user.getMem_num() != 201) {//관리자가 아닐 경우 / 관리자는 무조건 삭제 가능
			logger.debug("<<로그인 회원번호와 작성자 회원번호 불일치>>");
		
			//UI에 보여질 정보 저장
			request.setAttribute(
					"accessMsg", "로그인 아이디와 작성자 아이디 불일치");
			request.setAttribute(
					"accessBtn", "게시판 목록");
			request.setAttribute(
					"accessUrl", request.getContextPath()+"/community/list.do?c_category=1");
			
			//포워드 방식으로 화면 호출
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(
							"/WEB-INF/views/common/notice.jsp");
			dispatcher.forward(request, response);
			
			return false;
			}
		}
		
		logger.debug("<<로그인 회원번호와 작성자 회원번호 일치>>");
		
		return true;
	}
	
}



