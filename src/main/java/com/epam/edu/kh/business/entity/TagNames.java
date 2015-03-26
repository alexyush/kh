package com.epam.edu.kh.business.entity;

import java.util.ArrayList; 
import java.util.List; 

public class TagNames {

    private List<String> names = new ArrayList<String>();

    public final List<String> getNames() {
        return this.names;
    }

    public final void setNames(final List<String> names) {
        this.names = names;
    }
}
