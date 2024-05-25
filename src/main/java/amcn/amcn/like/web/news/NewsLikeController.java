package amcn.amcn.like.web.news;

import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.like.repository.LikeRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.news.domain.News;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NewsLikeController {
    private final LikeRepository likeRepository;

    @PostMapping("/news-like")
    @ResponseBody
    public int newLike(@RequestParam("newsId") Long id,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                       Member loginMember) {

        Likes likes = new Likes();
        News news = new News();
        news.setNewsId(id);
        likes.setMember(loginMember);
        likes.setNews(news);

        // 좋아요가 있는지 확인
        String correct = likeRepository.findByNewsLike(likes);
        if (correct.equals("O")) {
            //좋아요가 없으면 좋아요 저장
            likeRepository.newsSave(likes);
            return likeRepository.findByBookmarkNewsLike(likes).size();
        } else {
            likeRepository.newsRemove(likes);
            return likeRepository.findByBookmarkNewsLike(likes).size();
        }


    }
}
