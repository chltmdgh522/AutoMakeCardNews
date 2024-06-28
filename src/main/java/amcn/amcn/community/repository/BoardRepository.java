package amcn.amcn.community.repository;

import amcn.amcn.community.domain.board.Board;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board){
        em.persist(board);
        return board.getBoardId();
    }

    public void update(Board board){
        Board findBoard = em.find(Board.class, board.getBoardId());
        findBoard.setTitle(board.getTitle());
        findBoard.setSubstance(board.getSubstance());
        findBoard.setCategory(board.getCategory());
    }

    public Optional<Board> findBoardId(Long boardId){
        try {
            Board findBoard = em.createQuery("select b from Board b where b.boardId= :id ", Board.class)
                    .setParameter("id", boardId)
                    .getSingleResult();
            return Optional.of(findBoard);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Board> boardListIt(){
        return em.createQuery("select b from Board b where b.category = 'IT' order by size(b.likes) desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }
    public List<Board> boardListSports(){
        return em.createQuery("select b from Board b where b.category = '스포츠' order by size(b.likes) desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }
    public List<Board> boardListEco(){
        return em.createQuery("select b from Board b where b.category = '경제' order by size(b.likes) desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }
    public List<Board> boardListArt(){
        return em.createQuery("select b from Board b where b.category = '예술' order by size(b.likes) desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }
    public List<Board> boardListScience(){
        return em.createQuery("select b from Board b where b.category = '과학' order by size(b.likes) desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }

    public void delete(Board board){
        em.remove(board);
    }



}
