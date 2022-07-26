package toyproject.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Like {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //== 연관관계 편의 메서드 ==//
    public void setUser(User user) {
        this.user = user;
        user.getLikes().add(this);
    }

    //== 연관관계 편의 메서드 ==//
    public void setPost(Post post) {
        this.post = post;
        post.getLikes().add(this);
    }
}
