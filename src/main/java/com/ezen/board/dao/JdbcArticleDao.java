package com.ezen.board.dao;

import com.ezen.board.dto.Article;
import com.ezen.board.dto.ArticleComment;
import com.ezen.board.dto.Board;
import com.ezen.mall.domain.common.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcArticleDao implements ArticleDao {
    private ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

    /**
     * 게시판 전체
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Board> findByBoardAll() throws SQLException {
        List<Board> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT board_Num, board_Name, board_Info")
                .append(" FROM board")
                .append(" ORDER BY board_Num");

        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Board board = new Board();
                board.setBoardNum(rs.getInt("board_Num"));
                board.setBoardName(rs.getString("board_Name"));
                board.setBoardInfo(rs.getString("board_Info"));
                list.add(board);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * 게시글 쓰기
     *
     * @param article
     * @throws SQLException
     */
    @Override
    public void createArticle(Article article) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO article(article_num, article_title, article_content, article_date, hitcount, user_id, board_num)")
                .append(" VALUES(article_seq.nextval, ?, ?, TO_CHAR(article_date, 'YYYY-MM-DD') article_date, ?, ?, ?, ?)");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, article.getArticleTitle());
            pstmt.setString(2, article.getArticleContent());
            pstmt.setString(3, article.getArticleDate());
            pstmt.setInt(4, article.getHitcount());
            pstmt.setString(5, article.getUserId());
            pstmt.setInt(6, article.getBoardNum());
            pstmt.executeUpdate();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 게시글 전체 목록 반환
     *
     * @param rowCount    테이블당 보여지는 행의 갯수
     * @param requestPage 사용자 요청 페이지
     * @param type        검색 유형
     * @param value       검색 값
     * @return 검색 목록
     * @throws SQLException
     */
    public List<Article> findByAll(int rowCount, int boardNum, int requestPage, String type, String value) throws SQLException {
        List<Article> list = new ArrayList<>();
        if (type != null && type.equals("")) {
            type = null;
        }
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT board_num, article_num, article_title, user_id, article_date, hitcount")
                .append(" FROM (SELECT board_num, article_num, CEIL(rownum / ?) request_page, article_title, user_id, TO_CHAR(article_date, 'YYYY-MM-DD HH24:MI') article_date, hitcount")
                .append(" FROM (SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT")
                .append("       FROM article")
                .append("       WHERE board_num = ?");
        if (type != null) {
            switch (type) {
                case "t": // 제목 검색
                    value = "%" + value + "%";
                    sql.append("      AND ARTICLE_TITLE LIKE ?");
                    break;
                case "w": // 작성자 검색
                    sql.append("      AND USER_ID = ?");
                    break;
            }
        }
        sql.append(" ))")
                .append(" WHERE request_page = ?");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, rowCount);
            pstmt.setInt(2, boardNum);
            if (type == null) {
                pstmt.setInt(3, requestPage);
            } else {
//                조건 검색인 경우
                switch (type) {
                    case "t":
                    case "w":
                        pstmt.setInt(3, requestPage);
                        pstmt.setString(4, value);
                        break;
                    case "tw":
                        pstmt.setInt(3, requestPage);
                        pstmt.setString(4, value);
                        pstmt.setString(5, value);
                        break;
                }
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Article article = new Article();
                article.setBoardNum(rs.getInt("board_num"));
                article.setArticleNum(rs.getInt("article_num"));
                article.setArticleTitle(rs.getString("article_title"));
                article.setUserId(rs.getString("user_id"));
                article.setArticleDate(rs.getString("article_date"));
                article.setHitcount(rs.getInt("hitcount"));
                list.add(article);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * 검색
     *
     * @param type
     * @param value
     * @return
     * @throws SQLException
     */
    @Override
    public int findByArticleCount(int boardNum, String type, String value) throws SQLException {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT count(*) count")
                .append(" FROM article")
                .append(" WHERE board_num = ?");

        if (type != null) {
            switch (type) {
                case "t": // 제목 검색
                    value = "%" + value + "%";
                    sql.append(" AND article_title LIKE ?");
                    break;
                case "w": // 작성자 검색
                    sql.append(" AND user_id LIKE ?");
                    break;
                case "tw": // 제목과 작성자 검색
                    value = "%" + value + "%";
                    sql.append(" AND article_title LIKE ? OR user_id LIKE ?");
                    break;
            }
        }
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, boardNum);
            if (type != null) {
//                조건 검색
                switch (type) {
                    case "t":
                    case "w":
                        pstmt.setString(2, value);
                        break;
                    case "tw":
                        pstmt.setString(2, value);
                        pstmt.setString(3, value);
                        break;
                }
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    /**
     * 게시글 읽기
     *
     * @param articleNum
     * @param boardNum
     * @return
     * @throws SQLException
     */
    @Override
    public Article readArticle(int articleNum, int boardNum) throws SQLException {
        Article article = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT article_num, board_num, article_title, article_content, user_id, TO_CHAR(article_date, 'YYYY-MM-DD') article_date, hitcount")
                .append(" FROM article")
                .append(" WHERE article_num = ? AND board_num = ?");
        Connection con = connectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, articleNum);
            pstmt.setInt(2, boardNum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = new Article();
                article.setArticleNum(rs.getInt("article_num"));
                article.setBoardNum(rs.getInt("board_num"));
                article.setArticleTitle(rs.getString("article_title"));
                article.setArticleContent(rs.getString("article_content"));
                article.setUserId(rs.getString("user_id"));
                article.setArticleDate(rs.getString("article_date"));
                article.setHitcount(rs.getInt("hitcount"));
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return article;
    }

    /**
     * 조회수 증가
     *
     * @param articleNum
     * @param boardNum
     * @throws SQLException
     */
    @Override
    public void updateArticleHitCount(int articleNum, int boardNum) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ARTICLE")
                .append(" SET HITCOUNT = HITCOUNT + 1")
                .append(" WHERE  article_num = ? AND board_num = ?");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, articleNum);
            pstmt.setInt(2, boardNum);
            pstmt.executeUpdate();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 댓글 출력
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<ArticleComment> commentListAll() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT comment_num, comment_content, comment_date, article_num, user_id")
                .append(" FROM ARTICLE_COMMENT");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArticleComment> acList = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ArticleComment articleComment = new ArticleComment();
                articleComment.setCommentNum(rs.getInt("comment_num"));
                articleComment.setCommentContent(rs.getString("comment_content"));
                articleComment.setCommentDate(rs.getString("comment_date"));
                articleComment.setArticleNum(rs.getInt("article_num"));
                articleComment.setUserId(rs.getString("user_id"));
                acList.add(articleComment);
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return acList;
    }

    /**
     * 댓글 등록
     */
    public void createReply(ArticleComment articleComment) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO article_comment(COMMENT_NUM,COMMENT_CONTENT,COMMENT_DATE,ARTICLE_NUM,USER_ID)")
                .append(" VALUES(comment_seq.nextval, ?, ?, ?, ?)");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, articleComment.getCommentContent());
            pstmt.setString(2, articleComment.getCommentDate());
            pstmt.setInt(3, articleComment.getArticleNum());
            pstmt.setString(4, articleComment.getUserId());
            pstmt.executeUpdate();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        JdbcArticleDao jdbcArticleDao = new JdbcArticleDao();
//        List<Article> test = jdbcArticleDao.findBydAll(10,1,null,null);
//        System.out.println(test);
//        int count = jdbcArticleDao.findByArticleCount(10, null, null);
//        System.out.println(count);
//        List<Article> test = jdbcArticleDao.findByAll(10, 10, 1,null,null);
//        System.out.println(test);
//        Article article = jdbcArticleDao.readArticle(1, 10);
//        System.out.println(article);
    }
}
