package io.jus.hopegaarden.domain.define.member;

import io.jus.hopegaarden.domain.define.BaseEntity;
import io.jus.hopegaarden.domain.define.member.constant.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Builder
    public Member(String nickname, String password, String email, MemberRole role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // 사용자가 ADMIN인지 체크
    public boolean isAdmin() {
        return this.role.isAdministrator();
    }

    // 기본 사용자 생성
    public static Member createDefaultRole(final String email,
                                           final String password,
                                           final String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(MemberRole.MEMBER)
                .build();
    }

    // Spring Security UserDetails Area
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
