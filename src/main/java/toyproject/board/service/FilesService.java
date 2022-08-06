package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Files;
import toyproject.board.repository.FilesRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FilesService {

    private final FilesRepository filesRepository;

    @Transactional
    public Long join(Files files) {
        filesRepository.save(files);
        return files.getId();
    }

    public Files findOne(Long filesId) {
        return filesRepository.findOne(filesId);
    }

    public List<Files> findAll() {
        return filesRepository.findAll();
    }
}
