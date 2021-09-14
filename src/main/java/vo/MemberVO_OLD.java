package vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

// ** VO (Value Object)
// => setter, getter, toString

@Data
//=> 정의된 모든 필드에 대한 
//   Getter, Setter, ToString 과 같은 모든 요소를 한번에 만들어주는 어노테이션.
public class MemberVO_OLD {
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
	
	String[] check;
	// ** 배열타입 (CheckBox 처리) 
	// => 배열타입 검색조건 처리
		
	// 전송 자료가 {'A','B','C'} 가정하면
	// => Sql 
	// where lev='A' or lev='B' or lev='C' ..
	// where lev in ('A','B','C')
	
} //class
