<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>宠物信息</title>
		<link rel="stylesheet" href="<c:url value='/css/show.css'/>">
	</head>
	<body>
		<h1>宠物信息</h1>
		<c:forEach items="${result.datas}" var="map">
			${map.id},${map.name},${map.color}<br>
		</c:forEach>
		<hr/>
		<c:if test="${result.currentPage != 1}">
			<a href="<c:url value='/PetServlet?${queryString}currentPage=${result.currentPage-1}'/>">上一页</a>
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
		<c:if test="${result.currentPage != result.countPage}">
			<a href="<c:url value='/PetServlet?${queryString}currentPage=${result.currentPage+1}'/>">下一页</a>
		</c:if>
		<c:set var="abc" value="action=${queryString}&name=${queryString}&color=${queryString}"/>
	</body>
</html>