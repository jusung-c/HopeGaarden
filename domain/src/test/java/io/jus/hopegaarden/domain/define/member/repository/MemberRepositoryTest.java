package io.jus.hopegaarden.domain.define.member.repository;

import io.jus.hopegaarden.domain.define.member.Member;
import io.jus.hopegaarden.domain.util.JdbcTestHelper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static io.jus.hopegaarden.domain.fixture.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SuppressWarnings("NonAsciiCharacters")
class MemberRepositoryTest extends JdbcTestHelper {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = 일반_유저_생성();
        memberRepository.save(member);
    }

    @Nested
    @DisplayName("멤버를 찾는다")
    class FindMember {

        @Test
        void 아이디_값으로_멤버를_찾는다() {
            // when
            Optional<Member> result = memberRepository.findById(member.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }

        @Test
        void 닉네임_값으로_멤버를_찾는다() {
            // when
            Optional<Member> result = memberRepository.findByNickname(member.getNickname());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }

        @Test
        void 이메일_값으로_멤버를_찾는다() {
            // when
            Optional<Member> result = memberRepository.findByEmail(member.getEmail());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }
    }
}