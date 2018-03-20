package org.odict.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entry {
   private List<Etymology> etymologies;

    public List<Etymology> getEtymologies() {
        return etymologies;
    }

    public void setEtymologies(List<Etymology> etymologies) {
        this.etymologies = etymologies;
    }
}
