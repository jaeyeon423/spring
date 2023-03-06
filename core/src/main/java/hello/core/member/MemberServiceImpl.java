package hello.core.member;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void joinMember(Member member) {
            memberRepository.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }
}
