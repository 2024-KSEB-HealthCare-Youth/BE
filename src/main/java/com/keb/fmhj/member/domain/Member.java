package com.keb.fmhj.member.domain;

import com.keb.fmhj.global.BaseTimeEntity;
import com.keb.fmhj.result.domain.Result;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
