package amcn.amcn.community.service;

import amcn.amcn.community.domain.board.Board;
import amcn.amcn.community.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> titleContentALLSub() {
        List<Board> boards = boardRepository.boardListALLmore();
        List<Board> boards_copy = new ArrayList<>();

        for (Board board : boards) {
            Board newBoard = new Board();
            newBoard.setBoardId(board.getBoardId());
            newBoard.setComments(board.getComments());
            newBoard.setMember(board.getMember());
            newBoard.setCategory(board.getCategory());
            newBoard.setLikes(board.getLikes());
            newBoard.setCreatedDate(board.getCreatedDate());

            if (board.getTitle().length() >= 7 ) {
                newBoard.setTitle(board.getTitle().substring(0,7) + "...");
            }else{
                newBoard.setTitle(board.getTitle());
            }

            if (board.getSubstance().length() >= 15 ) {
                newBoard.setSubstance(board.getSubstance().substring(0,15) + "...");
            }else{
                newBoard.setSubstance(board.getSubstance());
            }

            boards_copy.add(newBoard);
        }

        return boards_copy;
    }
}
