package com.example.plus.entity;

import com.example.plus.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(PostRequestDto postRequestDto, User user){
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

}
