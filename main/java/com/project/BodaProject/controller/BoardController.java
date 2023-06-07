package com.project.BodaProject.controller;

import com.project.BodaProject.domain.board.BoardEntity;
import com.project.BodaProject.dto.CommunityPagenationVO;
import com.project.BodaProject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping("/board/update")
    public String BoardUpdateForm(Long boardNo, Model model) {
        model.addAttribute("board", boardService.selectBoardByBoardNo(boardNo));

        return "/updateForm";
    }

    @RequestMapping("/community")
    public String Board(Model model, @RequestParam(name = "p", required = false, defaultValue = "1") int pageNum,
                           @RequestParam(name = "con", required = false) String con,
                           @RequestParam(name = "query", required = false) String query) {

        CommunityPagenationVO pagenationVO = null;
        if (query != null) {
            switch (con) {
                case "writerName":

                    pagenationVO = boardService.getImpListViewByWriterName(pageNum, query);

                    break;

                case "TitleOrContent":
                    pagenationVO = boardService.getImpListViewByTitleAndContent(pageNum, query);
                    break;
            }

        } else {
            pagenationVO = boardService.getBoardListView(pageNum);
        }
        model.addAttribute("pagenation", pagenationVO);

        return "community";
    }

    @RequestMapping("/community/{boardNo}")
    public String impDetail(@PathVariable Long boardNo, Model model) {
        Map<String, BoardEntity> boards = boardService.selectBoardsByBoardNo(boardNo);
        model.addAttribute("board", boards.get("board"));
//        model.addAttribute("prevBoard", boards.get("prevBoard"));
//        model.addAttribute("nextBoard", boards.get("nextBoard"));

        boardService.upViewCnt(boardNo);
        return "view";
    }

    @RequestMapping("/community/write")
    public String BoardWriteForm() {

        return "/write";
    }

    @ResponseBody
    @RequestMapping(value = "/community/writeProc", method = RequestMethod.POST)
    public Long WriteProc(String title, Long writerNo, String content) {
        return boardService.saveBoard(title, writerNo, content).getNo();
    }

    @RequestMapping("/view/update")
    public String impBoardUpdateForm(Long boardNo, Model model) {
        model.addAttribute("board", boardService.selectBoardByBoardNo(boardNo));

        return "/write";
    }

    @ResponseBody
    @RequestMapping("/community/updateProc")
    public Long impUpdateProc(Long boardNo, String title, Long writerNo, String content) {
        Long bId = boardService.updateBoard(boardNo, title, writerNo, content);

        return bId;
    }

    @RequestMapping("/view/delete")
    public String impDeleteProc(Long boardNo) {
        boardService.deleteBoardByBoardId(boardNo);

        return "redirect:/community";
    }


}
