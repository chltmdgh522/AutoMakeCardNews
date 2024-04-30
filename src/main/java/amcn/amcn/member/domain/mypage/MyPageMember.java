package amcn.amcn.member.domain.mypage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MyPageMember {
    @NotEmpty
    @Size(min=2, max = 4, message = "최소 2글자에서 최대 4글자 사이여야 됩니다.")
    String name;

    @Email(message = "이메일 형식이 아닙니다.")
    String email;


    MultipartFile profile;

}
