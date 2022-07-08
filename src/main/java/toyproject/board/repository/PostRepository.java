package toyproject.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.board.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOne(Long postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> findAll() {
        List<Post> result = em.createQuery("select p from Post p", Post.class)
                .getResultList();
        return result;
    }
}
