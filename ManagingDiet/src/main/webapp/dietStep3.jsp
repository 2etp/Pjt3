<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>세 번째 단계</title>
</head>
<body>
	<p>당신의 일일 칼로리는 ${kcal}입니다. 돼지야</p>
	
	<form action="dietStep3.do" method="post">
		<input type="hidden" name="kcal" value="${kcal}">
		<input type="submit" value="3단계">
	</form>
</body>
</html>