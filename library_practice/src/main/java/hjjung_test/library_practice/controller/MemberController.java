package hjjung_test.library_practice.controller;

import hjjung_test.library_practice.domain.Member;
import hjjung_test.library_practice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    // 스프링 컨테이너가 뜰 때 Autowired 가 있다면, 스프링 컨테이너에서 memberService를 가져옴
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    // main build 시 에러 : ~'hjjung_test.library_practice.service.MemberService' that could not be found.
    // 위 MemberService 를 어디서 가져오나? 알 수 없음...
    // MemberService 를 annotation 해줘야 함 (없다면, 스프링이 알 수가 없음...) @Service
    // 마찬가지로, MemberService 에서 사용하는 MemoryMemberRepository 또한 @Repository 어노테이션을 걸어줘야 함
    // 또한, MemberService 도 Autowired 로 자동 연결시켜줘야 컨테이너가 알 수 있음

    // @Service, @Repository, @Controller 안에는 모두 @Component 가 등록되어 있음
    // @SpringBootApplication 안에 @ComponentScan 이 있음
    // 해당 annotation 아래에 있는 모든 package 를 확인하여 @Component 인 애들을 모두 확인하여 스프링 컨테이너에 등록.
    // 스프링 빈을 등록할 때는 기본적으로 싱글톤으로 등록하도록 되어있음

    @GetMapping("/members/new")
    public String createForm() {
        return "/members/createForm";
    }

    // Post 방식 통신
    // html 내 form 태그에 의해 전달됨. action 과 method 중요.
    // 해당 form 태그 내 값 전달. 여기서 중요한 건 'name' 값
    // 현재 매핑된 인자값(여기서 MemberForm 클래스) 내 같은 name 값을 가지고 있다면 매핑함
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        System.out.println("TEST : name : " + form.getName());

        Member member = new Member();
        member.setName(form.getName());

        memberService.Join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("members", memberList);

        return "members/memberList";
    }
}
