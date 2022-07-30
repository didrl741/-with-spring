package toyproject.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.board.domain.UserLikePost;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserLikePostRepository {

    private final EntityManager em;

    public void save(UserLikePost userLikePost) {
        em.persist(userLikePost);
    }

    public UserLikePost findOne(Long likeId) {
        return em.find(UserLikePost.class, likeId);
    }
}
