package amcn.amcn.comment.repository;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.community.domain.board.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }
    public List<Comment> findCommentBoardId(Long boardId){
        return em.createQuery("select c from Comment c where c.board.boardId= :id order by c.commentId desc ", Comment.class)
                .setParameter("id", boardId)
                .getResultList();
    }

    public Optional<Comment> findComment(Long id){
        Comment findComment = em.find(Comment.class, id);
        return Optional.ofNullable(findComment);
    }

    public Integer commentSize(Board board){
        return em.createQuery("select b from Board b where b.boardId = :id ", Comment.class)
                .setParameter("id",board.getBoardId())
                .getResultList().size();
    }

    public void delete(Comment comment){
        em.remove(comment);
    }
}
