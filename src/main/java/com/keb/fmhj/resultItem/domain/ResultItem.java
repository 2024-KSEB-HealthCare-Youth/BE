package com.keb.fmhj.resultItem.domain;

import com.keb.fmhj.item.domain.Item;
import com.keb.fmhj.result.domain.Result;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "result_item")
public class ResultItem {

    @Id
    @Column(name = "result_item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
