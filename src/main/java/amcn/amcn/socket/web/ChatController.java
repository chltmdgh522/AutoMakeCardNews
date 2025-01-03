package amcn.amcn.socket.web;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.socket.domain.ListMessage;
import amcn.amcn.socket.domain.dto.ChatMessage;
import amcn.amcn.socket.reposiotry.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate; // SimpMessagingTemplate 사용


    @GetMapping("/chat")
    public String getChat(Model model,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                          Member loginMember) {

        if (loginMember.getRoleType().equals(RoleType.MASTER)) {
            return "redirect:/";
        }
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
            model.addAttribute("messages", chatRepository.findAllMessage(member));
            model.addAttribute("userId", member.getMemberId());


        } else {
            return null;
        }
        return "chat/userChat";
    }

    @ResponseBody
    @PostMapping("/userchat")
    public String postChat(@RequestParam("user_content") String userContent,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                           Member loginMember) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            chatRepository.saveMemberMessage(userContent, member);

        } else {
            return null;
        }
        return "성공";
    }


    @GetMapping("/superchat")
    public String getSuperChat(Model model,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member loginMember) {
        if (!loginMember.getRoleType().equals(RoleType.MASTER)) {
            return "redirect:/";
        }
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
            model.addAttribute("users", chatRepository.findAllMembers());
        } else {
            return null;
        }
        return "chat/listChat";
    }

    @GetMapping("/superchat/{memberId}")
    public String getSuperChat2(@PathVariable String memberId, Model model,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                Member loginMember) {

        if (!loginMember.getRoleType().equals(RoleType.MASTER)) {
            return "redirect:/";
        }
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();

            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);

            Optional<Member> findMember2 = memberRepository.findMemberId(memberId);
            if (findMember2.isPresent()) {
                Member member1 = findMember2.get();

                model.addAttribute("messages", chatRepository.findAllMessage(member1));
                model.addAttribute("mId", memberId);
                model.addAttribute("userMember", member1);
            } else {
                return null;
            }
        } else {
            return null;
        }
        return "chat/masterChat";
    }

    @ResponseBody
    @PostMapping("/superchat/{memberId}")
    public String postSuperChat(@RequestParam("user_content") String userContent,
                                @PathVariable String memberId,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                Member loginMember) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            Optional<Member> findMember2 = memberRepository.findMemberId(memberId);
            if (findMember2.isPresent()) {
                Member member2 = findMember2.get();
                chatRepository.saveAdminMessage(userContent, member, member2);

            } else {
                return null;
            }

        } else {
            return null;
        }
        return "성공";
    }


    //읽었는지 안읽었는 검사하는것들
    @ResponseBody
    @PostMapping("/check/user")
    public String checkMessageIdUser(@RequestBody Map<String, List<String>> payload) {
        List<String> messageIds = payload.get("messageIds");

        chatRepository.findConfirmUser(messageIds);

        return "ok";
    }

    @ResponseBody
    @PostMapping("/check/admin")
    public String checkMessageIdsAdmin(@RequestBody Map<String, List<String>> payload) {
        List<String> messageIds = payload.get("messageIds");

        chatRepository.findConfirmAdmin(messageIds);

        return "ok";
    }


    // 사용자 메시지 전송 본격적인 웹 소켓
    @MessageMapping("/chat.userToAdmin")
    public void userToAdmin(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/admin/", chatMessage);
    }

    // 관리자 메시지 전송
    @MessageMapping("/chat.adminToUser")
    public void adminToUser(@Payload ChatMessage chatMessage) {
        log.info(chatMessage.getContent());
        log.info(chatMessage.getMemberId());
        messagingTemplate.convertAndSend("/topic/user/" + chatMessage.getMemberId(), chatMessage);
    }


}
