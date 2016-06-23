package ch.epfl.osper.oai.interfaces;

import java.util.Date;
import java.util.Set;

/**
 * Created by kryvych on 07/09/15.
 */
public interface Record {

    String getOAIIdentifier();

    Date getDateStamp();

    boolean isDeleted();

    Set<OaiSet> getSets();
}
