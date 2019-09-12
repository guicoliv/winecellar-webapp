package com.cellar.wine.ui;

import com.cellar.wine.models.Barrel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarrelUIFactory implements FactoryUI<Barrel, BarrelUI> {

    private BarrelUIFactory() {
    }

    public static FactoryUI<Barrel, BarrelUI> instance() {
        return new BarrelUIFactory();
    }

    public List<BarrelUI> createList(List<Barrel> barrels) {
        List<BarrelUI> result = new ArrayList<>();
        if (barrels != null) {
            for (Barrel b : barrels) {
                result.add(create(b));
            }
            Collections.sort(result, new BarrelUISorter());
        }
        return result;
    }

    public BarrelUI create(Barrel b) {
        if (b == null) {
            return null;
        }

        return new BarrelUI(b);
    }
}