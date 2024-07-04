package amcn.amcn.other.service;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.community.domain.board.Board;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.other.repository.OthersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OthersService {

    private final OthersRepository othersRepository;

    public List<Board> postBoard(Member loginMember){
        List<Board> boards = othersRepository.findPostBoard(loginMember);
        List<Board> boards_copy = new ArrayList<>();

        return titleSubstnaceModify(boards, boards_copy);
    }

    public List<Comment> commentBoard(Member loginMember){
        List<Comment> comments = othersRepository.findCommentBoard(loginMember);
        List<Comment> comments_copy = new ArrayList<>();

        return commentSubstnaceModify(comments, comments_copy);
    }

    public List<Board> likeBoard(Member loginMember){
        List<Board> boards =new ArrayList<>();
        for (Likes likes : othersRepository.findHeartBoard(loginMember)) {
           Board newBoard=new Board();
            newBoard.setBoardId(likes.getBoard().getBoardId());
            newBoard.setComments(likes.getBoard().getComments());
            newBoard.setMember(likes.getMember());
            newBoard.setLikes(likes.getBoard().getLikes());

            if (likes.getBoard().getTitle().length() >= 7) {
                newBoard.setTitle(likes.getBoard().getTitle().substring(0, 7) + "...");
            } else {
                newBoard.setTitle(likes.getBoard().getTitle());
            }

            if (likes.getBoard().getSubstance().length() >= 15) {
                newBoard.setSubstance(likes.getBoard().getSubstance().substring(0, 15) + "...");
            } else {
                newBoard.setSubstance(likes.getBoard().getSubstance());
            }
            boards.add(newBoard);
        }

        return boards;
    }





    public List<CardNews> findHeartCardNewsService(Member loginMember){
        return othersRepository.findHeartCardNews(loginMember);
    }

    public List<CardNews> findForkCardNewsService(Member loginMember){
        return othersRepository.findForkCardNews(loginMember);
    }

    private List<Board> titleSubstnaceModify(List<Board> boards, List<Board> boards_copy) {
        for (Board board : boards) {
            Board newBoard = new Board();
            newBoard.setBoardId(board.getBoardId());
            newBoard.setComments(board.getComments());
            newBoard.setMember(board.getMember());
            newBoard.setCategory(board.getCategory());
            newBoard.setLikes(board.getLikes());
            newBoard.setCreatedDate(board.getCreatedDate());

            if (board.getTitle().length() >= 7) {
                newBoard.setTitle(board.getTitle().substring(0, 7) + "...");
            } else {
                newBoard.setTitle(board.getTitle());
            }

            if (board.getSubstance().length() >= 15) {
                newBoard.setSubstance(board.getSubstance().substring(0, 15) + "...");
            } else {
                newBoard.setSubstance(board.getSubstance());
            }

            boards_copy.add(newBoard);
        }
        return boards_copy;

    }

    private List<Comment> commentSubstnaceModify(List<Comment> comments, List<Comment> comments_copy) {
        for (Comment comment : comments) {
            Comment newComment=new Comment();
            newComment.setCommentId(comment.getCommentId());
            newComment.setDate(comment.getDate());
            newComment.setMember(comment.getMember());
            newComment.setBoard(comment.getBoard());



            if (comment.getSubstance().length() >= 15) {
                newComment.setSubstance(comment.getSubstance().substring(0, 15) + "...");
            } else {
                newComment.setSubstance(comment.getSubstance());
            }

            comments_copy.add(newComment);
        }
        return comments_copy;

    }
}
