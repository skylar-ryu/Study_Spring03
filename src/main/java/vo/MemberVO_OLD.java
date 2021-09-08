package vo;

import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import lombok.Data;

//** VO (Value Object)
//=> setter, getter, toString

@Data
//=> 정의된 모든 필드에 대한 
//Getter, Setter, ToString 과 같은 모든 요소를 한번에 만들어주는 어노테이션.
public class MemberVO_OLD {
	private String id;
	private String password;
	private String name;
	private String lev;
	private String birthd;
	private int point;
	private double weight;
	private String rid;
	private String uploadfile; // Table에 저장된 경로 및 파일명 처리를 위한 필드
	private MultipartFile uploadfilef; // form 의 Image 정보를 전달받기 위한 필드
	
	String[] check;
	// ** 배열타입 (CheckBox 처리) 
	// => 배열타입 검색조건 처리

	// 전송 자료가 {'A','B','C'} 가정하면
	// => Sql 
	// where lev='A' or lev='B' or lev='C' ..
	// where lev in ('A','B','C')
	
	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getLev() {
//		return lev;
//	}
//	public void setLev(String lev) {
//		this.lev = lev;
//	}
//	public String getBirthd() {
//		return birthd;
//	}
//	public void setBirthd(String birthd) {
//		this.birthd = birthd;
//	}
//	public int getPoint() {
//		return point;
//	}
//	public void setPoint(int point) {
//		this.point = point;
//	}
//	public double getWeight() {
//		return weight;
//	}
//	public void setWeight(double weight) {
//		this.weight = weight;
//	}
//	
//	@Override
//	public String toString() {
//		return "MemberVO [id=" + id + ", password=" + password + ", name=" + name + ", lev=" + lev + ", birthd="
//				+ birthd + ", point=" + point + ", weight=" + weight + "]";
//	}	
	
}//class
