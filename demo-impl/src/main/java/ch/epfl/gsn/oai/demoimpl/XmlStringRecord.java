package ch.epfl.gsn.oai.demoimpl;


import ch.epfl.gsn.oai.interfaces.Record;

import java.util.Date;
import java.util.Map;

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
}
