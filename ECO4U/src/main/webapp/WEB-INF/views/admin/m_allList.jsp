<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 내용 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>
<div class="page-main">
	<h2>전체회원</h2>
	<div class="sub-menu">
		<ul>
			<li><a href="admin_list.do">전체회원 조회</a>
			<li><a href="delete_list.do">탈퇴회원 조회</a>
		</ul>
	</div>
	<form action="admin_list.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>ID</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>이름</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>이메일</option>
					<option value="4" <c:if test="${param.keyfield == 4}">selected</c:if>>전체</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="찾기">
				<input type="button" value="목록" onclick="location.href='admin_list.do'">
			</li>
		</ul>
	</form>
	<c:if test="${count == 0}">
		<div class="align-center">표시할 회원 정보가 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
		<table>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>가입일</th>
				<th>등급</th>
			</tr>
			
			<c:forEach var="member" items="${list}">
			<tr>
				<td>
					<c:if test="${member.auth==0}">${membe666r.id}</c:if>
					<c:if test="${member.auth > 0}"><a href="#">${member.id}</a></c:if>
				</td>
				<td>${member.mem_name}</td>
				<td>${member.mem_email}</td>
				<td>${member.mem_cell}</td>
				<td>${member.reg_date}</td>
				<td>
					<c:if test="${member.auth==0}">탈퇴</c:if>
					<c:if test="${member.auth==1}">일반</c:if>
					<c:if test="${member.auth==2}">관리자</c:if>
				</td>
			</tr>			
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
	</c:if>
</div>
<!-- 내용 끝 -->