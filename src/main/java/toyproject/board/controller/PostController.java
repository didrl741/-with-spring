package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toyproject.board.domain.Post;
import toyproject.board.domain.User;
import toyproject.board.service.PostService;
import toyproject.board.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/items/new")
    public String postForm(Model model) {

        model.addAttribute("postForm", new PostForm());

        return "post/createPostForm";
    }


    @PostMapping("/items/new")
    public String create(@Valid PostForm postForm, BindingResult result, HttpSession session) {

        if (postForm.getTitle().length() == 0 || postForm.getContent().length() == 0) {
            return "post/createPostForm";
        }

        List<User> users = userService.findByName((String)session.getAttribute("loginedUserName"));
        User user = users.get(0);

        log.info(user.getId().toString());

        Post post = new Post();

        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        post.setPostDate(LocalDateTime.now());
        post.setUser(user);

        postService.join(post);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String postList(Model model) {

        List<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);

        return "post/postList";
    }

    // 삭제하기 눌렀을 때
    @PostMapping("/items/{postId}/cancel")
    public String cancelPost(@PathVariable("postId") Long postId) {
        postService.cancelPost(postId);
        return "redirect:/items";
    }

    // 글 조회 (동기식)
    @GetMapping("/items/{postId}")
    public String showPost(@PathVariable("postId") Long postId, Model model, HttpSession session) {

        model.addAttribute("post", postService.findOne(postId));

        // 로그인아이디 유저가 현재 post에 좋아요 했는지 체크하는 변수 생성해서 model에 전달
        List<User> users = userService.findByName((String)session.getAttribute("loginedUserName"));
        User user = users.get(0);

        Long userId = user.getId();

        boolean checkLiked = userService.checkLiked(userId, postId);

        model.addAttribute("checkLiked", checkLiked);

        return "post/showPost";
    }

    // 글 조회(비동기식)
    @GetMapping("/items/ajax/{postId}")
    public String showPostByAjax(@PathVariable("postId") Long postId, Model model, HttpSession session) {

        model.addAttribute("post", postService.findOne(postId));

        List<User> users = userService.findByName((String)session.getAttribute("loginedUserName"));
        User user = users.get(0);

        Long userId = user.getId();

        boolean checkLiked = userService.checkLiked(userId, postId);

        model.addAttribute("checkLiked", checkLiked);

        return "post/showPostByAjax";
    }

}
