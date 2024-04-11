<%@ page import="com.ezen.board.dto.Article" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %>
<%@ page import="com.ezen.board.service.BoardService" %><%--게시글 읽기 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:if test="${empty loginMember}">--%>
<%--    <c:set scope="request" var="message" value="게시판 글읽기는 회원만 가능합니다!"/>--%>
<%--    <jsp:forward page="/member/login.jsp"/>--%>
<%--</c:if>--%>

<%
    String articleNum = request.getParameter("articleNum");
    String boardNum = request.getParameter("boardNum");

    BoardService boardService = new BoardServiceImpl();
    Article article = boardService.getReadArticle(Integer.parseInt(articleNum), Integer.parseInt(boardNum));

    request.setAttribute("article", article);
%>
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
                    <h1><input type="text" class="form-control" name="article_title" value="${article.articleTitle}" readonly></h1>
                </div>
                <div class="blog-profile">
                    <div class="blog-profile_text" readonly>
                        <h4>${article.userId}</h4>
                    </div>
                </div>
                <div class="blog-left">
                    <div class="blog-content" readonly>
                        <p>${article.articleContent}</p>
                    </div>
                    <div class="blog-share">
                        <span class="good">추천 5</span>
                        <span class="not-good">비추천 2</span><br>
                    </div>
                    <div class="blog-share-reply">
                        <span class="reply">댓글 3</span>
                    </div>
                    <div class="reply-table">
                        <div>
                            <span class="reply-id">${article.userId}</span>
                            |
                            <span class="reply-text">댓</span>
                        </div>
                        <div>
                            <span class="reply-id">12345678901234890</span>
                            |
                            <span class="reply-text">kajdfhghasldghjklsdhgkjl</span>
                        </div>
                        <span>닉네임 | 댓글 내용~</span>
                        <span>닉네임 | 댓글 내용~</span>
                        <span>닉네임 | 댓글 내용~</span>
                        <span>닉네임 | 댓글 내용~</span>
                        <span>닉네임 | 댓글 내용~</span>
                        <div class="reply-add">
                            <input class="reply-input"></input>
                            <button class="reply-btn">작성</button>
                        </div>
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