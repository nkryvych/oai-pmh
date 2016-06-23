package ch.epfl.osper.oai.interfaces;

import java.util.Date;
import java.util.Set;

/**
 * Created by kryvych on 20/06/16.
 */
public interface RecordWithSetAccessService<T extends Record> extends RecordAccessService<T>{

    Set<T> getRecords(Date from, Date to, String set, String resumptionToken) throws DataAccessException;

    Set<OaiSet> getSets() throws DataAccessException;

    Set<OaiSet> getSetsForRecord(Record record) throws DataAccessException;




}
