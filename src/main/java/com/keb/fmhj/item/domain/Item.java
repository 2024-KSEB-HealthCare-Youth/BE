package com.keb.fmhj.item.domain;

import com.keb.fmhj.resultItem.domain.ResultItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
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
    private String ingredients;

    @Column
    private String brand;

    @Column(nullable = false)
    private Long price;

    @Column
    private String link;

    @Column
    private String details;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultItem> resultItems;
}
