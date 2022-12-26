package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private List<String> content;

    public ListSection() {
    }
    public ListSection(List<String> content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public ListSection(String... content) {
        this(Arrays.asList(content));
    }

    public List<String> getContent() {
        return content;
    }

    public void addSection(String text) {
        if (content == null){
            content = new ArrayList<>();
        }
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
        ListSection section = (ListSection) o;
        return content.equals(section.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
