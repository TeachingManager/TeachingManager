package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fee {
    @Id
    @Column(name="fee_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fee_id;

    @Column(name = "totalMonthFee", nullable = false)
    private long totalMonthFee = 0;

    @Column(name = "payedMonthFee", nullable = false)
    private long payedMonthFee = 0;

    @Column(name = "year", nullable = false)
    private Short year;

    @Column(name = "month", nullable = false)
    private Short month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;


    @Builder
    public Fee(Institute institute, long totalMonthFee,Short year, Short month) {
        this.year = year;
        this.month = month;
        this.institute = institute;
        this.totalMonthFee = totalMonthFee;
    }

    public void increasePayedMonthFee(long paid_amount) {
        this.payedMonthFee += paid_amount;
    }

    public void increaseTotalMonthFee(long paid_amount) {
        this.totalMonthFee += paid_amount;
    }
}
