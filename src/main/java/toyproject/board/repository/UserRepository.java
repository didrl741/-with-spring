package toyproject.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.board.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long userId) {
        return em.find(User.class, userId);
    }

    public List<User> findAll() {
        List<User> result = em.createQuery("select u from User u", User.class)
                .getResultList();
        return result;
    }

    public List<User> findByName(String name) {
        List<User> result = em.createQuery("select u from User u where u.userName = :name", User.class)
                .getResultList();
        return result;
    }
}
