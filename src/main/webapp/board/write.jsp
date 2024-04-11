<%@ page import="com.ezen.board.dto.Article" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %>
<%@ page import="com.ezen.board.service.BoardService" %><%--게시글 읽기 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:if test="${empty loginMember}">--%>
<%--    <c:set scope="request" var="message" value="게시판 글쓰기는 회원만 가능합니다!"/>--%>
<%--    <c:set var="referer" value="/board/register.jsp" scope="request"/>--%>
<%--    <jsp:forward page="/member/login.jsp"/>--%>
<%--</c:if>--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HAN ZO</title>
    <link rel="stylesheet" href="/css/article.css">
    <link rel="stylesheet" href="/css/style.css">
    <script src="https://kit.fontawesome.com/89ab2ce88f.js" crossorigin="anonymous"></script>
</head>
<body>
<div id="wrapper">
    <!-- header start -->
    <jsp:include page="/module/header.jsp"/>
    <!-- header end -->

    <!-- nav start -->
    <jsp:include page="/module/nav.jsp"/>
    <!-- nav start -->
    <section class="notice">
        <div class="page-title">
            <div class="container">
                <div class="title">
                    <h1><input type="" class="form-control" name="article_title"></h1>
                </div>
                <div class="blog-profile">
                    <div class="blog-profile_text" readonly>
<%--                        <h4>${loginmember.id}</h4>--%>
                    </div>
                </div>
                <div class="blog-left">
                    <div class="blog-content">
                        <textarea class="writetext" name="article_content"></textarea>
                    </div>
                </div>
            </div>
        </div>

    </section>
</div>
<%-- footer start --%>
<jsp:include page="/module/footer.jsp"/>
<%-- footer end --%>
</body>
</html>