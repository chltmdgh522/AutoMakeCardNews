package amcn.amcn.member.webapi.login;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.service.login.LoginService;
import amcn.amcn.member.web.session.SessionConst;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Tag(name = "Login", description = "로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")  // 모든 요청에 /api를 붙임
public class LoginControllerV2 {
    private final LoginService loginService;

    @Operation(summary = "로그인 처리", description = "로그인 인증을 수행합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@Validated @RequestBody Member member, BindingResult bindingResult,
                                       HttpServletRequest request) throws Exception {

        Optional<Member> findLoginMember = loginService.loginIdCheck(member);
        if (findLoginMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("아이디가 존재하지 않습니다");
        }

        Member passwordMember = loginService.passwordCheck(member);
        if (passwordMember == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("비밀번호가 일치하지 않습니다");
        }

        // 로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, passwordMember);

        return ResponseEntity.ok().body("로그인 성공");
    }
}
