package com.ezen.board.dao;

import com.ezen.board.dto.Article;
import com.ezen.board.dto.Board;

import java.sql.SQLException;
import java.util.List;

public interface ArticleDao {
    public List<Board> findByBoardAll() throws SQLException;

    public void createArticle(Article article) throws SQLException;

    public List<Article> findByAll(int rowCount, int boardNum, int requestPage, String type, String value) throws SQLException;

    public int findByArticleCount(int boardNum, String type, String value) throws SQLException;

    public Article readArticle(int boardNum, int articleNum) throws SQLException;

//    public void createReply(Article article) throws SQLException;

    public void updateArticleHitCount(int boardNum, int articleNum) throws SQLException;
}
