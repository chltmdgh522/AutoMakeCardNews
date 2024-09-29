package amcn.amcn.socket.reposiotry;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.socket.domain.AdminMessage;
import amcn.amcn.socket.domain.ListMessage;
import amcn.amcn.socket.domain.UserMessage;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public void saveAdminMessage(String content, Member adminMember, Member member2) {
        AdminMessage adminMessage = new AdminMessage();
        adminMessage.setContent(content);
        adminMessage.setTimestamp(LocalDateTime.now());
        adminMessage.setMember(adminMember);
        adminMessage.setMember2(member2);

        em.persist(adminMessage);
    }

    public List<ListMessage> findAllMessage(Member member) {
        List<UserMessage> userMessages = em.createQuery("select l from UserMessage l where l.member.memberId=:id",
                        UserMessage.class)
                .setParameter("id", member.getMemberId())
                .getResultList();

        log.info(member.getMemberId());
        List<AdminMessage> adminMessages = em.createQuery("select l from AdminMessage l where l.member2.memberId=:id", AdminMessage.class)
                .setParameter("id", member.getMemberId())
                .getResultList();

        // 메시지를 담을 리스트
        List<ListMessage> combinedMessages = new ArrayList<>();

        // UserMessage를 ListMessage로 변환하여 combinedMessages에 추가
        for (UserMessage userMessage : userMessages) {
            ListMessage listMessage = new ListMessage();
            listMessage.setType("user");
            listMessage.setMessage(userMessage.getContent());
            listMessage.setTimestamp(userMessage.getTimestamp());
            listMessage.setUserMessageId(userMessage.getUserMessageId());

            combinedMessages.add(listMessage);
        }

        // AdminMessage를 ListMessage로 변환하여 combinedMessages에 추가
        for (AdminMessage adminMessage : adminMessages) {
            ListMessage listMessage = new ListMessage();
            listMessage.setType("admin");
            listMessage.setMessage(adminMessage.getContent());
            listMessage.setTimestamp(adminMessage.getTimestamp());
            combinedMessages.add(listMessage);
        }

        // timestamp 기준으로 정렬
        combinedMessages.sort(Comparator.comparing(ListMessage::getTimestamp));

        return combinedMessages;

    }

    public void findConfirm(List<String> messages) {
        for (String id : messages) {
            em.createQuery("update UserMessage m set m.confirm=true where m.userMessageId = :id ")
                    .setParameter("id",id)
                    .executeUpdate();

        }
    }


    public List<Member> findAllMembers() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }


}
