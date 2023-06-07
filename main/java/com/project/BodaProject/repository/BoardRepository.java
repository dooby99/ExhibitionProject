package com.project.BodaProject.repository;

import com.project.BodaProject.domain.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository <BoardEntity, Long>{

    // 모든 소감글 조회
    @Query(value = "select b from BoardEntity b")
    List<BoardEntity> findAllBoard();

    // board_id로 조회
    BoardEntity findBoardByNo(Long boardNo);

    // 이전글
    @Query(value = "select b.* from board_entity b join user_entity u on b.write_no = u.id where b.no = " +
            "(select b.no from board_entity b where b.no < ?1 order by b.no desc limit 1)"
            , nativeQuery = true)
    BoardEntity findPrevBoardEntityByBoardNo(Long boardNo);

    // 다음글
    @Query(value = "select b.* from board_entity b join user_entity u on b.write_no = u.id where b.no = " +
            "(select b.no from board_entity b where b.no > ?1 order by b.no asc limit 1)"
            , nativeQuery = true)
    BoardEntity findNextBoardEntityByBoardNo(Long boardNo);


    // 작성자명 검색 결과 조회
    @Query(value = "select count(b) from BoardEntity b where b.name.name like %?1%")
    int findBoardNoticeSearchResultTotalCountWN(String writerName);

    @Query(value = "select b.* from BoardEntity b join UserEntity u on b.write_no = u.id where u.name = ?1 order by b.no desc limit ?2, ?3"
            , nativeQuery = true)
    List<BoardEntity> findBoardImpListViewByWriterName(String writerName, int startRow, int boardCntPerPage);

    // 제목 또는 내용으로 검색 결과 조회
    @Query("select count(b) from BoardEntity b where b.title like %?1% or b.content like %?1%")
    int findBoardImpSearchResultTotalCountTC(String titleOrContent);

    @Query(value = "select b.* from BoardEntity b join UserEntity u on b.write_no = u.id where b.title like %?1% or b.content like %?1% order by b.no desc limit ?2, ?3"
            , nativeQuery = true)
    List<BoardEntity> findBoardImpListViewByTitleOrContent(String titleOrContent, int startRow, int boardCntPerPage);








    /* 페이지네이션 */
    // 모든 게시글 조회
    @Query(value = "select count(b) from BoardEntity b")
    Long findCount();

    @Query(value = "select b.* from board_entity b join user_entity u on b.write_no = u.id order by b.no desc limit ?1, ?2"
            , nativeQuery = true)
    List<BoardEntity> findBoardEntityListView(int startRow, int boardCntPerPage);

}
