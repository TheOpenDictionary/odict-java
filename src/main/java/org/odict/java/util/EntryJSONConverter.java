package org.odict.java.util;

import org.odict.java.schema.Entry;
import org.odict.java.schema.Etymology;
import org.odict.java.schema.Group;
import org.odict.java.schema.Usage;

import java.util.ArrayList;
import java.util.List;

public class EntryJSONConverter {

    private String addDefinitions(DefinitionsObject obj) {
        String output = "\"definitions\":[";
        int length = obj.definitionsLength();

        for (int i = 0;  i < length; i++) {
            output += "\"" + obj.definitions(i) + "\"";
            if (i != length - 1) output += ",";
        }

        output += "]";

        return output;
    }

    private String addGroups(Usage usage) {
        String output = "\"groups\":[";
        int length = usage.groupsLength();
        List<String> properties = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Group group = usage.groups(i);
            String id = group.id().trim();
            String description = group.description().trim();

            output += "{";

            if (id.length() > 0)
                properties.add(String.format("\"id\":\"%s\"", id));

            if (description.length() > 0)
                properties.add(String.format("\"description\":\"%s\"", description));

            if (group.definitionsLength() > 0)
                properties.add(this.addDefinitions(new DefinitionsObject(group)));

            output += String.join(",", properties);
            output += "}";

            properties.clear();

            if (i != length - 1) output += ",";
        }

        output += "]";

        return output;
    }

    private String addUsages(Etymology etymology) {
        String output = "\"usages\":[";
        int length = etymology.usagesLength();
        List<String> properties = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Usage usage = etymology.usages(i);
            String pos = usage.pos().trim();

            output += "{";

            if (pos.length() > 0)
                properties.add(String.format("\"pos\":\"%s\"", pos));

            if (usage.groupsLength() > 0)
                properties.add(this.addGroups(usage));

            if (usage.definitionsLength() > 0)
                properties.add(this.addDefinitions(new DefinitionsObject(usage)));

            output += String.join(",", properties);
            output += "}";

            properties.clear();

            if (i != length - 1) output += ",";
        }

        output += "]";

        return output;
    }

    private String addEtymologies(Entry entry) {
        String output = "\"etymologies\":[";
        int length = entry.etymologiesLength();

        for (int i = 0; i < length; i++) {
            Etymology etymology = entry.etymologies(i);
            String description = etymology.description().trim();

            output += "{";

            if (description.length() > 0)
                output += String.format("\"description\":\"%s\",", description);

            if (etymology.usagesLength() > 0)
                output += this.addUsages(etymology);

            output += "}";

            if (i != length - 1) output += ",";
        }

        output += "]";

        return output;
    }

    public String convert(Entry entry) {
        if (entry == null) return "{}";
        else return "{" + this.addEtymologies(entry) + "}";
    }

    class DefinitionsObject {
        private Group group;
        private Usage usage;

        DefinitionsObject(Group group) {
            this.group = group;
            this.usage = null;
        }

        DefinitionsObject(Usage usage) {
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
