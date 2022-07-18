package toyproject.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Post;
import toyproject.board.repository.PostRepository;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;

    @Test
    @Rollback(value = false)
    public void 게시글등록() throws Exception {
        //given
        Post post = new Post();
        post.setTitle("title!!");

        //when
        postService.join(post);

        //then

    }
}
