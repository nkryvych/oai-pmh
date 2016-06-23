package ch.epfl.osper.oai.interfaces;

import java.util.Set;

/**
 * Created by kryvych on 20/06/16.
 */
public interface RecordWithSets extends Record {

    Set<OaiSet> getSets();

}
