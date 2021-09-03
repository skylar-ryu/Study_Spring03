package vo;

import java.util.List;

import lombok.Data;

// ** DTO : Data Transfer Object 
// ** member, board 모두 사용 가능하도록 Generic 설정 
@Data
public class PageVO <T> {
	
	private List<T> list ; // 출력할 목록 	// generic하게 만들기 위해서<T>사용(매개를 Board, Page 둘다 사용하기 위해) 
	private int totalRowCount; // 전체 row(열) 갯수 -> 전체 필요한 page수를 계산하기 위해서
	private int rowsPerPage = 3 ; // 1page당 출력할(보여줄) row의 갯수 / 현재는 3이기때문에 한페이지당 3개의 게시물을 보여준다 
	private int pageNoCount = 2; // 1 page 당 표시할 pageNo의 갯수(게시판 밑에 나올 페이지번호의 갯수) 
	private int currPage; // 요청(출력)할 PageNo: 현재 위치한, 보여주는 페이지  
	private int sno; //start Row number
	private int eno; //end Row number
	

}// class
