package com.kati.core.domain.advertisement.domain;

import com.kati.core.domain.food.domain.Food;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"food"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ad_food")
@Entity
public class AdvertisementFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(name = "impression_count")
    private Long impression = 0L;

    public AdvertisementFood(Food food) {
        this.food = food;
    }

    public AdvertisementFood impressionUp() {
        this.impression++;
        this.food.viewCountUp();
        return this;
    }

}
