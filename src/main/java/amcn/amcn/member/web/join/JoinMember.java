package amcn.amcn.member.web.join;

import amcn.amcn.member.domain.member.MemberType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDate;

@Data
public class JoinMember {

    private String loginId;
    private String passwordCheck;
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType memberSex;
    @CreatedDate
    private LocalDate createdDate;
}
