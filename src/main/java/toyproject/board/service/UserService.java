package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.controller.UserForm;
import toyproject.board.domain.User;
import toyproject.board.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> users = userRepository.findByEmail(user.getUserEmail());
        if (!users.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다");
        }
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    // 로그인 가능 체크
    public Map<String, Object> checkLoginAvailable(UserForm userForm) {
        Map<String, Object> rs = new HashMap<String, Object>();

        List<User> users = userRepository.findByName(userForm.getName());

        if (users.isEmpty()) {
            rs.put("resultCode", "F-1");
            rs.put("msg", "해당 회원이 존재하지 않습니다.");
            log.info("해당 회원이 존재하지 않습니다");
        } else if (users.get(0).getUserPassword().equals(userForm.getPassword()) == false ) {
            rs.put("resultCode", "F-2");
            rs.put("msg", "비밀번호가 일치하지 않습니다.");
            log.info("비밀번호가 일치하지 않습니다.");
        } else {
            rs.put("resultCode", "S-1");
            rs.put("msg", "로그인에 성공했습니다.");
            log.info("로그인 성공");
        }

        return rs;
    }
}
