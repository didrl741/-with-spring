package toyproject.board.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.User;
import toyproject.board.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입() {
        //given
        User user = new User();
        user.setUserName("song");
        user.setUserEmail("didrl741@naver.com");

        //when
        Long saveId = userService.join(user);

        //then
        Assertions.assertEquals(user, userRepository.findOne(saveId));
    }

    @Test
    public void 중복이메일() throws Exception {
        //given
        User user1 = new User();
        user1.setUserEmail("didrl741@naver.com");
        userService.join(user1);

        //when
        User user2 = new User();
        user2.setUserEmail("didrl741@naver.com");

        //then
        assertThrows(IllegalStateException.class, () -> {
            userService.join(user2);
        });
    }
}