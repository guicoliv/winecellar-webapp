package com.cellar.wine.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
public class Fermentation extends BaseEntity implements Comparable<Fermentation> {

    public Fermentation() {
        super();
    }

    public Fermentation(Byte days, Float temperature) {
        super();
        this.days = days;
        this.temperature = temperature;
    }

    @Column(name = "days")
    private Byte days;

    @Column(name = "temperature")
    private Float temperature;

    @OneToMany(mappedBy = "fermentation")
    private List<GrapeComponent> grapes;

    @Override
    public int compareTo(Fermentation m) {
        int result = days.compareTo(m.getDays());

        if (result == 0)
            return temperature.compareTo(m.getTemperature());

        return result;
    }
}
