package amcn.amcn.other.repository;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.community.domain.board.Board;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OthersRepository {

    private final EntityManager em;


    //game 포인트
    public List<Member> memberPointList(){
        return em.createQuery("select m from Member m order by m.point desc", Member.class)
                .setMaxResults(5)
                .getResultList();
    }


    public List<Board> findPostBoard(Member loginMember){
        return em.createQuery("select b from Board b where b.member.memberId = :id order by b.boardId desc", Board.class)
                .setParameter("id",loginMember.getMemberId())
                .getResultList();
    }

    public List<Comment> findCommentBoard(Member loginMember){
        return em.createQuery("select c from Comment c where c.member.memberId = :id order by c.commentId desc", Comment.class)
                .setParameter("id",loginMember.getMemberId())
                .getResultList();
    }

    public List<Likes> findHeartBoard(Member loginMember){
        return em.createQuery("select l from Likes l where l.member.memberId = :id order by l.likeId desc", Likes.class)
                .setParameter("id",loginMember.getMemberId())
                .getResultList();
    }



    public List<CardNews> findHeartCardNews(Member loginMember){
        List<Likes> findLikes = em.createQuery("select l from Likes l where l.member.memberId= :memberId", Likes.class)
                .setParameter("memberId", loginMember.getMemberId())
                .getResultList();

        List<CardNews> list=new ArrayList<>();
        for (Likes findLike : findLikes) {
            try {
                if (findLike.getCardNews() != null) {
                    CardNews cardId = em.createQuery("select c from CardNews c where c.cardNewsId = :cardId and " +
                                    "c.trash = 'X'", CardNews.class)
                            .setParameter("cardId", findLike.getCardNews().getCardNewsId())
                            .getSingleResult();
                    list.add(cardId);
                }else{
                    log.info("널이라고?");
                }
            } catch (NoResultException e) {

                log.info(String.valueOf(e));
            }
        }

        return list;
    }

    public List<CardNews> findForkCardNews(Member loginMember){
        return em.createQuery("select c from CardNews c where c.fork != 0 and c.member.memberId = :memberId", CardNews.class)
                .setParameter("memberId", loginMember.getMemberId())
                .getResultList();

    }
}
