package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.board.domain.Post;
import toyproject.board.domain.User;
import toyproject.board.domain.UserLikePost;
import toyproject.board.service.PostService;
import toyproject.board.service.UserLikePostService;
import toyproject.board.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AjaxController {

    private final PostService postService;
    private final UserService userService;
    private final UserLikePostService userLikePostService;


    @GetMapping("/getAgeByName")
    /* inputName 파라미터를 받아, 미리 저장된 ageMap에서 해당 이름에 맵핑된 나이를 리턴해주는 메소드 */
    public Map<String,Object> getAgeByName(@RequestParam String inputName ) {
        Map<String, Integer> ageMap = new HashMap<>();
        ageMap.put("tom", 10); ageMap.put("bob", 20); ageMap.put("kim", 30);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("name", inputName);
        returnMap.put("age", ageMap.get(inputName));

        return returnMap;
    }


    @PostMapping("/postAgeByName")
    /* inputMap 파라미터를 받아, 미리 저장된 ageMap에서 해당 이름에 맵핑된 나이를 리턴해주는 메소드 */
    public Map<String,Object> postAgeByName(@RequestBody Map<String,Object> inputMap ) {
        Map<String, Integer> ageMap = new HashMap<>();
        ageMap.put("tom", 10); ageMap.put("bob", 20); ageMap.put("kim", 30);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("name", inputMap.get("name"));
        returnMap.put("age", ageMap.get(inputMap.get("name")));
        log.info("name", inputMap.get("name"));

        return returnMap;
    }

    // 좋아요 눌렀을 때
    @PostMapping("/items/{postId}/likeByAjax")
    /* inputMap 파라미터를 받아, 미리 저장된 ageMap에서 해당 이름에 맵핑된 나이를 리턴해주는 메소드 */
    public Map<String,Object> postAgeByName2(@PathVariable("postId") Long postId, HttpSession session) {

        Post post = postService.findOne(postId);

        UserLikePost userLikePost = new UserLikePost();
        userLikePost.setPost( post );

        String logInedUserName = (String)session.getAttribute("loginedUserName");
        userLikePost.setUser(userService.findByName(logInedUserName).get(0));

        userLikePostService.join(userLikePost);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("count", post.getUserLikePosts().size());


        return returnMap;
    }



    // 좋아요와 취소 둘다 구현눌렀을 때
    @PostMapping("/items/{postId}/likeAndHateByAjax")
    /* inputMap 파라미터를 받아, 미리 저장된 ageMap에서 해당 이름에 맵핑된 나이를 리턴해주는 메소드 */
    public Map<String,Object> likeOrHate(@PathVariable("postId") Long postId, HttpSession session) {

        String logInedUserName = (String)session.getAttribute("loginedUserName");
        Post post = postService.findOne(postId);

        // 리팩토링 필요..
        Long logInedUserId = userService.findByName(logInedUserName).get(0).getId();

        boolean checkLiked = userService.checkLiked(logInedUserId, postId);

        Map<String,Object> returnMap = new HashMap<>();

        if (checkLiked == true) {
            // 취소기능
            UserLikePost userLikePost = userService.findLiked(logInedUserId, postId);

            userLikePostService.deleteLIke(userLikePost.getId());

            returnMap.put("check", "canceled");

        } else {
            UserLikePost userLikePost = new UserLikePost();
            userLikePost.setPost( post );

            userLikePost.setUser(userService.findByName(logInedUserName).get(0));

            userLikePostService.join(userLikePost);
            returnMap.put("check", "liked");
        }


        returnMap.put("count", post.getUserLikePosts().size());

        return returnMap;
    }
}
