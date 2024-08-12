package com.keb.fmhj.member.domain;

import com.keb.fmhj.global.BaseTimeEntity;
import com.keb.fmhj.result.domain.Result;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column
    private String nickName;

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

    @Column
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private IsAdmin isAdmin = IsAdmin.USER;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;

    public void update(String name, String nickName, String email, String phoneNumber, String profileImage) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }
}