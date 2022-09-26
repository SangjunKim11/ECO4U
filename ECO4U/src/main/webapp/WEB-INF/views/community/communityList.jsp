<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/community.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/community.like.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/community.css">
<style>

#compt{
height:180px;
max-width:100%;
}

table{
padding-left:45px;
padding-right:45px;

}

.card{
text-align:center;
}


</style>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 내용 시작 -->
<div class="page-main">
	<div class="align-center">
		<h2 onclick="location.href='list.do?c_category=1'">Community</h2>
	</div>
	<div class="community-list-header">
		<ul class="community-category-ul" id="c_category">
			<li class="tip1"><a href="list.do?c_category=1">TIP</a></li>
			<li class="tip2"><a href="list.do?c_category=2">함께해요</a></li>
			<li class="tip3"><a href="list.do?c_category=3">친환경 소식</a></li>
		</ul>




		<form action="list.do" id="search_form" method="get">
			<input type="hidden" name="c_category" value="${param.c_category}">
			<ul class="search">
				<li><select name="keyfield" id="keyfield">
						<option value="1"
							<c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
						<option value="2"
							<c:if test="${param.keyfield == 2}">selected</c:if>>작성자
							ID</option>
						<option value="3"
							<c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
						<option value="4"
							<c:if test="${param.keyfield == 4}">selected</c:if>>제목+내용</option>
				</select></li>
				<li><input type="search" name="keyword" id="keyword"
					value="${param.keyword}"></li>
				<li><input type="submit" id="searchBtn" value="찾기"></li>
				<li>
					<!-- 글쓰기 버튼 --> <c:if test="${!empty user}">
						<input type="button" id="writeBtn" value="글쓰기"
							onclick="location.href='write.do'">
					</c:if>
				</li>
			</ul>


		</form>
	</div>


	<!-- 목록 내용 -->
	<div class="content">
		<!-- 게시글 목록 -->
		<c:if test="${count == 0}">
			<div class="community-result-display">표시할 게시물이 없습니다.</div>
		</c:if>

		<c:if test="${count > 0}">
		
		<div class="dp-btn">
		<div class="dropdown">
			<button class="dropbtn" type="button">정렬</button>
			<div class="dropdown-menu">

				<ul>
					<li><a class="dropdown-item"
						href="/community/list.do?c_category=${c_category}&sort=1">최신순</a></li>
					<li><a class="dropdown-item"
						href="/community/list.do?c_category=${c_category}&sort=2">조회수순</a></li>
					<li><a class="dropdown-item"
						href="/community/list.do?c_category=${c_category}&sort=3">좋아요순</a></li>
					<li><a class="dropdown-item"
						href="/community/list.do?c_category=${c_category}&sort=4">댓글수순</a></li>
				</ul>
			</div>
		</div></div>

			<c:forEach var="community" items="${list}">
				<c:if test="${community.c_auth==1}">
					<ul>
						<li class="notice-margin"><a href="detail.do?c_num=${community.c_num}"><span
								class="notice-icon">공지사항</span>
								<b><span class="notice-font">　${community.c_title}</span></b></a></li>
					</ul>
					<hr width="100%" size="1">
				</c:if>
			</c:forEach>

			<!-- TIP 게시판, 함께해요 게시판 -->
			<c:forEach var="community" items="${list}">
				<c:if
					test="${community.c_auth==0 && community.c_category==1 || community.c_category==2}">

					<table>
						<tr>
							<td>${community.id}· ${community.reg_date}</td>
							<td rowspan="3" width="15%"><span id="commu-photo" class="commu-photo">
									<c:if test="${!empty community.filename}">
											<img src="imageView.do?&c_num=${community.c_num}" width="200"
											height="150" class="com-photo" id="com-photo-1">
									</c:if>
								</span></td>
							</tr>
							<tr> <td class="co-list-title" width="85%"><a href="detail.do?c_num=${community.c_num}">${community.c_title}
							</a></td></tr>
							
						<tr>
						<td class="hlc"><img
							src="${pageContext.request.contextPath}/images/community/hit.png"
							width="20" height="20"> ${community.c_hit} <img
							src="${pageContext.request.contextPath}/images/community/like03.png"
							width="20" height="20"> ${community.like_cnt} <img
							src="${pageContext.request.contextPath}/images/community/comment.png"
							width="20" height="20"> ${community.com_cnt}</td></tr>
							
					</table>
					<hr>
				</c:if>
			</c:forEach>

			<!-- 친환경소식 -->
			<div class="row">

				<c:forEach var="community" items="${list}">
					<c:if test="${community.c_category==3}">

						<div class="news2" style="margin-top: 30px; padding: 5px 25px;">

							<div class="card" style="border: 0px;" >
								<a href="detail.do?c_num=${community.c_num}"> <c:if
										test="${!empty community.filename}">
										<img src="imageView.do?&c_num=${community.c_num}" width="300"
											height="240" class="com-photo" id="compt">
										<br>
										<span>${community.c_title}</span>
										<br>
										<span>${community.reg_date2}</span>
									</c:if> <c:if test="${empty community.filename}">
										<img
											src="${pageContext.request.contextPath}/images/no_image.png"
											width="300" height="240" id="compt">
										<br>
										<span>${community.c_title}</span>
										<br>
										<span>${community.reg_date2}</span>
									</c:if>
								</a>
							</div>

						</div>

					</c:if>
				</c:forEach>

			</div>
		</c:if>
		<div class="page align-center">${page}</div>
	</div>

</div>





<!-- 내용 끝 -->