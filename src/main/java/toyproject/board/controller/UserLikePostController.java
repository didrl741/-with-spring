package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.board.domain.UserLikePost;
import toyproject.board.service.PostService;
import toyproject.board.service.UserLikePostService;
import toyproject.board.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserLikePostController {
    private final UserLikePostService userLikePostService;
    private final UserService userService;
    private final PostService postService;

    // 좋아요 눌렀을 시 호출
    @PostMapping("/items/{postId}/like")
    public String likePost(@PathVariable("postId") Long postId, HttpSession session) {

        UserLikePost userLikePost = new UserLikePost();
        userLikePost.setPost( postService.findOne(postId) );

        String logInedUserName = (String)session.getAttribute("loginedUserName");
        userLikePost.setUser(userService.findByName(logInedUserName).get(0));

        userLikePostService.join(userLikePost);

        return "redirect:/";
    }
}
