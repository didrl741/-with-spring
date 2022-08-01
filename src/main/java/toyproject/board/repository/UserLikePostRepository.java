package toyproject.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.board.domain.Post;
import toyproject.board.domain.User;
import toyproject.board.domain.UserLikePost;

import javax.persistence.EntityManager;
import java.util.List;

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

    // post, user를 인자로 받아서 this를 반환하는 함수

//    public List<UserLikePost> findByUser(Long userId) {
//        List<UserLikePost> result = em.createQuery("select l from UserLikePost l where l.user = :userId", UserLikePost.class)
//                .setParameter("userId", userId)
//                .getResultList();
//        return result;
//    }

    public void deleteOne(Long likeId) {
        UserLikePost userLikePost = findOne(likeId);
        em.remove(userLikePost);
    }

}
