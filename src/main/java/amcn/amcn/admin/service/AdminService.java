package amcn.amcn.admin.service;

import amcn.amcn.admin.repository.member.AdminMemberRepository;
import amcn.amcn.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final AdminMemberRepository adminMemberRepository;

    //회원 페이징
    public Page<Member> getList(String name, int page) {

        Pageable pageable = PageRequest.of(page, 10);

        if (name.isEmpty()) {
            return adminMemberRepository.findAll(pageable);
        }

        return adminMemberRepository.findByNameContaining(name,pageable);
    }



}
