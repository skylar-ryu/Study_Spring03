package vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

// ** 공통모듈 구현 1.
// => VO (Value Object) : member 테이블의 구조
// => DTO (Data Transfer Object) : 자료를 주고 받는 통로 역할
// => setter, getter, toString

@Data
//=> 정의된 모든 필드에 대한 
//   Getter, Setter, ToString 과 같은 모든 요소를 한번에 만들어주는 어노테이션.
public class MemberVO {
	private String id;
	private String password;
	private String name;
	private String lev;
	private String birthd;
	private int point;
	private double weight;
	private String rid;
	
	private String uploadfile; // Table 에 저장된 경로및 화일명 처리를 위한 필드  
	private MultipartFile uploadfilef;
	// form 의 Image 정보를 전달받기위한 필드 
	// MultipartFile (Interface) -> CommonsMultipartFile
	
	String[] check;
	// ** 배열타입 (CheckBox 처리) 
	// => 배열타입 검색조건 처리
		
	// 전송 자료가 {'A','B','C'} 가정하면
	// => Sql 
	// where lev='A' or lev='B' or lev='C' ..
	// where lev in ('A','B','C')
	
	private boolean enabled ;
	// Spring Security 에서 사용	
	private List<AuthVO> authList;
	// Spring Security 인증 기능 위해 추가  -------- 
	
} //class
