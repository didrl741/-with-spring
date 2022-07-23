package toyproject.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import toyproject.board.domain.Post;
import toyproject.board.domain.PostSearch;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    // 검색 기능
    public List<Post> findAllByString(PostSearch postSearch) {

        String jpql = "select p From Post p join p.user u";
        boolean isFirstCondition = true;

        // 회원 이름 검색
        if (StringUtils.hasText(postSearch.getUserName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " u.name like :name";
        }

        TypedQuery<Post> query = em.createQuery(jpql, Post.class)
                .setMaxResults(100);    // 최대 100건

        if (StringUtils.hasText(postSearch.getUserName())) {
            query = query.setParameter("name", postSearch.getUserName());
        }

        return query.getResultList();
    }

    // 취소 기능
    public void cancelOne(Long postId) {
        Post post = findOne(postId);
        em.remove(post);
    }
}
