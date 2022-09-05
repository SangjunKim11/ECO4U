$(function(){
	//===== MYPage 프로필 사진 처리 ======//
	//확인 버튼 처리
	$('#photo_btn').click(function(){
		$('#photo_choice').show(); //프로필 수정 폼 노출
		$(this).hide(); //수정 버튼 감추기
	});
	
	//처음 화면에 보여지는 이미지 읽기
	let photo_path = $('.my-photo').attr('src');
	let my_photo; //업로드하고자 선택한 이미지 저장
	$('#upload').change(function(){
		my_photo = this.files[0];
		if(!my_photo){ //파일 정보가 없는 경우
			$('.my-photo').attr('src', photo_path);
			return;
		}
		//파일 용량 체크
		if(my_photo.size > 1024*1024){
			alert(Math.round(my_photo.size/1024) + 
							'kbytes(1024kbytes까지만 업로드 가능)');
			//원래 이미지로 교체
			$('.my-photo').attr('src', photo_path);
			$(this).val(''); //파일명 지우기
			return;
		}
		
		//이미지 미리보기 처리
		let reader = new FileReader();
		//선택한 이미지 읽기
		reader.readAsDataURL(my_photo);
		
		reader.onload=function(){
			//읽어들인 이미지 표시
			$('.my-photo').attr('src', reader.result);
		};
	});//end of change
	
	
	//취소 버튼 처리
	$('#photo_reset').click(function(){
		$('.my-photo').attr('src',photo_path);
		$('#upload').val('');
	});
	

	
});