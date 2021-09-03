package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.SearchCriteria;
import util.MemberDAO;
import vo.MemberVO;
import vo.PageVO;

//** interface 자동완성 
//=> Alt + Shift + T  
//=> 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO dao ;
	
	@Override
	public List<MemberVO> checkList(MemberVO vo) {
		return dao.checkList(vo);
	}
	// PageList2. => SearchPageList--------------- 
	@Override
	public int searchRowsCount(SearchCriteria cri) {
		return dao.searchRowsCount(cri);
	}
	@Override
	public List<MemberVO> searchList(SearchCriteria cri) {
		return dao.searchList(cri);
	}
	//-----------------------------------------------------
	
	@Override
	public PageVO<MemberVO> pageList(PageVO<MemberVO> pvo) {
		return dao.pageList(pvo);
	}
	
	@Override
	public List<MemberVO> selectList() {
		return dao.selectList();
	} //selectList()
	@Override
	public MemberVO selectOne(MemberVO vo) {
		return dao.selectOne(vo);
	} //selectOne
	
	@Override
	public int insert(MemberVO vo) {
		return dao.insert(vo);
	} //insert
	@Override
	public int update(MemberVO vo) {
		return dao.update(vo);
	} //update
	@Override
	public int delete(MemberVO vo) {
		return dao.delete(vo);
	} //delete
} //class