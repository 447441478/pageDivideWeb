<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>宠物管理</title>
		<link rel="stylesheet" href="<c:url value='/css/show.css'/>">
	</head>
	<body>
		<h1>宠物信息查询</h1>
		<a href="<c:url value='/PetServlet'/>">查看所有</a>
		<br/>
		<br/>
		<form action="<c:url value='/PetServlet?action=likeQuery'/>" method="post">
			<table>
				<tr>
					<td>宠物名：</td>
					<td><input type="text" name='name' value="${pet.name}" /></td>
				</tr>
				<tr>
					<td>颜&emsp;色：</td>
					<td><input type="text" name='color' value="${pet.color}" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value='查询' /></td>
				</tr>
			</table>
		</form>
		<c:if test="${!empty queryString}">
			<hr />
			<table class="showTable">
				<tr>
					<td>编号</td>
					<td>名字</td>
					<td>颜色</td>
				</tr>
				<c:forEach items="${result.datas}" var="map">
					<tr>
						<td>${map.id}</td>
						<td>${map.name}</td>
						<td>${map.color}</td>
					</tr>
				</c:forEach>
			</table>
			<hr/>
			<!-- 显示页码 -->
			<div class="page">
				<c:if test="${result.currentPage != 1}" var="boo">
					<a href="<c:url value='/PetServlet?${queryString}currentPage=${result.currentPage-1}'/>">上一页</a>
				</c:if>
				<c:if test="${!boo}">
					<span>上一页</span>
				</c:if>
				<c:forEach begin="${show.showStart}" end="${show.showEnd}" var="idx">
					<c:if test="${result.currentPage==idx}" var="boo">
						<span class="pageCode">${idx}</span>
					</c:if>
					<c:if test="${!boo}">
						<span class="pageCode">
							<a href="<c:url value='/PetServlet?${queryString}currentPage=${idx}'/>">${idx}</a>
						</span>
					</c:if>
				</c:forEach>
				<c:if test="${result.currentPage != result.countPage}" var="boo">
					<a href="<c:url value='/PetServlet?${queryString}currentPage=${result.currentPage+1}'/>">下一页</a>
				</c:if>
				<c:if test="${!boo}">
					<span>下一页</span>
				</c:if>
			</div>
			
		</c:if>
	</body>
</html>