package com.epam.edu.kh.business.entity;

import java.util.HashSet;
import java.util.Set;

public class TagNames {

    private Set<String> names = new HashSet<String>();

    public final Set<String> getNames() {
        return this.names;
    }

    public final void setNames(final Set<String> names) {
        this.names = names;
    }
}
