package amcn.amcn.config;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.MemberType;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    public final MemberRepository memberRepository;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // 서버 시작 시 데이터베이스에 초기값을 삽입합니다.
            initializeMembers();
        };
    }

    @Transactional
    public void initializeMembers() {
        // 데이터베이스 초기화를 위한 코드 작성
        Member member1 = new Member(
                "9999",
                "chltmdgh517",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "chltmdgh517@naver.com",
                LocalDate.of(2001, 7, 2),
                LocalDate.of(2022, 7, 2),
                "basic2.png",
                RoleType.MASTER,
                MemberType.남자,
                "최승호"
        );

        // 데이터베이스 초기화를 위한 코드 작성
        Member member2 = new Member(
                "9999",
                "chltmdgh517",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "chltmdgh517@naver.com",
                LocalDate.of(2001, 7, 2),
                LocalDate.of(2022, 7, 2),
                "basic2.png",
                RoleType.MASTER,
                MemberType.남자,
                "최승호"
        );

        // 데이터베이스 초기화를 위한 코드 작성
        Member member3 = new Member(
                "9999",
                "chltmdgh517",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "chltmdgh517@naver.com",
                LocalDate.of(2001, 7, 2),
                LocalDate.of(2022, 7, 2),
                "basic2.png",
                RoleType.MASTER,
                MemberType.남자,
                "최승호"
        );

        // 회원 정보 저장
        memberRepository.save(member1);

    }

}
