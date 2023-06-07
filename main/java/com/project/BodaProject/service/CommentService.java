package com.project.BodaProject.service;

import com.project.BodaProject.domain.User.UserEntity;
import com.project.BodaProject.domain.board.BoardEntity;
import com.project.BodaProject.domain.board.CommentEntity;
import com.project.BodaProject.dto.CommentDto;
import com.project.BodaProject.repository.BoardRepository;
import com.project.BodaProject.repository.CommentRepository;
import com.project.BodaProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    //my_page에 사용
//    public List<CommentDto> getMyCommTop5(Long memNo) {
//        List<CommentEntity> commList = commentRepository.findTop5ByWriter_MemNoOrderByRegDateDesc(memNo);
//        List<CommentEntity> commVOList = new ArrayList<>();
//        for (CommentEntity c : commList) {
//            commVOList.add(new CommentDto(c));
//        }
//        return commVOList;
//    }

    public CommentEntity saveComment(String content, Long boardNo, Long Id) {
        BoardEntity boardEntity = boardRepository.findById(boardNo).get();
        UserEntity userEntity = userRepository.findById(Id).get();
        CommentEntity commentEntity = CommentEntity.builder()
                .id(null)
                .content(content)
                .writer(userEntity)
                .board(boardEntity)
                .build();
        log.info("생성된 comment의 내용 : " + commentEntity.getContent());
        return commentRepository.save(commentEntity);
    }

    public CommentEntity updateComment(Long Id, String content) {
        CommentEntity commentEntity = commentRepository.findById(Id).get();
        commentEntity.setContent(content);
        return commentRepository.save(commentEntity);
    }

    public void deleteComment(Long Id) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(Id);
        if (commentEntity.isPresent()) {
            commentRepository.delete(commentEntity.get());
        }
    }

    public List<CommentDto> getCommentDtoList(Long boardNo) {
        List<CommentEntity> commentEntityList = commentRepository.findByBoardNo(boardNo);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            commentDtoList.add(new CommentDto(commentEntity));
        }

        return commentDtoList;
    }

//    public List<CommentEntity> show(Long boardNo){
//        List<CommentEntity> byBoardNo = commentRepository.findByBoard_No(boardNo);
//        return byBoardNo;
//    }


    public List<CommentDto> getCommentDtoListById(Long Id) {
        List<CommentEntity> commentEntityList = commentRepository.findByWriter_Id(Id);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            commentDtoList.add(new CommentDto(commentEntity));
        }

        return commentDtoList;
    }

    private final static int COMMENT_COUNT_PER_PAGE = 20;
    private Long Id;

    public void setId(Long Id) {
        this.Id = Id;
    }

    // 본인이 작성한 댓글(마이페이지에서 조회 가능)
//    public CommentImpPagenationVO getMyCommListView(int pageNum) {
//        Long totalCommCnt = commentImpRepository.findCountByWriter_MemNo(memNo);
//        int startRow = 0;
//        List<CommentImpVO> commVOList = new ArrayList<>();
//        CommentImpPagenationVO commPagenationVO = null;
//        if (totalCommCnt > 0) {
//            startRow = (pageNum - 1) * COMMENT_COUNT_PER_PAGE;
//
//            List<CommentImp> commList = commentImpRepository.findCommentEntityByListViewByUserId(Id, startRow, COMMENT_COUNT_PER_PAGE);
//            for (CommentImp comment : commList) {
//                commVOList.add(new CommentImpVO(comment));
//            }
//
//        } else {
//            pageNum = 0;
//        }
//
//        int endRow = startRow * COMMENT_COUNT_PER_PAGE;
//
//        commPagenationVO = new CommentImpPagenationVO(totalCommCnt, pageNum, commVOList, COMMENT_COUNT_PER_PAGE, startRow, endRow);
//
//        return commPagenationVO;
//    }

}
