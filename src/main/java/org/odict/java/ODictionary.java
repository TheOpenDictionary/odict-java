package org.odict.java;

import org.odict.java.schema.Dictionary;
import org.odict.java.util.EntryJSONConverter;

public class ODictionary {
    private Dictionary dictionary;
    private short version;

    public ODictionary(short version, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.version = version;
    }

    /**
     * Returns the entry for a given word in JSON
     *
     * @param word
     * @return String
     */
    public String getEntryAsJSON(String word) {
        return new EntryJSONConverter().convert(this.dictionary.entriesByKey(word));
    }

    /**
     * Returns a boolean denoting whether the dictionary contains an entry for a given word
     *
     * @param word
     * @return boolean
     */
    public boolean hasEntry(String word) {
        return (this.dictionary.entriesByKey(word) != null);
    }

    /**
     * Returns the major version number of the dictionary
     *
     * @return short
     */
    public short getVersion() {
        return version;
    }
}
