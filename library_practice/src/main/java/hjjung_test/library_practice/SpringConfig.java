package hjjung_test.library_practice;

import hjjung_test.library_practice.repository.*;
import hjjung_test.library_practice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

// DI 설정 방법 : [생성자 주입] or [필드 주입] or [Setter 주입]
// [생성자 주입] - ** 추천 ** 생성자에서 인자를 받아 DI 설정 : 처음 한 번 설정하고 변경 불가. 실제로 중간에 변경되는 경우 별로 없음
// [필드 주입] - 클래스 내부 변수 등으로 설정 : 추천하지 않음
// [Setter 주입] - Set 함수를 사용하여 생성 : set 함수가 public 으로 공개되어 있음 (실수 가능)

// Configuration 으로 하는 이유?
// 상황에 따라 구현 클래스를 변경해야 한다면? : @Service, @Repository 등을 설정해두면 매 번 코드를 변경해야 함
// ex. DB가 변경된다던가...
// 구현체만 변경된다면? Config 설정 파일만 변경하면 됨.
@Configuration
public class SpringConfig {
//    private final DataSource dataSource;
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}