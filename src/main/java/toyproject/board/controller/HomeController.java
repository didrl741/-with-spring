package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toyproject.board.domain.Post;
import toyproject.board.domain.User;
import toyproject.board.service.PostService;
import toyproject.board.service.UserService;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final PostService postService;

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "index";
    }

    @GetMapping("/make")
    public String make() {

        User user = new User();
        user.setUserName("didrl");
        user.setUserPassword("didrl");
        userService.join(user);

        User user2 = new User();
        user2.setUserName("12");
        user2.setUserPassword("12");
        userService.join(user2);

        Post post = new Post();
        post.setPostDate(LocalDateTime.now());
        post.setTitle("제목입니다");
        post.setContent("내용입니다");
        post.setUser(user);

        postService.join(post);

        return "index";

    }


}
