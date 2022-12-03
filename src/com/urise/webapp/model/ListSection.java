package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> content;

    public ListSection() {
        content = new ArrayList<>();
    }

    public ListSection(List<String> list) {
        this.content = list;
    }

    public void addSection(String text) {
        content.add(text);
    }


    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder("");
        for (String text : content) {
            listToString.append("+" + text + "\n");
        }
        return listToString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
