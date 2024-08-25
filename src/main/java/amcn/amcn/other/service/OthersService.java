package amcn.amcn.other.service;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.community.domain.board.Board;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.news.domain.News;
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

    public List<Board> postBoard(Member loginMember) {
        List<Board> boards = othersRepository.findPostBoard(loginMember);
        List<Board> boards_copy = new ArrayList<>();

        return titleSubstanceModify(boards, boards_copy);
    }

    public List<Comment> commentBoard(Member loginMember) {
        List<Comment> comments = othersRepository.findCommentBoard(loginMember);
        List<Comment> comments_copy = new ArrayList<>();

        return commentSubstanceModify(comments, comments_copy);
    }

    public List<Board> likeBoard(Member loginMember) {
        List<Board> boards = new ArrayList<>();
        for (Likes likes : othersRepository.findHeartBoard(loginMember)) {
            Board newBoard = new Board();
            try {
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

            } catch (NullPointerException e) {
                log.info(e.toString());
            }

        }

        for (Board board : boards) {
            log.info(board.getTitle());
        }
        return boards;
    }


    public List<News> postNews(Member loginMember) {
        List<News> news = othersRepository.findPostNews(loginMember);
        List<News> news_copy = new ArrayList<>();

        return newsTitleSubstanceModify(news, news_copy);
    }

    public List<News> newsScrap(Member loginMember) {
        List<News> news=new ArrayList<>();
        for (Likes likes : othersRepository.findNewsScrap(loginMember) ){
            News news2 =new News();
            try {
                news2.setNewsId(likes.getNews().getNewsId());
                news2.setLikes(likes.getNews().getLikes());

                if (likes.getNews().getTitle().length() >= 7) {
                    news2.setTitle(likes.getNews().getTitle().substring(0, 7) + "...");
                } else {
                    news2.setTitle(likes.getNews().getTitle());
                }

                if (likes.getNews().getOriginalContent().length() >= 15) {
                    news2.setOriginalContent(likes.getNews().getOriginalContent().substring(0, 15) + "...");
                } else {
                    news2.setOriginalContent(likes.getNews().getOriginalContent());
                }
                news.add(news2);

            } catch (NullPointerException e) {
                log.info(e.toString());
            }

        }
        return news;
    }

    public List<CardNews> findHeartCardNewsService(Member loginMember) {
        return othersRepository.findHeartCardNews(loginMember);
    }

    public List<CardNews> findForkCardNewsService(Member loginMember) {
        return othersRepository.findForkCardNews(loginMember);
    }


    private List<News> newsTitleSubstanceModify(List<News> news, List<News> news_copy) {
        for (News originalNews : news) {

            News newNews = new News();
            newNews.setNewsId(originalNews.getNewsId());
            newNews.setMember(originalNews.getMember());
            newNews.setLikes(originalNews.getLikes());

            log.info(String.valueOf(newNews.getNewsId()));

            if (originalNews.getTitle().length() >= 8) {
                newNews.setTitle(originalNews.getTitle().substring(0, 8) + "...");
            } else {
                newNews.setTitle(originalNews.getTitle());
            }

            if (originalNews.getOriginalContent().length() >= 15) {
                newNews.setOriginalContent(originalNews.getOriginalContent().substring(0, 15) + "...");
            } else {
                newNews.setOriginalContent(originalNews.getOriginalContent());
            }

            news_copy.add(newNews);
        }
        return news_copy;

    }

    private List<Board> titleSubstanceModify(List<Board> boards, List<Board> boards_copy) {
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

    private List<Comment> commentSubstanceModify(List<Comment> comments, List<Comment> comments_copy) {
        for (Comment comment : comments) {
            Comment newComment = new Comment();
            newComment.setCommentId(comment.getCommentId());
            newComment.setDate(comment.getDate());
            newComment.setMember(comment.getMember());


            if (comment.getSubstance().length() >= 15) {
                newComment.setSubstance(comment.getSubstance().substring(0, 15) + "...");
            } else {
                newComment.setSubstance(comment.getSubstance());
            }

            Board board = comment.getBoard();
            if (comment.getBoard().getTitle().length() >= 7) {
                board.setTitle(board.getTitle().substring(0, 7) + "...");
            } else {
                board.setTitle(board.getTitle());
            }
            newComment.setBoard(board);

            comments_copy.add(newComment);
        }
        return comments_copy;

    }
}
