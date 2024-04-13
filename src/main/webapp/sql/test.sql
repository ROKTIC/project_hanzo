SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT       
      FROM article       
      WHERE board_num = 10;


SELECT B.board_num, B.article_num, B.article_title, B.user_id, B.article_date, B.hitcount
      ,(SELECT COUNT(*) FROM article_comment A WHERE A.ARTICLE_NUM = B.article_num) -- 스칼라 서브쿼리 
FROM (SELECT board_num, article_num, CEIL(rownum / 10) request_page, article_title, user_id, TO_CHAR(article_date, 'YYYY-MM-DD HH24:MI') article_date, hitcount 
      FROM (SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT       
      FROM article
      WHERE board_num = 10
      AND ARTICLE_TITLE LIKE '%안녕%'
      ORDER BY article_num
      ))B 
      WHERE request_page = 1;


SELECT board_num, article_num, article_title, user_id, article_date, hitcount
FROM (SELECT board_num, article_num, CEIL(rownum / 10) request_page, article_title, user_id, TO_CHAR(article_date, 'YYYY-MM-DD HH24:MI') article_date, hitcount 
      FROM (SELECT board_num, article_num, ARTICLE_TITLE, USER_ID, ARTICLE_DATE, HITCOUNT
      FROM article
      WHERE board_num = 10
      AND ARTICLE_TITLE LIKE '%안녕%'
      ORDER BY article_num
      ))
      WHERE request_page = 1;




-- SELECT  : 내가 가져오고 싶은 테이블 컬럼
-- FROM    : 내가 가져오려고 하는 테이블
-- WHERE   : 내가 가져오려고 하는 조건

SELECT count(a.ARTICLE_NUM)
from article a
join ARTICLE_COMMENT b
on a.ARTICLE_NUM = b.article_num 
where a.ARTICLE_NUM = 1
;




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
WHERE request_page = ?;


SELECT comment_num, comment_content, comment_date, article_num, user_id
FROM ARTICLE_COMMENT 
Where article_num = 590;

select count(*) count
from article_comment
WHERE article_num = 1;


select * from article_comment;