package com.project.BodaProject.repository;

import com.project.BodaProject.domain.board.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository <CommentEntity, Long> {

    @Query("select c from CommentEntity c where c.board.no=?1")
    List<CommentEntity> findByBoardNo(Long boardNo);

//    List<CommentEntity> findByBoard_No(Long boardNo);

    List<CommentEntity> findByWriter_Id(Long Id);

//    List<CommentEntity> findTop5ByWriter_IdOrderByCreateTimeDesc(Long Id);

    @Query("select count(c) from CommentEntity c where c.writer.id=?1")
    Long findCountByWriter_Id(Long Id);

    @Query(value = "select c.* from CommentEntity c where c.user_id = ?1 order by c.id desc limit ?2, ?3"
            , nativeQuery = true)
    List<CommentEntity> findCommentEntityByListViewByUserId(Long Id, int startRow, int COMMENT_COUNT_PER_PAGE);


}
