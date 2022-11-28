package com.urise.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> sections;

    public ListSection(List<String> list) {
        this.sections = list;
    }

    @Override
    public String toString() {
        StringBuilder listToString= new StringBuilder("");
        for (String text: sections){
            listToString.append(text +"\n");
        }
        return listToString.toString();
    }
}
