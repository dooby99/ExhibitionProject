package com.project.BodaProject.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.BodaProject.domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "comments")
@Slf4j
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity writer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    private BoardEntity board;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "content")
    private String content;

    @Builder
    public CommentEntity(Long id, UserEntity writer, BoardEntity board, String content, LocalDateTime createTime){
        this.id=id;
        this.board=board;
        this.writer=writer;
        this.content=content;
        this.createTime=createTime;

    }
}
