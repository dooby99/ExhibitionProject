package com.project.BodaProject.dto;


import com.project.BodaProject.domain.board.CommentEntity;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {


    @Autowired
    CommentEntity commentEntity;

    private Long id;
    private Long boardId;
    private String boardTitle;
    private Long writerId;
    private String writerName;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public CommentDto(CommentEntity entity){
        this.id = entity.getId();
        this.boardId = entity.getBoard().getNo();
        this.boardTitle = entity.getBoard().getTitle();
        this.writerId = entity.getWriter().getId();
        this.writerName = entity.getWriter().getName();
        this.content = entity.getContent();
        this.createTime = entity.getCreateTime();
    }
}
