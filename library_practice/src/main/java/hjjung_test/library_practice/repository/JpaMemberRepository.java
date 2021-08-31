package hjjung_test.library_practice.repository;

import hjjung_test.library_practice.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{
    private final EntityManager em;
    // JPA는 모두 EntityManager를 주입받아야 이용할 수 있다

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 아래 쿼리 문에서 from MEMBER 또는 from member 등으로 쓰면 안되더라...
        // 테이블명과 클래스명을 동일시하여 처리하는 듯?
        // JPQL 이라는 SQL과 비슷한 JPA의 언어를 사용하여 전달해야 한다고 함
        List<Member> resultList = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return resultList.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
//        List<Member> result = em.createQuery("select m from member m", Member.class).getResultList();
//        return result;

        // 위 문장에서 Ctrl + Alt + N 하면 inline 화 가능
        // 또는 Ctrl + Alt + Shift + T 하여 inline 검색 후 엔터
        return em.createQuery("select m from Member m", Member.class).getResultList();

        // >> JPQL 언어 - 객체를 대상으로(Entity를 대상으로) 쿼리를 날림 : SQL로 번역됨
        // select m 을 함 : 엔티티, 즉 객체 자체를 조회함
    }
}
