package amcn.amcn.mail;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverMailIdService {
    private final MemberRepository memberRepository;

    private final JavaMailSender emailsender;

    private String ePw; // 인증번호

    // 메일 내용 작성


    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException, MessagingException {
//		System.out.println("보내는 대상 : " + to);
//		System.out.println("인증 번호 : " + ePw);

        MimeMessage message = emailsender.createMimeMessage();
        log.info("=asdfasdfasdf==================");
        message.addRecipients(MimeMessage.RecipientType.TO, to);// 보내는 대상
        message.setSubject("AMCN 아이디 찾기");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 카드뉴스 자동제작 AMCN 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드는 당신의 아이디 입니다.<p>";
        msgg += "<br>";
        msgg += "<p>멋진 카드뉴스를 만들어주세요. 감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>아이디 입니다.</h3>";
        msgg += "<div style='font-size:130%; color:red;'>";
        msgg += "ID : <strong>";
        msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("chltmdgh522@naver.com", "AMCN"));// 보내는 사람

        return message;
    }

    // 랜덤 인증 코드 전송

    public String createKey(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member);
        if(findMember.isPresent()){
            Member member1 = findMember.get();
            ePw=member1.getLoginId();
        }
        return ePw;
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
    public String sendSimpleMessage(Member member) throws Exception {

        ePw = createKey(member); // 랜덤 인증번호 생성

        // TODO Auto-generated method stub
        MimeMessage message = createMessage(member.getEmail()); // 메일 발송
        try {// 예외처리
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }


        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }

}
