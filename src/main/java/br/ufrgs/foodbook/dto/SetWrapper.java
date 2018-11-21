package br.ufrgs.foodbook.dto;

import java.util.Set;

public class SetWrapper<T> {
    private Set<T> set;

    public SetWrapper(Set<T> set) {
        this.set = set;
    }

    public Set<T> getSet() {
        return set;
    }

    public void setSet(Set<T> set) {
        this.set = set;
    }
}
