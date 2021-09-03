package com.ncs.green;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.Criteria;
import criTest.PageMaker;
import criTest.SearchCriteria;
import lombok.extern.log4j.Log4j;
import service.BoardService;
import vo.BoardVO;
import vo.MemberVO;
import vo.PageVO;

@Log4j
@Controller
public class BoardController {

	@Autowired
	BoardService service;

	// ** Check_MemberList
	@RequestMapping(value = "/bchecklist")
	public ModelAndView bchecklist(ModelAndView mv, BoardVO vo) {

		List<BoardVO> list = null;

		// ** check 확인
		// => 없으면 : service.selectList() 와 같음
		// => 있으면 : 조건검색 service.checkList();
		if(vo.getCheck() != null) list=service.checkList(vo);
		else list = service.selectList();

		if(list != null && list.size() > 0) {
			// Mapper는 null을 return 하지 않으므로 길이로 확인
			mv.addObject("Banana",list);
		}else {
			mv.addObject("message","~~ 출력할 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("board/checkBList");		
		return mv;
	}//bchecklist
	
	// ** jsonView board Detail
	@RequestMapping(value = "/jsbdetail")
	public ModelAndView jsbdetail(HttpServletResponse response, ModelAndView mv, BoardVO vo) {
		
		response.setContentType("text/html; charset=UTF-8");
		
		vo = service.selectOne(vo);
		if (vo != null) {
			mv.addObject("content", vo.getContent());
		}else {
			mv.addObject("message", "~~ 글번호에 해당하는 자료가 존재하지 않습니다. ~~");
		}
		mv.setViewName("jsonView");
		return mv;
	} //aidblist
	
	
	// ** Ajax id_BoardList
	@RequestMapping(value = "/aidblist")
	public ModelAndView aidblist(ModelAndView mv, BoardVO vo) {
		List<BoardVO> list = service.aidBList(vo);
		if (list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("board/boardList");
		return mv;
	} //aidblist
	
	// ** Ajax BoardList 
	@RequestMapping(value = "/ablist")
	public ModelAndView ablist(ModelAndView mv) {
		List<BoardVO> list = service.selectList();
		if (list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("ajaxTest/axBoardList");
		return mv;
	} //ablist
	// **********************************************************
	
	// ** Board CriPageList
	@RequestMapping(value = "/bcplist")
	// ** ver01 
	//public ModelAndView bcplist(ModelAndView mv, Criteria cri, PageMaker pageMaker) {
	// ** ver02
	public ModelAndView bcplist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
		// => setCurrPage, setRowsPerPage 는 Parameter 로 전달되어,
		//    setCurrPage(..) , setRowsPerPage(..) 는 자동처리됨(스프링에 의해)
		//    -> cri.setCurrPage(Integer.parseInt(request.getParameter("currPage")))
		// => 그러므로 currPage 이용해서 sno, eno 계산만 하면됨
		cri.setSnoEno();
		
		// 2) 서비스 처리
		// ** ver01
		//mv.addObject("Banana",service.criPList(cri));
		// ** ver02 : searchType, keyword 에 따른 조건검색
		// => service 에 메서드 추가 searchList(cri) , searchRowsCount() 
		mv.addObject("Banana",service.searchList(cri));
		
		// 3) PageMaker 처리
		pageMaker.setCri(cri);
		// ** ver01
		//pageMaker.setTotalRowCount(service.totalRowsCount());
		// ** ver02
		pageMaker.setTotalRowCount(service.searchRowsCount(cri));
		
		System.out.println("*** pageMaker => "+pageMaker);
		mv.addObject("pageMaker",pageMaker);
		mv.setViewName("board/bCriList");
		return mv;
	} //bcplist 
	
	// ** BoardPageList 1. 
	@RequestMapping(value = "/bpagelist")
	public ModelAndView bpagelist(ModelAndView mv, PageVO<BoardVO> pvo) {
		// 1) Paging 준비
		// => 요청 Page 확인 : currPage ( Parameter )
		// => sno , eno 계산후 List 읽어오기
		// => totalRowCount : 전체Page수 계산
		int currPage = 1;
		if (pvo.getCurrPage() > 1) currPage = pvo.getCurrPage();
		else pvo.setCurrPage(currPage);
		
		int sno = (currPage-1)*pvo.getRowsPerPage() + 1 ;
		int eno = sno + pvo.getRowsPerPage() - 1 ;
		pvo.setSno(sno);
		pvo.setEno(eno);
		
		// 2) Service 처리
		// => List 읽어오기, 전체Row수(totalRowCount) 
		// => 전체 PageNo 계산하기
		pvo=service.pageList(pvo); // 대입문은 없어도 가능 (pvo는 참조형이므로)
		int totalPageNo = pvo.getTotalRowCount()/pvo.getRowsPerPage();
		if ( pvo.getTotalRowCount()%pvo.getRowsPerPage() !=0 )
			totalPageNo += 1;
		
		// 3) View 처리
		// view02 
		// => PageBlock 기능 추가 : sPageNo, ePageNo
		// => 이를 위해 currPage, pageNoCount
		// => 유형
		//    1) currPage 가 항상 중앙에 위치하도록 할때
		//int sPageNo = currPage - (pvo.getPageNoCount()/2);
		//int ePageNo = currPage + (pvo.getPageNoCount()/2);
		
		//    2) 11번가의 상품List, Naver 카페글 유형
		// 예를들어 currPage=3 이고 pageNoCount 가 3 이면 1,2,3 page까지 출력 되어야 하므로
		// 아래 처럼 currPage-1 을 pageNoCount 으로 나눈후 다시 곱하고 +1
		// currPage=11 -> 10,11,12, => (11-1)/3 * 3 +1 = 10
		// 연습 ( pageNoCount=5 )
		// -> currPage=11 인경우 : 11,12,13,14,15 -> ((11-1)/5)*5 +1 : 11
		// -> currPage=7 인경우 : 6,7,8,9,10 -> ((7-1)/5)*5 +1 : 6
		
		int sPageNo = ((currPage-1)/pvo.getPageNoCount())*pvo.getPageNoCount()+1;
		int ePageNo = (sPageNo + pvo.getPageNoCount()) - 1 ;
		// 계산으로 얻어진 ePageNo가 실제 LastPage 인 totalPageNo 보다 크면 수정 필요.
		if (ePageNo > totalPageNo) ePageNo = totalPageNo;
		
		mv.addObject("sPageNo", sPageNo) ;
		mv.addObject("ePageNo", ePageNo) ;
		mv.addObject("pageNoCount", pvo.getPageNoCount()) ;
		
		// view01 => currPage, totalPageNo, List 
		mv.addObject("currPage", pvo.getCurrPage());
		mv.addObject("totalPageNo", totalPageNo);
		
		if (pvo.getList() != null && pvo.getList().size() > 0) mv.addObject("Banana",pvo.getList());
		else mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다. ~~");
		
		mv.setViewName("board/pageBList");
		return mv;
	} //bpagelist
	
	// ** @Log4j Test
	@RequestMapping(value = "/logj")
	public ModelAndView logj(ModelAndView mv) {
		log.info("** Log4j Test : info **");
		log.error("** Log4j Test : error **");
		mv.setViewName("home");
		return mv;
	} //logj	
	
	// ** 답글달기
	@RequestMapping(value = "/replyf")
	public ModelAndView replyf(ModelAndView mv, BoardVO vo) {
		// => vo 에는 전달된 부모글의 root, step, indent 가 담겨있음 
		// => 매핑메서드의 인자로 정의된 vo 는 request.setAttribute 와 동일 scope
		//    단, 클래스명의 첫글자를 소문자로 ...  ${boardVO.root}
		log.info("** replyf vo :"+vo);
		mv.setViewName("board/replyForm");
		return mv;
	} //replyf 
	
	@RequestMapping(value = "/reply")
	public ModelAndView reply(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		// ** 답글 입력 Service
		// => 성공 : blist
		// => 실패 : 재입력 유도 (replyForm) 
		
		// => 전달된 vo 에 담겨있는 value : id, title, content 
		// => 추가적으로 필요한 value : 부모글의 root, step, indent
		//    root : 부모글의 root와 동일
		//    step: 부모글의 step+1 
		//    (기존 답글의 step 값이 현재 계산된 이 값보다 같거나 큰 값은 +1 : sql 에서 처리) 
		//    indent: 부모글의 indent+1 
		// => 이를 위해 boardDetail 에서 요청시 퀴리스트링으로 전달 -> replyf  
		// => 부모글의 root, step, indent 를 replyForm 에서 hidden으로 처리한 후 
		//    전달된 vo 에는 이 값이 담겨있으므로 step, indent 만 1씩 증가해주면 됨.
		
		vo.setStep(vo.getStep()+1);
		vo.setIndent(vo.getIndent()+1);
		//System.out.println("** replyTest **"+vo);
		if (service.replyInsert(vo) > 0) {
				// 답글 입력 성공
			rttr.addFlashAttribute("message", "~~ 답글 등록 성공 ~~");
		}else { // 답글 입력 실패
			rttr.addFlashAttribute("message", "~~ 답글 등록 실패 ~~");
		}
		mv.setViewName("redirect:blist");
		return mv;
	} //reply 
	
	// **************************************
	// Board CRUD
	@RequestMapping(value = "/blist")
	public ModelAndView blist(ModelAndView mv) {

		List<BoardVO> list = service.selectList() ;
		if (list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 없습니다 ~~");
		}
		mv.setViewName("board/boardList");
		return mv;
	} //blist

	@RequestMapping(value = "/bdetail")
	public ModelAndView bdetail(HttpServletRequest request, ModelAndView mv, 
			BoardVO vo, RedirectAttributes rttr) {
		// ** Detail 처리 조건
		// => 로그인 했을때만 글내용을 볼 수 있도록 ( boardList.jsp 에서 처리 ) 
		// => 조회수 증가 
		//    글쓴이(Parameter 로 전달) 와 글보는이(loginID) 가 달라야 함.

		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("loginID") != null) {
			String loginID = (String)session.getAttribute("loginID");
			// 글쓴이(Parameter 로 전달) 와 글보는이(loginID) 가 다른경우에만 조회수 증가
			if (!loginID.equals(vo.getId())) {
				service.countUp(vo) ;
			} 
			// 글내용 조회
			vo = service.selectOne(vo);
			if (vo != null) {
				request.setAttribute("Apple", vo);
				// 글 수정 하기의 경우 
				if ("U".equals(request.getParameter("jcode"))) {
					mv.setViewName("board/bupdateForm");
				}else mv.setViewName("board/boardDetail");  
			}else {
				rttr.addFlashAttribute("message", "~~ 글번호에 해당하는 글을 찾을 수 없습니다 ~~");
				mv.setViewName("redirect:blist"); 
			}
		}else {
			mv.addObject("message", "~~ 로그인 정보가 없습니다 !! 로그인 후 다시 하세요  ~~");
			mv.setViewName("member/loginForm"); 
		}
		return mv;
	} //bdetail

	// ** 새글등록
	@RequestMapping(value = "/binsertf")
	public ModelAndView binsertf(ModelAndView mv) {
		mv.setViewName("board/binsertForm");
		return mv;
	} //binsertf
	
	@RequestMapping(value = "/binsert")
	public ModelAndView binsert(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {

		if ( service.insert(vo) > 0) {
			rttr.addFlashAttribute("message", "~~ 새글 등록 성공 ~~");
			mv.setViewName("redirect:blist"); 
		}else {
			mv.addObject("message", "~~ 새글 등록 실패 !! 다시 하세요 ~~");
			mv.setViewName("board/binsertForm");
		}
		return mv;
	} //binsert	

	@RequestMapping(value = "/bupdate")
	public ModelAndView bupdate(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		if (service.update(vo) > 0) {
			rttr.addFlashAttribute("message", "~~ 글수정 성공 ~~");
			mv.setViewName("redirect:blist");
		}else {
			rttr.addFlashAttribute("message", "~~ 글수정 실패 !!! 다시 하세요 ~~");
			mv.setViewName("redirect:bdetail?seq="+vo.getSeq()+"&jcode=U");
		}
		return mv;
	} //bupdate	
	
	@RequestMapping(value = "/bdelete")
	public ModelAndView bdelete(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		if (service.delete(vo) > 0) {
			rttr.addFlashAttribute("message", "~~ 글삭제 성공 ~~");
			mv.setViewName("redirect:blist");
		}else {
			rttr.addFlashAttribute("message", "~~ 글삭제 실패 !!! 다시 하세요 ~~");
			mv.setViewName("redirect:bdetail?seq="+vo.getSeq());
		}
		return mv;
	} //bdelete	

} // class
