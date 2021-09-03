<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis Ajax_MemberList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<script src="resources/myLib/axTest02.js"></script>
</head>
<body>
<h2>** Spring Mybatis Ajax_MemberList **</h2>
<br>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<table border="1" width=800>
<tr height="30" bgcolor="Azure">
	<th>ID</th><th>Password</th><th>Name</th><th>Level</th><th>BirthDay</th>
	<th>Point</th><th>Weight</th><th>추천인ID</th><th>Image</th><th>회원탈퇴</th>
</tr>
<!-- ** 반복문에 이벤트 적용하기 
=> Ajax 또는 아닌경우 모두 적용
=> 과제 : id 를 클릭하면 이 id 가 쓴 board 글 목록이 resultArea2 에 출력하기 
1) JS : Tag의 onclick 이벤트속성(리스너) 이용
  => id 전달: function 의 매개변수를 이용 -> aidBList('banana') 
  => a Tag 를 이용하여 이벤트적용
	   -> href="" 의 값에 따라 scroll 위치 변경 가능.
		  <a href="" onclick="aidBList('banana')" >....
	   -> href="#"      .... scroll 위치 변경
		 	"#" 에 #id 를 주면 id의 위치로 포커스를 맞추어 이동,
		 	 #만 주면 포커스가 top 으로 올라감
	   -> href="javascript:;" ...... scroll 위치 변경 없음

2) JQuery : class, id, this 이용
=> 모든 row 들에게 이벤트를 적용하면서 (class 사용)
   해당되는(선택된) row 의 값을 인식 할 수 있어야 함 (id 활용) 
-->

<c:forEach var="list" items="${Banana}" varStatus="vs">
<tr height="30" align="center">
	<td> <!-- test 1) JS function -->
		<a href="#resultArea2" onclick="aidBList('${list.id}')">${list.id}</a>
	     <%-- "aidBList(${list.id})" => aidBList(banana) : XXXXX --%>
		 
		 <!-- 2) JQuery : class, id, this 이용 
		 <span id="${list.id}" class="ccc textLink">${list.id}</span>-->
	</td> 
	<td>${list.password}</td><td>${list.name}</td><td>${list.lev}</td>
	<td>${list.birthd}</td><td>${list.point}</td><td>${list.weight}</td><td>${list.rid}</td>
	
	<!-- ** Image DownLoad 추가 
	1) class="dnload" 를 이용하여  jQuery Ajax 로 처리 
		=> 안됨 (java 클래스인  서버의 response가  웹브러우져로 전달되지 못하고 resultArea에 담겨질 뿐 )  
		<img src="${list.uploadfile}" width="50" height="60" class="dnload textLink">
	2) aTag 로  직접 요청함 
		=> java 클래스인  서버의 response가  웹브러우져로 전달되어 download 잘됨. 
	-->
	<td><a href="dnload?dnfile=${list.uploadfile}">
	<img src="${list.uploadfile}" width=70 height=70></a></td>
	
	<!-- ** Delete 기능 실급 
	=> 1) JS의 function 으로 처리 : id는 매개변수로 처리
	 	  id 속성의 값으로 index 사용하기(List에 적당한 key가 없을 때 유용) 
	<td id='${vs.index}'><span onclick="amDelete('${list.id}', '${vs.index}')" 
				class="textLink">Delete</span>
				
	=> 2) JQ 방식 : Class, id 사용
	-->
	<td><span id="${list.id}" class="ddd textLine">Delete</span>
	</td>
	
</tr>
</c:forEach>
</table>
<hr>
</body>
</html>