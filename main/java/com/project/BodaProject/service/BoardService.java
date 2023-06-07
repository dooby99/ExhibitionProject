package com.project.BodaProject.service;

import com.project.BodaProject.domain.User.UserEntity;
import com.project.BodaProject.domain.board.BoardEntity;
import com.project.BodaProject.dto.CommunityPagenationVO;
import com.project.BodaProject.repository.BoardRepository;
import com.project.BodaProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;

    public List<BoardEntity> selectAllBoards() {
        return boardRepository.findAllBoard();
    }

    // 게시글 조회수 증가
    public void upViewCnt(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        boardEntity.setViews(boardEntity.getViews() + 1);
        boardRepository.save(boardEntity);
    }

    // 게시글 단건 조회
    public BoardEntity selectBoardByBoardNo(Long boardNo) {
        return boardRepository.findBoardByNo(boardNo);
    }

    // 게시글 단건 조회 + 이전글, 다음글
    public HashMap<String, BoardEntity> selectBoardsByBoardNo(Long boardNo) {
        BoardEntity board = boardRepository.findBoardByNo(boardNo);
        BoardEntity prevBoard = boardRepository.findPrevBoardEntityByBoardNo(boardNo);
        BoardEntity nextBoard = boardRepository.findNextBoardEntityByBoardNo(boardNo);
        HashMap<String, BoardEntity> boardNoticeMap = new HashMap<>();
        boardNoticeMap.put("board", board);
        boardNoticeMap.put("prevBoard", prevBoard);
        boardNoticeMap.put("nextBoard", nextBoard);

        return boardNoticeMap;
    }

    /* 페이지 네이션 */
    private static final int BOARD_COUNT_PER_PAGE = 10; // 한페이지 당 보여줄 게시글의 수

    @Transactional
    public CommunityPagenationVO getBoardListView(int pageNum) {
        int totalBoardCnt = boardRepository.findCount().intValue();
        int startRow = 0;
        List<BoardEntity> boardEntityList = null;
        CommunityPagenationVO boardPagenationVO = null;
        if (totalBoardCnt > 0) {
            startRow = (pageNum - 1) * BOARD_COUNT_PER_PAGE;

            boardEntityList = boardRepository.findBoardEntityListView(startRow, BOARD_COUNT_PER_PAGE);
        } else {
            pageNum = 0;
        }

        int endRow = startRow * BOARD_COUNT_PER_PAGE;

        boardPagenationVO = new CommunityPagenationVO(totalBoardCnt, pageNum, boardEntityList, BOARD_COUNT_PER_PAGE, startRow, endRow);

        return boardPagenationVO;
    }

    /* 검색 기능 (작성자명) */
    @Transactional
    public CommunityPagenationVO getImpListViewByWriterName(int pageNum, String writer) {
        int totalBoardCnt = boardRepository.findBoardNoticeSearchResultTotalCountWN(writer);
        int startRow = 0;
        List<BoardEntity> boardEntityList = null;
        CommunityPagenationVO communityPagenationVO = null;
        if (totalBoardCnt > 0) {
            startRow = (pageNum - 1) * BOARD_COUNT_PER_PAGE;
            boardEntityList = boardRepository.findBoardImpListViewByWriterName(writer, startRow, BOARD_COUNT_PER_PAGE);
        } else {
            pageNum = 0;
        }

        int endRow = startRow * BOARD_COUNT_PER_PAGE;
        communityPagenationVO = new CommunityPagenationVO(totalBoardCnt, pageNum, boardEntityList, BOARD_COUNT_PER_PAGE, startRow, endRow);

        return communityPagenationVO;
    }

    /* 검색 기능 (제목 또는 내용) */
    @Transactional
    public CommunityPagenationVO getImpListViewByTitleAndContent(int pageNum, String titleOrContent) {
        int totalBoardCnt = boardRepository.findBoardImpSearchResultTotalCountTC(titleOrContent);
        int startRow = 0;
        List<BoardEntity> boardEntityList = null;
        CommunityPagenationVO communityPagenationVO = null;
        if (totalBoardCnt > 0) {
            startRow = (pageNum - 1) * BOARD_COUNT_PER_PAGE;
            boardEntityList = boardRepository.findBoardImpListViewByTitleOrContent(titleOrContent, startRow, BOARD_COUNT_PER_PAGE);
        } else {
            pageNum = 0;
        }

        int endRow = startRow * BOARD_COUNT_PER_PAGE;
        communityPagenationVO = new CommunityPagenationVO(totalBoardCnt, pageNum, boardEntityList, BOARD_COUNT_PER_PAGE, startRow, endRow);

        return communityPagenationVO;
    }

    /* 추가(작성) */
    public BoardEntity saveBoard(String title, Long user_id, String content) {
        UserEntity userEntity = null;
        Optional<UserEntity> userOpt = userRepository.findById(user_id);
        if (userOpt.isPresent()) {
            userEntity = userOpt.get();
        }

        BoardEntity boardEntity = new BoardEntity(null, title, content, userEntity, LocalDateTime.now());
        return boardRepository.save(boardEntity);
    }

    /* 수정 */
    @Transactional
    public Long updateBoard(Long boardNo, String title, Long user_id, String content) { // 해당 board에 boardId, memNo, regDt 등이 담겨 있다면 다른 내용들도 따로 set하지 않고 바로 save해도 boardId, memNo등이 같으니 변경을 감지하지 않을까?
        UserEntity userEntity = null;
        Optional<UserEntity> userOpt = userRepository.findById(user_id);
        if (userOpt.isPresent()) {
            userEntity = userOpt.get();
        }

        BoardEntity newBoard = null;
        BoardEntity originBoard = boardRepository.findBoardByNo(boardNo);
        if (originBoard != null) {
            newBoard = new BoardEntity(boardNo, title, content, userEntity, originBoard.getCreateT());

            boardRepository.save(newBoard);
        }

        return boardNo;
    }

    /* 삭제 */
    @Transactional
    public void deleteBoardByBoardId(Long boardNo) {
        BoardEntity boardEntity = boardRepository.findBoardByNo(boardNo);
        boardRepository.delete(boardEntity);
    }





}
