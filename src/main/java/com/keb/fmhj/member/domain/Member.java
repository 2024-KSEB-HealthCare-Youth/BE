package com.keb.fmhj.member.domain;

import com.keb.fmhj.global.BaseTimeEntity;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.result.domain.Result;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private Integer age;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;

    public void update(UpdateMemberDto requestDto) {
        if (requestDto.getPassword() != null) {
            this.password = requestDto.getPassword();
        }
        if (requestDto.getName() != null) {
            this.name = requestDto.getName();
        }
        if (requestDto.getNickName() != null) {
            this.nickname = requestDto.getNickName();
        }
        if (requestDto.getAge() != null) {
            this.age = requestDto.getAge();
        }
        if (requestDto.getPhoneNumber() != null) {
            this.phoneNumber = requestDto.getPhoneNumber();
        }
        if (requestDto.getEmail() != null) {
            this.email = requestDto.getEmail();
        }
        if (requestDto.getProfileImage() != null) {
            this.profileImage = requestDto.getProfileImage();
        }
    }

    // 이름 변경
    public void changeName(String newName) {
        this.name = newName;
    }

    // 닉네임 변경
    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    // 전화번호 변경
    public void changePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    // 이메일 변경
    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    // 프로필 이미지 변경
    public void changeProfileImage(String newProfileImage) {
        this.profileImage = newProfileImage;
    }
}
