package hjjung_test.library_practice.service;

import hjjung_test.library_practice.domain.Member;
import hjjung_test.library_practice.repository.MemberRepository;
import hjjung_test.library_practice.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 실제 스프링부트가 뜨면서 테스트 진행
@Transactional  // 트랜젝션 테스트 진행 (테스트 종료 후 롤백 처리하여 진행됨) -> BeforeEach, AfterEach 없어도 됨
                // @Commit 어노테이션 추가하면 커밋하면서 진행됨
// cf. 실제 테스트는 Java 단위 테스트 (스프링부트 통합 테스트가 아닌) 에서 디테일하게 테스트하는 게 더 좋다고 한다
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 테스트는 함수명을 한글로 적어도 됨!
    @Test
    // @Commit 커밋 넣은 경우, 처리 후 commit 추가하여 처리함
    public void 회원가입() throws Exception{
        // 1. given : 어떤 상황이 주어졌을 때
        Member member = new Member();
        member.setName("spring1");

        // 2. when : 이 코드가 실행되면
        Long saveId = memberService.Join(member);

        // 3. then : 이렇게 되어야 함
        Member findMember = memberRepository.findById(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        // 위 코드와 동일한 내용이지만 더 간단 (람다식 공부해야 할듯...)
        memberService.Join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.Join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

        // then
    }
}