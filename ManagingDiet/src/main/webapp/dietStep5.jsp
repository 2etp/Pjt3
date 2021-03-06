<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>식단 프로그램 구성</title>
<meta charset="UTF-8" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
 .pageInfo{
  	list-style : none;
  	display: inline-block;
    margin: 50px 0 0 100px;  	
 }
  
 .pageInfo li{
  	float: left;
    font-size: 20px;
    margin-left: 18px;
    padding: 7px;
    font-weight: 500;
 }
  
 a:link {color:black; text-decoration: none;}
 a:visited {color:black; text-decoration: none;}
 a:hover {color:black; text-decoration: underline;}
 
.active{
 	background-color: #cdd5ec;
}
</style>
</head>

<body>

	<h1>음식 추천 리스트</h1>
	
	<form action="insertFood.do" method="post" name="frm">
		<c:forEach items="${foodList}" var="food">
			<label>	
			<input type="checkbox" name="food" value="${food.foodName}" onClick="itemSum(this.form);">
				<div id="list-wrap">
					<img src="image/${food.imgPath}" width="300px" height="300px">
					<p>음식명 : ${food.foodName}</p>
					<p>칼로리 : <span id="foodCalorie">${food.foodCalorie}</span>kcal</p>
					<p>탄수화물 : ${food.foodCarbs}g</p>
					<p>단백질 : ${food.foodProtein}g</p>
					<p>지방 : ${food.foodFat}g</p>
				</div>
				<br>
			</label>
	
		</c:forEach>
		
		칼로리 합계 <input type="text" name="sum" id="sum" readonly><br><br>

		<input type="hidden" name="id" value="${idKey.id}">
		<input type="submit" value="식단짜기">

	</form>
	
	<div class="pageInfo_wrap" >
		<div class="pageInfo_area">
			<ul id="pageInfo" class="pageInfo">
			
				<!-- 이전페이지 버튼 -->
				<c:if test="${pageMaker.prev}">
					<li class="pageInfo_btn previous"><a href="${pageMaker.startPage-1}">Previous</a></li>
				</c:if>
				
				<!-- 각 번호 페이지 버튼 -->
				<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<li class="pageInfo_btn ${pageMaker.cri.pageNum == num ? "active":"" }"><a href="${num}">${num}</a></li>
				</c:forEach>
				
				<!-- 다음페이지 버튼 -->
				<c:if test="${pageMaker.next}">
					<li class="pageInfo_btn next"><a href="${pageMaker.endPage + 1 }">Next</a></li>
				</c:if>	
				
			</ul>
		</div>
	</div>
	
	<form class="moveForm" method="get">	
		<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
		<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
		<input type="hidden" name="carbs" value="${session[0]}">
		<input type="hidden" name="protein" value="${session[1]}">
		<input type="hidden" name="fat" value="${session[2]}"> 
	</form>
	
<script>

// 유저가 선택한 음식의 칼로리 합 구하기
function itemSum(frm) {
	var kcal = "${kcal}";
	var sum = 0;
	var count = frm.food.length;
	for(var i = 0; i < count; ++i) {
		if(frm.food[i].checked == true) {
			sum += parseInt(document.getElementsByTagName('span')[i].innerText);
			
			if(sum > kcal) {
				alert("일일칼로리를 초과하였습니다! 다시 선택해 주세요.");
				frm.food[i].checked == false;
			}
		}
	}
	 frm.sum.value = sum;

}

// 페이징 처리
let moveForm = $(".moveForm");

$(".pageInfo a").on("click", function(e){
	e.preventDefault();
	moveForm.find("input[name='pageNum']").val($(this).attr("href"));
	moveForm.attr("action", "dietStep4.do");
	moveForm.submit();
	
});
	
</script>

</body>
</html>