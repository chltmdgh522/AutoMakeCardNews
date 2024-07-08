package amcn.amcn.oauth;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

//    타 블로그 글을 보면 OAuth2UserService를 상속 받거나 직접 구현 하는 경우가 있는데
//    DefaultOAuth2UserService는 구현체이기에 이대로 진행 해도무관하다

    //DB 저장을 진행 하기 위해 유저 래퍼지토리 주입
    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        //부모 클래스 loadUser로 부터 유저 정보를 가지고 오는 메서드 ( OAuth2 공급업체로 부터 사용자 정보를 가져오는 것 )     ;
        log.info(oAuth2User.getAttributes().toString());
        String O_email="";
        String O_name="";
        String O_id="";

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Object responseObj = oAuth2User.getAttributes().get("response");
        if (responseObj instanceof Map) {
            Map<String, Object> responseMap = (Map<String, Object>) responseObj;
            Object emailObj = responseMap.get("email");
            Object nameObj = responseMap.get("name");
            Object idObj = responseMap.get("id");

            if (emailObj != null && nameObj !=null && idObj !=null) {
                O_email = emailObj.toString();
                O_name=nameObj.toString();
                O_id=idObj.toString();
            } else {
                log.error("Email not found in response");
            }
        } else {
            log.error("Invalid response object type");
        }

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {


//            log.info("gdgdgdgdg");
//            log.info(oAuth2User.getAttributes().get("email").toString());
//            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
//            log.info(oAuth2Response.getEmail());
//            log.info(oAuth2Response.getProviderId());

        } else {
            return null;
        }

// 구글과 네이버 서비스마다 인증 규격이 상이하기 때문에 서로 다른 DTO로 담아야 한다.
// 따라서 OAuth2 DTO 객체 격인 OAuth2Response 객체를 인터페이스로 만든다.
// 네이버로 인터페이스를 구현, 구글 타입으로 인터페이스를 구현하는 식으로 진행한다.

        String username = O_id;

        log.info(username);
        Member member = new Member();
        member.setEmail(O_email);
        Optional<Member> existData = memberRepository.findByEmail(member);


        String role = null;

        if (existData.isEmpty()) {

            Member member1 = new Member();
            member1.setName(O_name);
            member1.setMemberId(O_id);
            member1.setEmail(O_email);
            member1.setRoleType(RoleType.O_USER);
            log.info("gkdl");
            memberRepository.save(member1);
        } else {

            log.info("zzzzzzz");
            existData.get().setEmail(O_email);
        }

        NaverResponse naverResponse=new NaverResponse();

        naverResponse.setId(O_id);
        naverResponse.setName(O_name);
        naverResponse.setEmail(O_email);
        role="User";

        //로그인 성공
        success(naverResponse);

        return new CustomOAuth2User(naverResponse, role);
    }
    private static void success(NaverResponse naverResponse) {
        // 현재 요청 가져오기
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            HttpSession session = request.getSession();

            Member member = new Member();
            member.setMemberId(naverResponse.getId());
            member.setName(naverResponse.getName());
            member.setEmail(naverResponse.getEmail());
            member.setRoleType(RoleType.O_USER);

            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        } else {
            log.error("Failed to get current HttpServletRequest");
        }
    }
}