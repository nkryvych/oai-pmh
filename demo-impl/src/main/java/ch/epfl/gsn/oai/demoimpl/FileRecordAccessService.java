package ch.epfl.gsn.oai.demoimpl;


import ch.epfl.gsn.oai.demoimpl.utils.ReadFileToString;
import ch.epfl.gsn.oai.interfaces.*;
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
public class FileRecordAccessService implements RecordAccessService {

    private final Properties recordConfiguration;

    private final MetadataFormats formats;

    @Inject
    public FileRecordAccessService(Properties recordConfiguration, MetadataFormats formats) {
        this.recordConfiguration = recordConfiguration;
        this.formats = formats;
    }

    @Override
    public Set<Record> getRecords(Date from, Date to) {
        Record record = getRecord("record1");
        return Sets.newHashSet(record);
    }

    @Override
    public Set<Record> getRecords(Date from, Date to, String resumptionToken) {
        return getRecords(from, to);
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
        return null;
    }
}
