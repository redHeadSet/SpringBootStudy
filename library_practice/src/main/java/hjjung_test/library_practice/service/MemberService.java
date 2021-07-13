package hjjung_test.library_practice.service;

import hjjung_test.library_practice.domain.Member;
import hjjung_test.library_practice.repository.MemberRepository;
import hjjung_test.library_practice.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// ** cf. service 쪽 패키지 함수명은 비지니스 로직이랑 비슷하게 이름을 지어야 함
// 그래야 기획 쪽과 얘기할 때 매칭하기 쉬움
// 반대로, respository 등은 좀 더 개발자스럽게(?)_기계적으로 네이밍

public class MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 회원 가입
    public Long Join(Member member){
//        Ctrl + Alt + Shift + T 입력 시 리펙토링과 관련된 함수가 생성됨
        // 함수화 관련 단축키 Ctrl + Alt + M
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 같은 이름을 가진 멤버 추가 불가
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다");
//        });
//        아래와 같은 코드지만 읽기가 좋음
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long MemberId) {
        return memberRepository.findById(MemberId);
    }
}
