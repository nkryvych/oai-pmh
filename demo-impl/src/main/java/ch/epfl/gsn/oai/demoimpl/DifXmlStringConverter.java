package ch.epfl.gsn.oai.demoimpl;

import ch.epfl.gsn.oai.interfaces.Converter;
import ch.epfl.gsn.oai.interfaces.MetadataFormat;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;

/**
 * Created by kryvych on 10/09/15.
 */
@Named
public class DifXmlStringConverter implements Converter<XmlStringRecord> {

    public static final String FORMAT_NAME = "dif";

    @Override
    public String convert(XmlStringRecord record) {
        return record.getRecord(FORMAT_NAME);
    }

    @Override
    public <M extends MetadataFormat> boolean isForFormat(Class<M> formatClass) {
        if (DifFormat.class.equals(formatClass)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canConvertRecord(XmlStringRecord record) {
        return StringUtils.isNotEmpty(record.getRecord(FORMAT_NAME));
    }


}
