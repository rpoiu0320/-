<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>upload</h2>
	
	<form method="post" enctype="multipart/form-data" action="/sample/ex08">
		<input type="file" name="files" multiple="multiple">
		<input type="submit">
	</form>
	
</body>
</html>