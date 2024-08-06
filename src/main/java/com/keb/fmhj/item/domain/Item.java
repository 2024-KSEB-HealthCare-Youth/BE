package com.keb.fmhj.item.domain;

import com.keb.fmhj.resultItem.domain.ResultItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recommend_item")
public class Item {

    @Id
    @Column(name = "recommend_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String itemImage;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultItem> resultItems;
}
