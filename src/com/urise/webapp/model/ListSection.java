package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> sections;

    public ListSection() {
        sections = new ArrayList<>();
    }

    public ListSection(List<String> list) {
        this.sections = list;
    }

    public void addSection(String text) {
        sections.add(text);
    }


    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder("");
        for (String text : sections) {
            listToString.append(text + "\n");
        }
        return listToString.toString();
    }
}
