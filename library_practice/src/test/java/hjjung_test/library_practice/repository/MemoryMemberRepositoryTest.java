package hjjung_test.library_practice.repository;

import hjjung_test.library_practice.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// ** 각 테스트는 서로 의존하지 않아야 한다!
public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 각 테스트의 순서가 보장되지 않음
    // repository 는 1개이기 때문에 각 테스트 진행 중 repository 안에 데이터가 혼용될 수 있음
    // AfterEach 는 각 테스트 이후 동작하는 call back 메서드
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();

        System.out.println("[save] result = " + (result == member));
//        org.junit.jupiter.api 가 제공하는...
//        Assertions.assertEquals(result, member);

//        org.assertj.core.api 가 제공하는...
//        Assertions를 import static 으로 설정하여 바로 사용 (7 line)
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member member3 = new Member();
        member3.setName("spring3");
        repository.save(member3);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(3);
    }
}
