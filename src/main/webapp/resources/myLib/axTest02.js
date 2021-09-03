/*
** AjaxTest02
=> 반복문에 이벤트 적용하기 
=> amlist -> id별로 board조회, delete 기능, ImageDownload
=> ablist -> 상세글 보기 기능
*/
$(function(){
// ** Ajax MemberList
  $('#amlist').click(function(){
	$.ajax({
		type:'Get',
		url:'amlist',
		success:function(resultPage){
			$('#resultArea1').html(resultPage);
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}
	}); //ajax
  }); //amlist_click	
	
// ** Ajax BoardList	
  $('#ablist').click(function(){
	$.ajax({
		type:'Get',
		url:'ablist',
		success:function(resultPage){
			$('#resultArea1').html(resultPage);
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}
	}); //ajax
  }); //ablist_click	

// ** 반복문에 이벤트 적용하기
// => 2) JQuery : class, id, this 이용
  $('.ccc').click(function(){
	/* id값 가져오기 
	1) 컨텐츠 인식
	var id = $(this).html(); // 또는 $(this).text();
	2) 미리 만들어둔 id 값 이용하기 */
	var id = $(this).attr('id'); 
	// 현재 이벤트가 발생된 Tag 의 id 속성의 값을 제공
	
	$.ajax({
		type:'Get',
		url: 'aidblist',
		data:{
			id:id
		},
		success:function(resultPage){
			$('#resultArea2').html(resultPage);
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}	
	}); //ajax 		
  }); //ccc_click
	
}); //ready

// ** 반복문에 이벤트 적용하기
// => id별로 board조회
// test 1) JS function
function aidBList(id) {
	$.ajax({
		type: 'Get',
		url: 'aidblist',
		data:{
			id:id
		},
		success:function(resultPage){
			$('#resultArea2').html(resultPage);
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}	
	}); //ajax
} //aidBList


// => Member Delete 기능
//    JSONView 으로 처리  
// test 2) JQ Function
// => class, id 활용
$('.ddd').click(function(){
	var id = $(this).attr('id');
	$.ajax({
		type:'Get',
		url: 'jsdelete',
		data:{
			id:id
		},
		success:function(resultData){
			if(resultData.success=='T'){
				alert('~~ 회원 탈퇴 성공 !!! 1달 뒤에 재가입가능합니다 ~~');
				// 삭제됨 으로 변경하고, 더이상 클릭하지 못하도록 함
				// => 그러나, this는 적용 안됨
				// => ajax 가 실행되면서 this는 이미 변경됨
				// => this 대신 사용 가능한 속성이 필요함 (id를 부여해서 사용)
				
				// => <span> 에 id 를 적용한 경우 
				// => JQ 메서드가 적용안되므로 <td>의 값을 변경함
				// $('#'+index).html("Deleted").removeClass('textLink');
				// $('#'+index).off(); // 이벤트(textLink) 제거
				// => Deleted는 적용 되지만, removeClass , off적용안됨.
				$('#'+index).html("Deleted");
				$('#'+index).css({
					color:'gray',
					fontWeight:'bold'					
				});
				$('#'+id).off();			
			}else{
				alert('~~ 회원 탈퇴 실패 !!! 잠시후 다시 하세요 ~~');
			}
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}		
	})//ajax
	
});//ddd.click



// test 1) JS Function
function amDelete(id,index) {
	$.ajax({
		type:'Get',
		url: 'jsdelete',
		data:{
			id:id
		},
		success:function(resultData){
			if(resultData.success=='T'){
				alert('~~ 회원 탈퇴 성공 !!! 1달 뒤에 재가입가능합니다 ~~');
				// 삭제됨 으로 변경하고, 더이상 클릭하지 못하도록 함
				// => 그러나, this는 적용 안됨
				// => ajax 가 실행되면서 this는 이미 변경됨
				// => this 대신 사용 가능한 속성이 필요함 (id를 부여해서 사용)
				
				// => <span> 에 id 를 적용한 경우 
				// => JQ 메서드가 적용안되므로 <td>의 값을 변경함
				// $('#'+index).html("Deleted").removeClass('textLink');
				// $('#'+index).off(); // 이벤트(textLink) 제거
				// => Deleted는 적용 되지만, removeClass , off적용안됨.
				$('#'+index).html("Deleted");
				$('#'+index).css({
					color:'gray',
					fontWeight:'bold'					
				});
								
			}else{
				alert('~~ 회원 탈퇴 실패 !!! 잠시후 다시 하세요 ~~');
			}
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}		
	})//ajax
}//amDelete


