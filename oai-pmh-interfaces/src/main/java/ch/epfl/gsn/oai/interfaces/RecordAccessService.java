package ch.epfl.gsn.oai.interfaces;

import java.util.Date;
import java.util.Set;

/**
 * Created by kryvych on 07/09/15.
 */
public interface RecordAccessService {

    Set<Record> getRecords(Date from, Date to) throws DataAccessException;

    Set<Record> getRecords(Date from, Date to, String resumptionToken) throws DataAccessException;

    Record getRecord(String identifier) throws DataAccessException;

    boolean isValidResumptionToken(String resumptionToken);

    Date getEarliestDatestamp();
}
