package com.cellar.wine.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "barrel_component")
public class BarrelComponent extends BaseEntity implements Comparable<BarrelComponent> {

    @Builder
    public BarrelComponent(Long id, Byte percentage, Integer size, Integer aging,
                           GrapeComponent grapeComponent, Barrel barrel) {
        super(id);
        this.percentage = percentage;
        this.size = size;
        this.aging = aging;
        this.grapeComponent = grapeComponent;
        this.barrel = barrel;
    }

    @Column(name = "percentage")
    private Byte percentage;

    @Column(name = "size")
    private Integer size;

    @Column(name = "aging")
    private Integer aging;

    @ManyToOne
    @JoinColumn(name = "grape_component_id", referencedColumnName = "id")
    private GrapeComponent grapeComponent;

    @ManyToOne
    @JoinColumn(name = "barrel_id", referencedColumnName = "id")
    private Barrel barrel;

    @Override
    public int compareTo(BarrelComponent bc) {
        int result = percentage.compareTo(bc.getPercentage());

        if (result == 0)
            return barrel.getName().compareTo(bc.getBarrel().getName());

        return result;
    }
}