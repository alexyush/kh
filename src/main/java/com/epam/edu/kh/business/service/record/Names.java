package com.epam.edu.kh.business.service.record;

import java.util.HashSet;
import java.util.Set;

public class Names {

    public Names() {

    }

    private Set<String> names = new HashSet<String>();

    public final Set<String> getNames() {
        return this.names;
    }
    public final void setNames(Set<String> names){
        this.names = names;
    }
}
