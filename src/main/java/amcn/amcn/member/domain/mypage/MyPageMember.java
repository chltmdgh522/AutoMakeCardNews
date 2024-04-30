package amcn.amcn.member.domain.mypage;

import amcn.amcn.member.domain.member.EmailType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MyPageMember {
    @NotEmpty
    @Size(min=2, max = 4, message = "최소 2글자에서 최대 4글자 사이여야 됩니다.")
    private String name;

    private String email;

    private String emailF;

    private EmailType emailType;


    private MultipartFile profile;

}
