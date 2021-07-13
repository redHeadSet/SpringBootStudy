package hjjung_test.library_practice.service;

import hjjung_test.library_practice.domain.Member;
import hjjung_test.library_practice.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // 테스트는 함수명을 한글로 적어도 됨!
    @Test
    void 회원가입() {
        // 1. given : 어떤 상황이 주어졌을 때
        Member member = new Member();
        member.setName("spring1");

        // 2. when : 이 코드가 실행되면
        Long saveId = memberService.Join(member);

        // 3. then : 이렇게 되어야 함
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        // when
//        memberService.Join(member1);
//        try {
//            memberService.Join(member2);
//            fail(); // 에러가 나야 정상인데, 안나면 실패
//        } catch (IllegalStateException e) {
//            System.out.println("실패했당");
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
//        }

        // 위 코드와 동일한 내용이지만 더 간단 (람다식 공부해야 할듯...)
        memberService.Join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.Join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}