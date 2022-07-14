<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<meta charset="UTF-8">
<title>첫 번째 단계</title>
</head>
<body>

<main>
	<form action="dietStep1.do" method="post">
		<label for="man"><input id="man" type="radio" name="sex" value="남">남</label>
		
		<label for="woman"><input id="woman" type="radio" name="sex" value="여">여</label>
		
		<label for="height">신장</label>
		<input id="height" type="number" name="height">
		
		<label for="weight">체중</label>
		<input id="weight" type="number" name="weight">
		
		<label for="age">나이</label>
		<input id="age" type="number" name="age">
		
		<input type="hidden" name="type" value="bulkup">
		
		<input type="submit" value="1단계">
	</form>
</main>
</body>
</html>