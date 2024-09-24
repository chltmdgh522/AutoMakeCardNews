package amcn.amcn.socket.reposiotry;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.socket.domain.AdminMessage;
import amcn.amcn.socket.domain.UserMessage;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
@Slf4j
@Transactional
public class ChatRepository {

    private final EntityManager em;
    public void saveMemberMessage(String content, Member member) {
        UserMessage memberMessage = new UserMessage();
        memberMessage.setContent(content);
        memberMessage.setTimestamp(LocalDateTime.now());
        memberMessage.setMember(member);

        em.persist(memberMessage);
    }

    public void saveAdminMessage(String content, Member adminMember) {
        AdminMessage adminMessage = new AdminMessage();
        adminMessage.setContent(content);
        adminMessage.setTimestamp(LocalDateTime.now());
        adminMessage.setMember(adminMember);

        em.persist(adminMessage);
    }

}
