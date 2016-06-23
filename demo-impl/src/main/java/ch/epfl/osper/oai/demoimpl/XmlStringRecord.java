package ch.epfl.osper.oai.demoimpl;


import ch.epfl.osper.oai.interfaces.OaiSet;
import ch.epfl.osper.oai.interfaces.Record;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by kryvych on 09/09/15.
 */
public class XmlStringRecord implements Record {

    private final Map<String, String> formatToRecord;

    private final String identifier;

    private final Date datestamp;

    public XmlStringRecord(String identifier, Date datestamp, Map<String, String> formatToRecord) {
        this.formatToRecord = formatToRecord;
        this.identifier = identifier;
        this.datestamp = datestamp;
    }

    public String getRecord(String format) {
        return formatToRecord.get(format);
    }

    @Override
    public String getOAIIdentifier() {
        return identifier;
    }

    @Override
    public Date getDateStamp() {
        return datestamp;
    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public Set<OaiSet> getSets() {
        return null;
    }
}
