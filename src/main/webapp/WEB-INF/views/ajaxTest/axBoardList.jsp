<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis Ajax_BoardList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/axTest03.js"></script>
</head>
<body>
<h2>** Spring Mybatis Ajax_BoardList **</h2>
<br>
<c:if test="${message!=null}">
 => ${message}<br> 
</c:if>
<hr>
<!-- ** 반복문에 이벤트 적용 
	=> 매개변수처리에 varStatus 활용, ajax, json Test  
	=> Login 여부에 무관하게 처리함.
// Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기  -> aTag, JS, jsBDetail1(  ) 
// Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(span result)에 글 내용 출력하기 -> aTag, JS, jsBDetail2( , ) 
// Test 3. seq 에 마우스 오버시에 별도의 DIV에 글내용 표시 되도록 하기 
//			-> jQuery : id, class, this
// 			-> seq 의 <td> 에 마우스 오버 이벤트
//			-> content 를 표시할 div (table 바깥쪽에) : 표시/사라짐  
//			-> 마우스 포인터의 위치 를 이용해서   div 의 표시위치 지정
-->	
<table>
<tr height="40" bgcolor="PaleTurquoise">
	<th>Seq</th><th>Title</th><th>ID</th><th>RegDate</th><th>조회수</th>
</tr>
<c:forEach var="list" items="${Banana}" varStatus="vs">
<tr height="40">
	<!-- Test 3. 
		=> 3.1) css 활용 : sss1
		=> 3.2) JQ 메서드 show() , hide() 활용 : sss2 
	-->
	<td id="${list.seq}" class="sss1 textLink">${list.seq}</td>
	<td>
		<!-- 답글 등록후 indent 에 따른 들여쓰기 
			=> 답글인 경우에만 적용  -->
		<c:if test="${list.indent>0}">
			<c:forEach begin="1" end="${list.indent}">
				<span>&nbsp;&nbsp;</span>
			</c:forEach>
			<span style="color:Purple">re..</span>
		</c:if>
	<!-- Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기  
			=> aTag, JS, jsBDetail1(  )  
		<a href="#resulrArea2" onclick="jsBDetail1(${list.seq})">${list.title}</a>-->
	<!-- Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(<tr>추가 : span)에 글 내용 출력하기
			=> aTag, JS, jsBDetail2( , ) ,  추가된<tr> 에 id(index 활용), class 
			=> scroll 이동하지 않도록함
				-> href="javascript:;" 
				-> 동일효과 : "javascript:void(0);"
			=> Toggle 방식으로 없을때 클릭하면 표시되고, 있을때 클릭하면 사라짐
			=> 만약 출력할 content의 내용이 없으면 아무것도 나타나지 않는다 (공백의 span 은 표시 되지않음.)	
			-->	
		<a href="javascript:;" onclick="jsBDetail2(${list.seq}, ${vs.index})">${list.title}</a>
		
	</td>
	<td>${list.id}</td><td>${list.regdate}</td><td align="center">${list.cnt}</td>
</tr>
<tr><td></td>
	<td colspan="4"><span id="content${vs.index}" class="content" style="background: Lavender"></span></td>
</tr></c:forEach>
</table>
<div id="content"></div>
<br><hr>
</body>
</html>