package com.keb.fmhj.result.domain;

import com.keb.fmhj.global.BaseTimeEntity;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.resultItem.domain.ResultItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "result")
public class Result extends BaseTimeEntity {

    @Id
    @Column(name = "result_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "result_advanced_skin_type", joinColumns = @JoinColumn(name = "result_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "advanced_skin_type")
    private List<AdvancedSkinType> advancedSkinType;

    @Column
    @Enumerated(EnumType.STRING)
    private BasicSkinType basicSkinType;

    @Column(nullable = false)
    private String resultImage;

    @Column(nullable = false)
    private String faceImage;

    @Column
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "result", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultItem> resultItems;
}
