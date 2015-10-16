package ch.epfl.osper.oai.demoimpl;


import ch.epfl.osper.oai.interfaces.Converter;
import ch.epfl.osper.oai.interfaces.ConverterFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

/**
 * Created by kryvych on 10/09/15.
 */
@Named
public class ConverterFactoryImpl extends ConverterFactory {

    private Set<Converter> converters;

    @Inject
    public ConverterFactoryImpl(Set<Converter> converters) {
       this.converters = converters;
    }


    @Override
    public Set<Converter> getConverters() {
        return converters;
    }
}
