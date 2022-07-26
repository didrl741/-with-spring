package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        post.setLoveCount(0);

        postService.join(post);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String postList(Model model) {

        List<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);

        return "post/postList";
    }

    @PostMapping("/items/{postId}/cancel")
    public String cancelPost(@PathVariable("postId") Long postId) {
        postService.cancelPost(postId);
        return "redirect:/items";
    }

    @GetMapping("/items/{postId}")
    public String showPost(@PathVariable("postId") Long postId, Model model) {

        model.addAttribute("post", postService.findOne(postId));
        return "post/showPost";
    }
}
