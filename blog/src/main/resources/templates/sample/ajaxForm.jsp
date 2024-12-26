<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax Form</title>
</head>
<body>
	<h1>ajax Form</h1>
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	<button id="uploadBtn">Upload</button>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js">
	</script>
	
	<script>
		$(function()
		{
			$("#uploadBtn").on("click", function()
			{
				var formData = new FormData();
				var inputFile = $("input[name=uploadFile]");	// name이 uploadFile인 모든 input 태그 가져옴
				var files = inputFile[0].files;
				console.log(files);
				
				for (var i = 0; i < files.length;i++)
				{
					formData.append("uploadFile", files[i]);
				}
				
				$.ajax
				({
					url: "/sample/ajax",
					type: "post",
					processData: false,	// 기본값 true, 데이터를 컨텐츠 타입에 맞게 변환
					contentType: false,	// application/json 이 false면 multipart/form-data 타입으로 전송됨
					data: formData,
					success: function(response)
					{
						alert(response.msg);
					}
				})
			})
		})
	</script>
	
</body>
</html>