package com.ncs.green;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//** 로그인처리를 담당하는 인터셉터 구현
//** HandlerInterceptorAdapter
//=> HandlerInterceptor 인터페이스를 사용하기 편리하게 구현해 놓은 추상 클래스
//=> HandlerInterceptor 인터페이스 -> HandlerInterceptorAdapter (추상 클래스)

//오버라이딩 단축키 : shift + alt + s + v
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	// ** boolean preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	// => return 값 : 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임
	// => false: 더이상 컨트롤러 요청으로 가지 않도록 함
	// => true : 컨트롤러의 uri로 가게 됨
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ** Login 여부에 따른 인증 구현 
		// 1) session 객체 가져오기
		HttpSession session = request.getSession(false);
		
		// 2) 로그인 확인하기
		//    결과에 따라 true 또는 false 를 return 
		if (session!=null && session.getAttribute("loginID")!=null) {
			// Login 되어있음 -> Controller 진행
			return true;
		}else {
			// Login 되어있지 않음
			// => Login 유도 (loginForm 요청) 후 return false
			// 2.1) redirect 
			//response.sendRedirect("/green/loginf");
			// 2.2) forward
			request.setAttribute("message", "~~ 로그인 후 이용 하세요 !!! ~~");
			String url="/WEB-INF/views/member/loginForm.jsp";
			request.getRequestDispatcher(url).forward(request, response);
			
			return false;
		}
	} //preHandle
	
	// ** postHandle()
	// => 컨트롤러가 수행되고 view페이지 랜더링 직전에 수행되는 메서드
	// => Ajax 요청시에도 적용됨
	// => 컨트롤러 실행중 예외 발생시 실행되지 않음
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//super.postHandle(request, response, handler, modelAndView);
		modelAndView.addObject("message", "~~~ postHandle() Test ~~~");  // jsp 에 출력됨
	} //postHandle
	
	// ** afterCompletion()
	// => 컨트롤러가 수행되고 view페이지 랜더링 후 실행됨
	// => Ajax 요청시에도 적용됨
	// => 컨트롤러 실행중 예외 발생시에도 실행됨
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//super.afterCompletion(request, response, handler, ex);
		request.setAttribute("message", "~~~ afterCompletion() Test ~~~"); // jsp 에 출력되지않음
		System.out.println("** afterCompletion() 통과 **");
	} //afterCompletion
	
	// ** afterConcurrentHandlingStarted (Concurrent: 공존하는, 동시에 발생하는) 
	// => Servlet3.0 부터 비동기 요청 가능하고,
	//    비동기 요청시에는 postHandle(), afterCompletion() 은 실행되지않고 이 메서드가 실행됨.
	// => 서블릿의 비동기 요청
	//    기존 서블릿은 클라이언트의 요청을 처리하는 쓰레드에서 응답도 처리하는 방식으로 응답완료시까지 쓰레드를 점유하게되어,
	//    클라이언트와의 연결을 유지하는 프로그래밍 방식의 경우,
	//    클라이언트가 증가하면 쓰레드가 부족해지는 상황이 발생 가능함.    
	//    Servlet3.0 부터 추가된 비동기 방식은 응답을 별도의 쓰레드로 처리할 수 있도록 하여
	//    원하는 방식으로 선택할 수 있도록 함. 
	//    ( https://javacan.tistory.com/entry/Servlet-3-Async )
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//super.afterConcurrentHandlingStarted(request, response, handler);
		request.setAttribute("message", "~~~ afterConcurrentHandlingStarted Test ~~~");
		System.out.println("** afterConcurrentHandlingStarted() 통과 **");
		
	} //afterConcurrentHandlingStarted

} //class
