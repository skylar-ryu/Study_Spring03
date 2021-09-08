package com.ncs.green;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.MemberService;
import vo.MemberVO;
import vo.PageVO;

@Controller
public class MemberController {
	
	@Autowired
	MemberService service ;
	@Autowired
	PasswordEncoder passwordEncoder;
	// PasswordEncoder interface 구현 클래스
	// => Pbkdf2PasswordEncoder, BCryptPasswordEncoder, 
	//    SCryptPasswordEncoder, StandardPasswordEncoder, 
	//    NoOpPasswordEncoder
	// => 대표적인 BCryptPasswordEncoder root-context.xml 또는 servlet-context.xml 에 bean 설정
	
// ** Check_MemberList	
	@RequestMapping(value = "/mchecklist")
	public ModelAndView mchecklist(ModelAndView mv, MemberVO vo) {
		
		List<MemberVO> list = null;
		
		// ** check 확인
		// => vo 에 check 컬럼을 추가하면 편리 
		// => 없으면 : service.selectList();
		// => 있으면 : 조건검색 service.checkList(); -> 서비스에 메서드 추가
		
		// if (vo.getCheck() != null && vo.getCheck().length > 0) { .... }
		// => 배열 Type 의 경우 선택하지 않으면 check=null 이므로 길이비교 필요없음. 
		
 		if (vo.getCheck() != null) list= service.checkList(vo);
		else list = service.selectList();
		
		if (list != null && list.size() > 0) {
			// Mapper 는 null 을 return 하지 않으므로 길이로 확인
			mv.addObject("Banana",list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("member/checkMList");
		return mv;
	} //mchecklist
	
// ** Json Delete
	@RequestMapping(value = "/jsdelete")
	public ModelAndView jsdelete(ModelAndView mv, MemberVO vo) {
		
		if (service.delete(vo) > 0) {
			mv.addObject("success", "T");
		}else {
			mv.addObject("success", "F");
		}
		mv.setViewName("jsonView");
		return mv;
	} //jsdelete
	
// ** Image DownLoad
	@RequestMapping(value = "/dnload")
	public ModelAndView dnload(HttpServletRequest request, ModelAndView mv, 
						@RequestParam("dnfile") String dnfile) {
						// String dnfile = request.getParameter("dnfile"); 과 동일구문
		
		String realPath = request.getRealPath("/"); // deprecated Method
		String fileName = dnfile.substring(dnfile.lastIndexOf("/")+1);
				
		// ** 위 의 위치를 이용해서 실제 저장위치 확인 
		// => 개발중인지, 배포했는지 에 따라 결정
		if (realPath.contains(".eclipse."))
			 realPath = "D:/MTest/MyWork/Spring03/src/main/webapp/resources/uploadImage/"+fileName;
		else realPath += "resources\\uploadImage\\"+fileName; 
		File file = new File(realPath) ;
		mv.addObject("downloadFile", file);
		mv.setViewName("download");
		// ** 일반적인 경우 ~/views/download.jsp 를 찾음, 그러나 이 경우에는 아님
		// => servlet-context.xml 에 설정하는 view 클래스 (DownloadView.java) 의
		// id 와 동일 해야함.
		return mv;
		// ** 위 addOb.. , setView.., return..  3 구문은 아래처럼 작성도 가능.
		// => return new ModelAndView("download", "downloadFile", file);
		// => 생성자 참고 
		//    public ModelAndView(View view, String modelName, 
		//     				Object modelObject) { 
		//     		this.view = view; addObject(modelName, modelObject); }
	} //dnload
	
// *** JSON : 제이슨, JavaScript Object Notation
// 자바스크립트의 객체 표기법으로, 데이터를 전달 할 때 사용하는 표준형식.
// 속성(key) 과 값(value) 이 하나의 쌍을 이룸
		
// ** JAVA의 Data 객체 -> JSON 변환하기
// 1) GSON
// : 자바 객체의 직렬화/역직렬화를 도와주는 라이브러리 (구글에서 만듦)
// 즉, JAVA객체 -> JSON 또는 JSON -> JAVA객체
		
// 2) @ResponseBody (매핑 메서드에 적용)
// : 메서드의 리턴값이 View 를 통해 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
// 이때 쓰여지기전, 리턴되는 데이터 타입에 따라 종류별 MessageConverter에서 변환이 이뤄진다.
// MappingJacksonHttpMessageConverter 를 사용하면 request, response 를 JSON 으로 변환
// view (~.jsp) 가 아닌 Data 자체를 전달하기위한 용도
// @JsonIgnore : VO 에 적용하면 변환에서 제외

// 3) jsonView
// => Spring 에서 MappingJackson2JsonView를 사용해서
// ModelAndView를 json 형식으로 반환해 준다.
// => 방법
// -> pom dependency추가 , 설정화일 xml 에 bean 등록
// -> return할 ModelAndView 생성시 View를 "jsonView"로 설정

// ** Json Login Test	
// => viewName 을 "jsonView"	로
// => addObject
//	-> 성공 : loginSuccess = 'T'
//	-> 실패 : loginSuccess = 'F' , 실패 message
	
	// ** JsonLogin
	@RequestMapping(value = "/jslogin")
	public ModelAndView jslogin(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) {

		// jsonView 사용시 response 의 한글 처리
		response.setContentType("text/html; charset=UTF-8");
		
		String password = vo.getPassword();
		vo = service.selectOne(vo);
		if (vo != null) {
			if (vo.getPassword().equals(password)) {
				// 로그인 성공 : 로그인정보 session에 보관,  home으로
				mv.addObject("loginSuccess","T");
				request.getSession().setAttribute("loginID",vo.getId());
				request.getSession().setAttribute("loginName",vo.getName());
			}else {
				// password 오류 : message , 재로그인 유도 (loginForm 으로)
				mv.addObject("loginSuccess","F");
				mv.addObject("message"," password 오류 !! 다시 하세요 ~~");
			}
		}else {
			// ID 오류
			mv.addObject("loginSuccess","F");
			mv.addObject("message"," ID 오류 !! 다시 하세요 ~~");
		}
		mv.setViewName("jsonView");
		return mv;
	} //jslogin
	
	// ** Ajax MemberList
	@RequestMapping(value = "/amlist")
	public ModelAndView amlist(ModelAndView mv) {
		List<MemberVO> list = service.selectList();
		if (list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("ajaxTest/axMemberList");
		return mv;
	} //amlist
	
	// ** Member SearchCriteria PageList
	@RequestMapping(value = "/mcplist")
	public ModelAndView mcplist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
		cri.setSnoEno();
		// 2) 서비스 처리
		mv.addObject("Banana",service.searchList(cri));
		// 3) PageMaker 처리
		pageMaker.setCri(cri);
		pageMaker.setTotalRowCount(service.searchRowsCount(cri));
		
		mv.addObject("pageMaker",pageMaker);
		mv.setViewName("member/mCriList");
		return mv;
	} //mcplist 
	
	// ** ID 중복확인
	@RequestMapping(value = "/idCheck")
	public ModelAndView idCheck(ModelAndView mv, MemberVO vo) {
		// => 전달된 ID 의 존재여부 확인
		// => notNull : 존재 -> 사용불가 
		// => Null : 없음 -> 사용가능
		// => 그러므로 전달된 ID 보관해야함
		mv.addObject("newID", vo.getId());
		if (service.selectOne(vo) != null) {
			  mv.addObject("idUse", "F"); // 사용불가
		}else mv.addObject("idUse", "T"); // 사용가능
		mv.setViewName("member/idDupCheck");
		return mv;
	} //idCheck
	
	// ** Mermber PageList : PageBlock
	@RequestMapping(value = "/mpagelist")
	public ModelAndView bpagelist(ModelAndView mv, PageVO<MemberVO> pvo) {
		// 1) Paging 준비
		// => 요청 Page 확인, sno , eno 계산 
	
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
		// => PageBlock 기능 : sPageNo, ePageNo 계산
		
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
		
		mv.setViewName("member/pageMList");
		return mv;
	} //mpagelist

	@RequestMapping(value = "/mlist")
	public ModelAndView mlist(ModelAndView mv) {

		List<MemberVO> list = service.selectList();
		if (list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 한건도 없습니다 ~~") ;
		}
		mv.setViewName("member/memberList");
		return mv;
	} //mlist

	// ** Loginf	
	@RequestMapping(value = "/loginf")
	public ModelAndView loginf(ModelAndView mv) {
		mv.setViewName("member/loginForm");
		return mv;
	} //loginf
	
	// ** Login
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		String password = vo.getPassword(); //User가 입력한값
		// => 입력값의 오류에 대한 확인은 UI 에서 JavaScript로 처리
		vo = service.selectOne(vo);
		if (vo != null) {
			// ** ID 가 일치함
			//if (vo.getPassword().equals(password)) {
			
			// ** BCryptPasswordEncoder 적용
			// => passwordEncoder.matches(rawData, digest) -> (입력값, DB에보관된값_digest)
			if (passwordEncoder.matches(password, vo.getPassword())) { 
				// vo.getPassword() : digest 즉 암호화된값 
				// 로그인 성공 : 로그인정보 session에 보관,  home으로
				request.getSession().setAttribute("loginID",vo.getId());
				request.getSession().setAttribute("loginName",vo.getName());
				// BCryptPasswordEncoder 로 암호화 되면 복호화가 불가능함.
				// => password 수정 을 별도로 처리해야 함.
				// => 그러나 기존의 update  Code 를 활용하여 updateForm.jsp 에서 수정을 위해
				//    User가 입력한 raw_password 를 보관함. 
				request.getSession().setAttribute("loginPW",password);
				// => 수정시에만 사용
				
				mv.setViewName("redirect:home");
			}else {
				// password 오류 : message , 재로그인 유도 (loginForm 으로)
				mv.addObject("message"," password 오류 !! 다시 하세요 ~~");
				mv.setViewName("member/loginForm");
			}
		}else {
			// ID 오류
			mv.addObject("message"," ID 오류 !! 다시 하세요 ~~");
			mv.setViewName("member/loginForm");
		}
		return mv;
	} //login

	// ** redirect 시 message 처리하기
	// => RedirectAttributes
	//   - addFlashAttribute("message",message)
	//     -> 세션을 통해 url에 표시되지 않게 보낼 수 있으면서, 일회성 임.
	//        session 에 보관되므로 url에 붙지않기 때문에 깨끗하고 f5(새로고침)에 영향을 주지않음 
	//        즉, home 에서 다시 request 처리하지않아도 됨
	//     -> 비교 : mv.addObject("message", message) 하고, 
	//              redirect 하면 message 내용이 url 에 붙어 전달됨
	//   - addAttribute("message",message)
	//     -> url 에 붙어전달됨
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
		// 존재하는 session 확인 후 삭제
		HttpSession session = request.getSession(false);
		if (session!=null) {
			session.invalidate();
		}
		//mv.addObject("message", "~~ 로그아웃 !!! ~~~");
		rttr.addFlashAttribute("message", "~~ 로그아웃 !!! ~~~");
		mv.setViewName("redirect:home");
		return mv;
	} //logout
	// => viewName 을 지정하지 않으면 요청명으로 찾음 ( /WEB-INF/views/logout.jsp 을 찾음 )

	@RequestMapping(value = "/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		HttpSession session = request.getSession(false);
		if (session!=null && session.getAttribute("loginID") !=null) {
			vo.setId((String)session.getAttribute("loginID"));

			if  (request.getParameter("id")!=null) vo.setId(request.getParameter("id"));

			vo=service.selectOne(vo);
			if (vo!=null) {
				// updateForm 요청인지 확인 
				if ("U".equals(request.getParameter("jcode"))) { 
					mv.setViewName("member/updateForm");
					// ** PasswordEncoder 사용 때문에 
					//    session에 보관해 놓은 raw_password 를 수정할수 있도록 vo에 set 해줌.
					vo.setPassword((String)session.getAttribute("loginPW"));
				}else {
					mv.setViewName("member/memberDetail");
					vo.setPassword("*****"); // ~Detail.jsp 에서 표시하지 않아도 됨.
				}
				mv.addObject("Apple", vo);
			}else {
				mv.addObject("message","~~ 정보를 찾을 수 없습니다, 로그인 후 이용하세요 ~~");
				mv.setViewName("member/loginForm");
			}
		}else {
			// 로그인 정보 없음
			mv.addObject("message","~~ 로그인 정보 없습니다, 로그인 후 이용하세요 ~~");
			mv.setViewName("member/loginForm");
		}
		return mv;
	} //mdetail

	// ** Joinf
	@RequestMapping(value = "/joinf")
	public ModelAndView joinf(ModelAndView mv) {
		mv.setViewName("member/joinForm");
		return mv;
	} //joinf

	// ** Join
	@RequestMapping(value = "/join")
	public ModelAndView join(HttpServletRequest request, ModelAndView mv, MemberVO vo) throws IOException  {
		
		// ** Uploadfile (Image) 처리
		// => MultipartFile 타입의 uploadfilef 의 정보에서 
		//    upload된 image 와 화일명을 get 처리,
		// => upload된 image 를 서버의 정해진 폴더 (물리적위치)에 저장 하고, -> file1
		// => 이 위치에 대한 정보를 table에 저장 (vo의 UploadFile 에 set) -> file2
		// ** image 화일명 중복시 : 나중 이미지로 update 됨.  
		
		// ** Image 물리적위치 에 저장
		// 1) 현재 웹어플리케이션의 실행 위치 확인 : 
		// => eslipse 개발환경 (배포전)
		//    D:\MTest\MyWork\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Spring03\
		// => 톰캣서버에 배포 후 : 서버내에서의 위치가 됨
		//    D:\MTest\IDESet\apache-tomcat-9.0.41\webapps\Spring03\
		String realPath = request.getRealPath("/"); // deprecated Method
		System.out.println("** realPath => "+realPath);
				
		// 2) 위 의 위치를 이용해서 실제 저장위치 확인 
		// => 개발중인지, 배포했는지 에 따라 결정
		if (realPath.contains(".eclipse."))
			 realPath = "D:/MTest/MyWork/Spring03/src/main/webapp/resources/uploadImage/";
		else realPath += "resources\\uploadImage\\"; 
		
		// ** 폴더 만들기 (File 클래스활용)
		// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는경우)  만들어 준다
		File f1 = new File(realPath);
		if (!f1.exists()) f1.mkdir();
		// realPath 디렉터리가 존재하는지 검사 (uploadImage 폴더 존재 확인)
		// => 존재하지 않으면 디렉토리 생성
		
		// 기본 Image 지정
		String file1, file2 = "resources/uploadImage/basicman1.jpg";
			
		// ** MultipartFile
		// => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이의 처리를 위한 메서드를 제공한다.
		//    -> String getOriginalFilename(), 
		//    -> void transferTo(File destFile),
		//    -> boolean isEmpty()
		
		// => Ajax Test에서 uploadfilef 를 사용하지 않은 경우 ( vo.getUploadfilef() = null 로 처리되어 Type 비교하지 않음 ) 와 
		//    uploadfilef:'' 지정한 경우 비교 ( 이 경우엔 Server Error : typeMismatch ) 
		System.out.println("** Ajax Test vo => "+vo);
		
		MultipartFile uploadfilef = vo.getUploadfilef();
		if ( uploadfilef != null && !uploadfilef.isEmpty() ) {
			// Image 를 선택했음
			file1 = realPath + uploadfilef.getOriginalFilename(); //  전송된 File명 추출 & 연결
			uploadfilef.transferTo(new File(file1)); // real 위치에 전송된 File 붙여넣기
			file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename(); // Table 저장 경로
		}
		
		vo.setUploadfile(file2); // Table 저장 경로 set
		
		// ** Password 암호화
		// => BCryptPasswordEncoder 적용
		//    encode(rawData) -> digest 생성 & vo 에 set  
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		
		if (service.insert(vo) > 0) {
			// Join 성공 -> 로그인 유도
			mv.addObject("message", "~~ 회원가입 완료, 로그인 하세요 ~~");
			mv.setViewName("member/loginForm");
		}else {
			// Join 실패 -> 재가입 유도
			mv.addObject("message", "~~ 회원가입 오류, 다시 하세요 ~~");
			mv.setViewName("member/joinForm");
		}
		return mv;
	} //join

	// ** Update
	@RequestMapping(value = "/mupdate")
	public ModelAndView mupdate(HttpServletRequest request, ModelAndView mv, 
						MemberVO vo, RedirectAttributes rttr) throws IOException {
		
		// *** Image Upload 추가
		String realPath = request.getRealPath("/"); // deprecated Method
		// => 위 의 위치를 이용해서 실제 저장위치 확인  : 개발중인지, 배포했는지
		if (realPath.contains(".eclipse."))
			 realPath = "D:/MTest/MyWork/Spring03/src/main/webapp/resources/uploadImage/";
		else realPath += "resources\\uploadImage\\"; 
		
		// ** 폴더 만들기 (File 클래스활용)
		// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는경우)  만들어 준다
		// => update 의 경우에도 폴더가 없을 수 있음 
		File f1 = new File(realPath);
		if (!f1.exists()) f1.mkdir();
		
		// 기본 Image 지정
		String file1, file2 = "resources/uploadImage/basicman1.jpg";
			
		MultipartFile uploadfilef = vo.getUploadfilef();
		if ( uploadfilef != null && !uploadfilef.isEmpty() ) {
			// Image 를 선택했음
			file1 = realPath + uploadfilef.getOriginalFilename(); //  전송된 File명 추출 & 연결
			uploadfilef.transferTo(new File(file1)); // real 위치에 전송된 File 붙여넣기
			file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename(); // Table 저장 경로
		} else  {
			// updateForm 에서 Image 를 선택하지 않은경우 에는 이전과 동일하게 처리
			// => updateForm 에 hidden 으로 보관한 이전 화일명을 사용
			file2 = vo.getUploadfile();
		}
		vo.setUploadfile(file2); // Table 저장 경로 set
		
		// ** Password 암호화
		// => BCryptPasswordEncoder 적용
		//    encode(rawData) -> digest 생성 & vo 에 set  
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));

		if (service.update(vo) > 0) {
			// Update 성공 -> mList
			rttr.addFlashAttribute("message", "~~ 정보 수정 성공 ~~");
			mv.setViewName("redirect:mlist");
		}else {
			// Update 실패 -> 재수정 할 수 있도록 유도
			rttr.addFlashAttribute("message", "~~ 정보수정 오류, 다시 하세요 ~~");
			mv.setViewName("redirect:mdetail?id="+vo.getId()+"&jcode=U");
		}
		return mv;
	} //mupdate

	// ** Member Delete : 회원탈퇴
	@RequestMapping(value = "/mdelete")
	public ModelAndView mdelete(HttpServletRequest request, ModelAndView mv, MemberVO vo,
							RedirectAttributes rttr) {
		
		// => 삭제 대상 -> vo 에 set
		HttpSession session = request.getSession(false);
		String loginID = (String)session.getAttribute("loginID");

		if (session!=null && loginID!=null) {
			// ** 삭제 가능 => message, home.jsp
			// => 삭제대상 확인, session 삭제여부 확인 
			vo.setId(loginID);
			
			// *** Image Upload 추가
			// => 해당 이미지 정보읽어온후, 삭제 -> 회원삭제
			vo=service.selectOne(vo);
			if ( vo != null) {
				// => 화일명 추출
				String fileName = vo.getUploadfile().substring(vo.getUploadfile().lastIndexOf("/")+1);  
				
				String realPath = request.getRealPath("/"); // deprecated Method
				// => 위 의 위치를 이용해서 실제 저장위치 확인  : 개발중인지, 배포했는지
				if (realPath.contains(".eclipse."))
					 realPath = "D:/MTest/MyWork/Spring03/src/main/webapp/resources/uploadImage/"+fileName;
				else realPath += "resources\\uploadImage\\"+fileName; 
				// 삭제  
				// => File 인스턴스 생성후 존재 확인 후 삭제
				File delF = new File(realPath);
				if (delF.exists()) delF.delete();
			}
			
			if (service.delete(vo) > 0) {
				// 삭제성공
				session.invalidate();  // session 삭제 
				rttr.addFlashAttribute("message", "~~ 회원탈퇴 성공 !!  1개월후 재가입 가능 합니다 ~~");
				mv.setViewName("redirect:home");
				// mv.setViewName("redirect:"); 허용되지않음
			}else { // 삭제실패
				rttr.addFlashAttribute("message", "~~ 회원탈퇴 오류 !!  다시 하세요 ~~");
				mv.setViewName("redirect:mdetail?id="+vo.getId());
			}
		}else {
			// 탈퇴 불가능 => message, loginForm.jsp 
			mv.addObject("message", "~~ 탈퇴 불가능 !!  로그인후 하세요 ~~");
			mv.setViewName("member/loginForm");
		}
		return mv;
	} //mdelete


} //class
