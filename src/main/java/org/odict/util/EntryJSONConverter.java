package org.odict.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.odict.models.Entry;
import org.odict.models.Etymology;
import org.odict.models.Group;
import org.odict.models.Usage;

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

    private List<Group> getGroups(schema.Usage usage) {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < usage.groupsLength(); i++) {
            schema.Group g = usage.groups(i);

            Group group = new Group();
            long id = g.id();
            String description = g.description().trim();

            group.setID(id);

            if (description.length() > 0) group.setDescription(description);
            if (g.definitionsLength() > 0) group.setDefinitions(
                this.getDefinitions(new DefinitionsObject(g))
            );

            groups.add(group);
        }
        return groups;
    }

    private List<Usage> getUsages(schema.Etymology etymology) {
        List<Usage> usages = new ArrayList<>();

        for (int i = 0; i < etymology.usagesLength(); i++) {
            schema.Usage u = etymology.usages(i);

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

    private List<Etymology> getEtymologies(schema.Entry entry) {
        List<Etymology> etymologies = new ArrayList<>();

        for (int i = 0; i < entry.etymologiesLength(); i++) {
            schema.Etymology ety = entry.etymologies(i);

            Etymology etymology = new Etymology();
            String description = ety.description().trim();

            if (description.length() > 0) etymology.setDescription(description);
            if (ety.usagesLength() > 0) etymology.setUsages(this.getUsages(ety));

            etymologies.add(etymology);
        }

        return etymologies;
    }

    public String convert(schema.Entry e) throws JsonProcessingException {
        Entry entry = new Entry();
        if (e == null) return "{}";
        else {
            entry.setEtymologies(this.getEtymologies(e));
            return this.mapper.writeValueAsString(entry);
        }
    }

    class DefinitionsObject {
        private schema.Group group;
        private schema.Usage usage;

        DefinitionsObject(schema.Group group) {
            this.group = group;
            this.usage = null;
        }

        DefinitionsObject(schema.Usage usage) {
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
