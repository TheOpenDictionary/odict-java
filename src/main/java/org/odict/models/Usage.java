package org.odict.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usage {
   private String pos;
   private List<Group> groups;
   private List<String> definitions;

    public String getPOS() {
        return pos;
    }

    public void setPOS(String pos) {
        this.pos = pos;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }
}
