package service;

import java.util.List;

import criTest.SearchCriteria;
import vo.MemberVO;
import vo.PageVO;

public interface MemberService {
	
	// ** Check_MebmerList
	List<MemberVO> checkList(MemberVO vo);
	
	// PageList2. => SearchPageList
	public int searchRowsCount(SearchCriteria cri);
	public List<MemberVO> searchList(SearchCriteria cri);
	
	PageVO<MemberVO> pageList(PageVO<MemberVO> pvo); //pageList()
	List<MemberVO> selectList(); //selectList()
	MemberVO selectOne(MemberVO vo); //selectOne
	int insert(MemberVO vo); //insert
	int update(MemberVO vo); //update
	int delete(MemberVO vo); //delete

} //MemberService