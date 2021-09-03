package service;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;
import vo.PageVO;

public interface BoardService {

	// ** Check_MebmerList
	List<BoardVO> checkList(BoardVO vo);

	// ** Ajax idBoardList
	public List<BoardVO> aidBList(BoardVO vo);
	
	// PageList2. => ver02 : SearchPageList
	public int searchRowsCount(SearchCriteria cri);
	public List<BoardVO> searchList(SearchCriteria cri);
	
	// PageList2. => ver01 : criPageList
	public int totalRowsCount();
	public List<BoardVO> criPList(Criteria cri);
	
	// PageList1.
	PageVO<BoardVO> pageList(PageVO<BoardVO> pvo);
	
	int replyInsert(BoardVO vo); // 답글등록

	List<BoardVO> selectList(); //selectList
	BoardVO selectOne(BoardVO vo); //selectList

	// ** 조회수 증가
	int countUp(BoardVO vo); //countUp

	int insert(BoardVO vo); //insert
	int update(BoardVO vo); //update
	int delete(BoardVO vo); //delete

}