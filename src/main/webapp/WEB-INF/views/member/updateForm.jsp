<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Oracle MemberUpdate Form **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Spring Oracle MemberUpdate Form **</h2>
<table><form action="mupdate" method="post" enctype="multipart/form-data">
	<tr height="40"><td bgcolor="PaleGreen">I  D</td>
		<td><input type="text" name="id" size="20" value="${Apple.id}" readonly></td>
		<!-- ** input Tag 입력 막기 
				=> disabled :  서버로 전송 안됨 
				=> readonly :  서버로 전송 됨   -->
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">Password</td>
		<td><input type="password" name="password" size="20" value="${Apple.password}"></td>
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">Name</td>
		<td><input type="text" name="name" size="20" value="${Apple.name}"></td>
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">Level</td>
		<td><select name="lev"    >
			<c:choose>
			<c:when test="${Apple.lev=='A'}">
				<option value="A" selected>관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
			</c:when>
			<c:when test="${Apple.lev=='B'}">
				<option value="A">관리자</option>
				<option value="B" selected>나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
			</c:when>
			<c:when test="${Apple.lev=='C'}">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C" selected>잎새</option>
				<option value="D">새싹</option>
			</c:when>
			<c:when test="${Apple.lev=='D'}">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D" selected>새싹</option>
			</c:when>
			</c:choose>
		</select></td>
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">Birthday</td>
		<td><input type="date" name="birthd" value="${Apple.birthd}"></td>
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">Point</td>
		<td><input type="text" name="point" size="20" value="${Apple.point}"></td>
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">Weight</td>
		<td><input type="text" name="weight" size="20" value="${Apple.weight}"></td>
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">추천인ID</td>
		<td><input type="text" name="rid" size="20" value="${Apple.rid}"></td>
	</tr>
	<tr height="40"><td bgcolor="PaleGreen">Image</td>
		<td>
		<img src="${Apple.uploadfile}" class="select_img" width="100" height="100"><br>
		<input type="hidden" name="uploadfile" value="${Apple.uploadfile }"><br>
		<input type="file" name="uploadfilef" id="uploadfilef">
		<script>
		$('#uploadfilef').change(function(){
			if(this.files && this.files[0]) {
				var reader = new FileReader;
		 			reader.onload = function(e) {
	 				$(".select_img").attr("src", e.target.result)
	 					.width(100).height(100); 
	 				} // onload_function
	 				reader.readAsDataURL(this.files[0]);
	 		} // if
		}); // change	
		</script>
		</td>
	</tr>
	<tr height="40"><td></td>
		<td><input type="submit" value="수정">&nbsp;&nbsp;
			<input type="reset" value="취소"></td>
	</tr>
</form></table>
<c:if test="${message != null}">
	<br>${message}<br><br>	
</c:if>
<hr>
<a href="home">HOME</a>
</body>
</html>