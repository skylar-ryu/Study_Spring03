<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis Cri_PageList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script>
//** Button 으로 현재 입력 & 선택한 keyword 와 searchType 을 queryString 으로 
//   요청을 보내기 때문에 makeQuery() 메서드를 사용해야 함.
//$(document).ready(function() { .... })
$(function() {	
	// SearchType 이 '---' 면 keyword 클리어
	$('#searchType').change(function() {
		if ($(this).val()=='n') $('#keyword').val('');
	}); //change
	
	$('#searchBtn').on("click", function() {
		self.location="bcplist"
			+"${pageMaker.makeQuery(1)}"
			+"&searchType="
			+$('#searchType').val()
			+'&keyword='
			+$('#keyword').val()
		// bcplist?curPage=1&rowsPerPage=3&searchType=t&keyword=Java
				
		/* 	비교 Test :
		self.location="bcplist"
				+"${pageMaker.searchQuery(1)}" */		
				
	}); //on_click
	
}) //ready

</script>
</head>
<body>
<h2>** Spring Mybatis Cri_PageList **</h2>
<br>
<c:if test="${message!=null}">
 => ${message}<br> 
</c:if>
<div id="searchBar">
	<select name="searchType" id="searchType">
		<option value="n" <c:out value="${pageMaker.cri.searchType==null ? 'selected':''}"/> >---</option>
		<option value="t" <c:out value="${pageMaker.cri.searchType=='t' ? 'selected':''}"/> >Title</option>
		<option value="c" <c:out value="${pageMaker.cri.searchType=='c' ? 'selected':''}"/> >Content</option>
		<option value="w" <c:out value="${pageMaker.cri.searchType=='w' ? 'selected':''}"/> >Writer(ID)</option>
		<option value="tc" <c:out value="${pageMaker.cri.searchType=='tc' ? 'selected':''}"/> >Title or Content</option>
		<option value="cw" <c:out value="${pageMaker.cri.searchType=='cw' ? 'selected':''}"/> >Content or Writer</option>
		<option value="tcw" <c:out value="${pageMaker.cri.searchType=='tcw' ? 'selected':''}"/> >Title or Content or Writer</option>
	</select>
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}">
	<button id="searchBtn">Search</button>
</div>
<br><hr>
<table>
<tr height="40" bgcolor="PaleTurquoise">
	<th>Seq</th><th>Title</th><th>ID</th><th>RegDate</th><th>조회수</th>
</tr>
<c:forEach var="list" items="${Banana}"><tr height="40">
	<td>${list.seq}</td>
	<td>
		<!-- 답글 등록후 indent 에 따른 들여쓰기 
			=> 답글인 경우에만 적용  -->
		<c:if test="${list.indent>0}">
			<c:forEach begin="1" end="${list.indent}">
				<span>&nbsp;&nbsp;</span>
			</c:forEach>
			<span style="color:Purple">re..</span>
		</c:if>
	
		<!-- 로그인 했을때만 글내용을 볼 수 있도록 -->
		<c:if test="${loginID!=null}">
			<a href="bdetail?seq=${list.seq}&id=${list.id}">${list.title}</a>
		</c:if>
		<c:if test="${loginID==null}">
			${list.title}
		</c:if>
	</td>
	<td>${list.id}</td><td>${list.regdate}</td><td align="center">${list.cnt}</td>
</tr></c:forEach>
</table>
<br><hr>
<div align="center">
	<!-- Paging 2 : Criteria 적용 
		=> ver01 : pageMaker.makeQuery(?)
		=> ver02 : pageMaker.searchQuery(?)
	
		 1)  First << ,  Prev <  처리 -->
	<c:if test="${pageMaker.prev && pageMaker.spageNo>1}">
		<a href="bcplist${pageMaker.searchQuery(1)}">FF</a>&nbsp;
		<a href="bcplist${pageMaker.searchQuery(pageMaker.spageNo-1)}">Prev</a>
	</c:if>
	
	<!-- 2) sPageNo ~ ePageNo 까지, displayPageNo 만큼 표시 -->
	<c:forEach var="i" begin="${pageMaker.spageNo}" end="${pageMaker.epageNo}">
		<c:if test="${i==pageMaker.cri.currPage}">
			<font size="5" color="Orange">${i}</font>&nbsp;
		</c:if>
		<c:if test="${i!=pageMaker.cri.currPage}">
			<a href="bcplist${pageMaker.searchQuery(i)}">${i}</a>&nbsp;
		</c:if>
	</c:forEach>
	&nbsp;
	<!-- 3) Next >  ,  Last >>  처리 -->
	<c:if test="${pageMaker.next && pageMaker.epageNo>0}">
		<a href="bcplist${pageMaker.searchQuery(pageMaker.epageNo+1)}">Next</a>&nbsp;
		<a href="bcplist${pageMaker.searchQuery(pageMaker.lastPageNo)}">LL</a>&nbsp;&nbsp;
	</c:if>
</div>

<br><hr>
<c:if test="${loginID!=null}"> 	
	<a href="binsertf">새글등록</a>&nbsp;&nbsp;
	<a href="logout">Logout</a>&nbsp;&nbsp;
</c:if>  
<c:if test="${loginID==null}"> 
	<a href="loginf">로그인</a>&nbsp;&nbsp;
	<a href="joinf">회원가입</a>&nbsp;&nbsp;
</c:if>
<a href="home">HOME</a>
</body>
</html>