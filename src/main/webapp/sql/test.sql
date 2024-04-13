SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT       
      FROM article       
      WHERE board_num = 10 

;
SELECT board_num, article_num, article_title, user_id, article_date, hitcount 
FROM (SELECT board_num, article_num, CEIL(rownum / 10) request_page, article_title, user_id, TO_CHAR(article_date, 'YYYY-MM-DD HH24:MI') article_date, hitcount 
      FROM (SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT       
      FROM article       
      WHERE board_num = 10 
      ORDER BY article_num desc
      ))
      WHERE request_page = 1;


SELECT board_num, article_num, article_title, user_id, article_date, hitcount
FROM article
WHERE ARTICLE_TITLE LIKE ?
;
WHERE request_page = ?
;
SELECT board_num, article_num, article_title, user_id, article_date, hitcount FROM article WHERE  WHERE request_page = 1

;
SELECT board_num, article_num, article_title, user_id, article_date, hitcount 
FROM (SELECT board_num, article_num, CEIL(rownum / ?) request_page, article_title, user_id, TO_CHAR(article_date, 'YYYY-MM-DD HH24:MI') article_date, hitcount 
FROM (SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT       FROM article       WHERE board_num = ? )) 
WHERE request_page = ?

;

SELECT board_num, article_num, article_title, user_id, article_date, hitcount 
FROM (SELECT board_num, article_num, CEIL(rownum / ?) request_page, article_title, user_id, TO_CHAR(article_date, 'YYYY-MM-DD HH24:MI') article_date, hitcount 
FROM (SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT       
FROM article       WHERE board_num = ?      AND ARTICLE_TITLE LIKE ? )) 
WHERE request_page = ?