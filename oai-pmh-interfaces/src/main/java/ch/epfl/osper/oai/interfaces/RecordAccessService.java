package ch.epfl.osper.oai.interfaces;

import java.util.Date;
import java.util.Set;

/**
 * Created by kryvych on 07/09/15.
 */
public interface RecordAccessService <T extends Record> {

    Set<T> getRecords(Date from, Date to) throws DataAccessException;

    Set<T> getRecords(Date from, Date to, String set, String resumptionToken) throws DataAccessException;

    T getRecord(String identifier) throws DataAccessException;

    boolean isValidResumptionToken(String resumptionToken);

    Date getEarliestDatestamp();

    boolean isSetSupported();

    Set<OaiSet> getSets() throws DataAccessException;

}
