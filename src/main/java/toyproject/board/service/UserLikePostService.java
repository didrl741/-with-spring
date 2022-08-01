package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Post;
import toyproject.board.domain.UserLikePost;
import toyproject.board.repository.PostRepository;
import toyproject.board.repository.UserLikePostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLikePostService {

    private final UserLikePostRepository userLikePostRepository;

    @Transactional
    public Long join(UserLikePost userLikePost) {
        userLikePostRepository.save(userLikePost);
        return userLikePost.getId();
    }



    public UserLikePost findOne(Long likeId) {
        return userLikePostRepository.findOne(likeId);
    }

    @Transactional
    public void deleteLIke(Long likeId) {
        userLikePostRepository.deleteOne(likeId);
    }
}
