package ch.epfl.osper.oai.demoimpl;


import ch.epfl.osper.oai.demoimpl.utils.ReadFileToString;
import ch.epfl.osper.oai.interfaces.*;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by kryvych on 10/09/15.
 */
@Named
public class FileRecordAccessService implements RecordAccessService<XmlStringRecord> {

    private final Properties recordConfiguration;

    private final MetadataFormats formats;

    @Inject
    public FileRecordAccessService(Properties recordConfiguration, MetadataFormats formats) {
        this.recordConfiguration = recordConfiguration;
        this.formats = formats;
    }

    @Override
    public Set<XmlStringRecord> getRecords(Date from, Date to) {
        XmlStringRecord record = getRecord("record1");
        return Sets.newHashSet(record);
    }

    @Override
    public XmlStringRecord getRecord(String identifier) {
        try {
            Map<String, String> formatsToRecords = Maps.newHashMap();

            for (MetadataFormat metadataFormat : formats.getFormats()) {
                String fileName = new StringBuilder().append(recordConfiguration.getProperty("records.directory"))
                        .append(identifier).append("_").append(metadataFormat.getName()).append(".xml").toString();
                String record = ReadFileToString.readFileFromClasspath(fileName);

                formatsToRecords.put(metadataFormat.getName(), record);
            }
            return new XmlStringRecord(identifier, new Date(), formatsToRecords);
        } catch (IOException e) {
            throw new DataAccessException("Cannot read record ", e);
        }
    }

    @Override
    public boolean isValidResumptionToken(String resumptionToken) {
        return true;
    }

    @Override
    public Date getEarliestDatestamp() {
        return new Date(0);
    }

    @Override
    public boolean isSetSupported() {
        return false;
    }

    @Override
    public Set<XmlStringRecord> getRecords(Date from, Date to, String set, String resumptionToken) throws DataAccessException {
        return getRecords(from, to);
    }

    @Override
    public Set<OaiSet> getSets() throws DataAccessException {
        return Sets.newHashSet();
    }

}
