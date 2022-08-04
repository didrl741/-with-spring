package toyproject.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.board.domain.Files;

import javax.persistence.EntityManager;

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
}
