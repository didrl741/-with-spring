package toyproject.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.board.domain.Files;
import toyproject.board.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilesRepository {

    private final EntityManager em;

    public void save(Files files) {
        em.persist(files);
    }

    public Files findOne(Long filesId) {
        return em.find(Files.class, filesId);
    }

    public List<Files> findAll() {
        List<Files> result = em.createQuery("select f from Files f", Files.class)
                .getResultList();
        return result;
    }
}
