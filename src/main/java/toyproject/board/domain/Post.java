package toyproject.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    private LocalDateTime postDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //== 연관관계 편의 메서드 ==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }
}
