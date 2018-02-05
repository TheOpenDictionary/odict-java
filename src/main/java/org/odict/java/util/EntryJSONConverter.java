package org.odict.java.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.odict.java.models.Entry;
import org.odict.java.models.Group;
import org.odict.java.models.Usage;
import org.odict.java.models.Etymology;

import java.util.ArrayList;
import java.util.List;

public class EntryJSONConverter {
    private ObjectMapper mapper = new ObjectMapper();

    private List<String> getDefinitions(DefinitionsObject obj) {
        List<String> definitions = new ArrayList<>();

        for (int i = 0;  i < obj.definitionsLength(); i++)
            definitions.add(obj.definitions(i));

        return definitions;
    }

    private List<Group> getGroups(org.odict.java.schema.Usage usage) {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < usage.groupsLength(); i++) {
            org.odict.java.schema.Group g = usage.groups(i);

            Group group = new Group();
            String id = g.id().trim();
            String description = g.description().trim();

            if (id.length() > 0) group.setID(id);
            if (description.length() > 0) group.setDescription(description);
            if (g.definitionsLength() > 0) group.setDefinitions(
                this.getDefinitions(new DefinitionsObject(g))
            );

            groups.add(group);
        }

        return groups;
    }

    private List<Usage> getUsages(org.odict.java.schema.Etymology etymology) {
        List<Usage> usages = new ArrayList<>();

        for (int i = 0; i < etymology.usagesLength(); i++) {
            org.odict.java.schema.Usage u = etymology.usages(i);

            Usage usage = new Usage();
            String pos = u.pos().trim();

            if (pos.length() > 0) usage.setPOS(pos);
            if (u.groupsLength() > 0) usage.setGroups(this.getGroups(u));
            if (u.definitionsLength() > 0) usage.setDefinitions(
                this.getDefinitions(new DefinitionsObject(u))
            );

            usages.add(usage);
        }

        return usages;
    }

    private List<Etymology> getEtymologies(org.odict.java.schema.Entry entry) {
        List<Etymology> etymologies = new ArrayList<>();

        for (int i = 0; i < entry.etymologiesLength(); i++) {
            org.odict.java.schema.Etymology ety = entry.etymologies(i);

            Etymology etymology = new Etymology();
            String description = ety.description().trim();

            if (description.length() > 0) etymology.setDescription(description);
            if (ety.usagesLength() > 0) etymology.setUsages(this.getUsages(ety));

            etymologies.add(etymology);
        }

        return etymologies;
    }

    public String convert(org.odict.java.schema.Entry e) throws JsonProcessingException {
        Entry entry = new Entry();
        if (e == null) return "{}";
        else {
            entry.setEtymologies(this.getEtymologies(e));
            return this.mapper.writeValueAsString(entry);
        }
    }

    class DefinitionsObject {
        private org.odict.java.schema.Group group;
        private org.odict.java.schema.Usage usage;

        DefinitionsObject(org.odict.java.schema.Group group) {
            this.group = group;
            this.usage = null;
        }

        DefinitionsObject(org.odict.java.schema.Usage usage) {
            this.usage = usage;
            this.group = null;
        }

        public int definitionsLength() {
            return (this.group != null) ? group.definitionsLength() : usage.definitionsLength();
        }

        public String definitions(int j) {
            return (this.group != null) ? group.definitions(j) : usage.definitions(j);
        }
    }
}
