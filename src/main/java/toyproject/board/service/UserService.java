package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.User;
import toyproject.board.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    public void validateDuplicateUser(User user) {
        List<User> users = userRepository.findByName(user.getUserEmail());
        if (!users.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다")
        }
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }
}
