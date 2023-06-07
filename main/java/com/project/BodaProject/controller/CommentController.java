package com.project.BodaProject.controller;

import com.project.BodaProject.domain.board.CommentEntity;
import com.project.BodaProject.dto.CommentDto;
import com.project.BodaProject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Slf4j
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    // 댓글 작성
    @GetMapping("/community/comment/write")
    public String writeCommentEntity(String content, Long boardNo, Long Id) {
        commentService.saveComment(content, boardNo, Id);
        return "main";
    }

    // 댓글 수정
    @RequestMapping("/community/comment/update")
    public String updateCommentEntity(String content, Long Id) {
        if (commentService.updateComment(Id, content) != null) {
            return "success";
        }
        return "false";
    }

    @RequestMapping("/community/comment/delete")
    public String deleteCommentEntity(Long Id) {
        commentService.deleteComment(Id);

        return "success";
    }


    // 해당 글에 대한 전체 댓글 조회
    @RequestMapping("/community/comment/list")
    @ResponseBody
    public List<CommentDto> getCommentList(@RequestParam Long boardNo) {
//    public List<CommentEntity> getCommentList(@RequestParam Long boardNo) {
        List<CommentDto> commentDtoList = commentService.getCommentDtoList(boardNo);

        return commentDtoList;
//        List<CommentEntity> show = commentService.show(boardNo);
//        return show;
    }

//    @RequestMapping("/community/comment/list")
//    @ResponseBody
//    public List<CommentEntity> getList(@RequestParam Long boardNo){
//        List<CommentEntity> show = commentService.show(boardNo);
//        return show;
//    }



    @RequestMapping("/community/comment/checkId")
    public List<CommentDto> getCommentListById(Long Id) {
        List<CommentDto> impList = commentService.getCommentDtoListById(Id);

        return impList;

    }
}