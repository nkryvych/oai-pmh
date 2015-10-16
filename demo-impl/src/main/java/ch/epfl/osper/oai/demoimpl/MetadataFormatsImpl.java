package ch.epfl.osper.oai.demoimpl;

import ch.epfl.osper.oai.interfaces.MetadataFormat;
import ch.epfl.osper.oai.interfaces.MetadataFormats;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

/**
 * Created by kryvych on 18/09/15.
 */
@Named
public class MetadataFormatsImpl extends MetadataFormats {

    private final Set<MetadataFormat> formats;

    @Inject
    public MetadataFormatsImpl(Set<MetadataFormat> formats) {
        this.formats = formats;
    }


    @Override
    public Set<MetadataFormat> getFormats() {
        return formats;
    }
}
