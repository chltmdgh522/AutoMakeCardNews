package amcn.amcn.socket.web;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.socket.domain.dto.ChatMessage;
import amcn.amcn.socket.reposiotry.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate; // SimpMessagingTemplate 사용

    @MessageMapping("/chat")
    public void handleChatMessage(ChatMessage chatMessage) {
        log.info("Received message: {}", chatMessage.getContent());
        log.info("Sender: {}", chatMessage.getSender());
        log.info("Login member set in session: {}", chatMessage.getMemberId());

        Optional<Member> findMember = memberRepository.findMemberId(chatMessage.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();

            // chatMessage 객체에서 sender 정보를 확인하여 관리자와 사용자 구분
            if ("admin".equals(chatMessage.getSenderType())) {
                chatRepository.saveAdminMessage(chatMessage.getContent(), member);
            } else {
                chatRepository.saveMemberMessage(chatMessage.getContent(), member);
            }

            // "master" 타입의 사용자에게만 메시지 전송
            RoleType roleType = member.getRoleType();
            String name = roleType.name();
            if ("MASTER".equals(name)) {
                messagingTemplate.convertAndSend("/topic/master-messages", chatMessage);
            }
        } else {
            log.warn("Member not found for ID: {}", chatMessage.getMemberId());
        }
    }
}
