package ch.epfl.osper.oai.interfaces;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by kryvych on 20/06/16.
 */
public class OaiSet {

    private String spec;

    private String name;

    private String description;

    public OaiSet(String spec, String name, String description) {
        this.spec = spec;
        this.name = name;
        this.description = description;
    }

    public String getSpec() {
        return spec;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasDescription() {
        return StringUtils.isNotEmpty(getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OaiSet oaiSet = (OaiSet) o;

        if (spec != null ? !spec.equals(oaiSet.spec) : oaiSet.spec != null) return false;
        return !(name != null ? !name.equals(oaiSet.name) : oaiSet.name != null);

    }

    @Override
    public int hashCode() {
        int result = spec != null ? spec.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
