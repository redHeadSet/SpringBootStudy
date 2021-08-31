package hjjung_test.library_practice.repository;

import hjjung_test.library_practice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// SpringDataJpa가 해당 JpaRepository 를 상속하는 경우, 구현체를 알아서 Bean에 등록함
// 프록시를 사용하여 알아서 등록함
// JpaRepository 내에 기본적인 함수가 설정되어 있음 (save, findById, findAll 등..)
// 단, findByName 은 만들어져 있지 않기 때문에 직접 처리 :
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{

    // JSQL : select m from Member from m.name = ? 방식으로 처리됨
    // 해당 select 문이 함수명을 보고 알아서 처리함
    @Override
    Optional<Member> findByName(String name);
    // Optional<Member> findByNameAndId(String name, int id); >> 같은 걸로 인터페이스 이름만 적는 걸로도 개발이 끝남...!
}
