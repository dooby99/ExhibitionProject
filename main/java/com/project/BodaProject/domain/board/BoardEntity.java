package com.project.BodaProject.domain.board;

import com.project.BodaProject.domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@Entity
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_no", nullable = false)
    private UserEntity name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createT;

    private int views;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder
    public
    BoardEntity(Long no, String title, String content, UserEntity writer, LocalDateTime createT) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.name = writer;
        this.createT = createT;
    }

}
