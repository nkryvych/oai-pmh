package ch.epfl.osper.oai.rest.verbs;

import ch.epfl.osper.oai.interfaces.*;
import ch.epfl.osper.oai.rest.ErrorOai;
import ch.epfl.osper.oai.rest.OaiRecordRequestParameters;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by kryvych on 07/09/15.
 */
@Named
@Scope("prototype")
public class GetRecord<T extends Record>{

    private final MetadataFormats metadataFormats;

    private final TemplateHelper templateHelper;

    private final RecordAccessService recordAccessService;

    private final ConverterFactory converterFactory;

    @Inject
    public GetRecord(MetadataFormats metadataFormats, TemplateHelper templateHelper, RecordAccessService recordAccessService, ConverterFactory converterFactory) {
        this.metadataFormats = metadataFormats;
        this.templateHelper = templateHelper;
        this.recordAccessService = recordAccessService;
        this.converterFactory = converterFactory;
    }

    public String getResponse(OaiRecordRequestParameters parameters){
        if(!metadataFormats.isSupportedFormat(parameters.getMetadataPrefix())) {
            return ErrorOai.CANNOT_DISSEMINATE_FORMAT.generateMessage(templateHelper, this.getClass().getSimpleName(), parameters.getParametersString());
        }


        Record record = recordAccessService.getRecord(parameters.getIdentifier());

        MetadataFormat metadataFormat = metadataFormats.getByName(parameters.getMetadataPrefix());
        Converter converter = converterFactory.getConverter(metadataFormat.getClass());

        if(record == null || !converter.canConvertRecord(record)) {
            return ErrorOai.ID_DOES_NOT_EXIST.generateMessage(templateHelper, this.getClass().getSimpleName(), parameters.getParametersString());
        }

        return formatContent(record, converter, parameters.getParametersString());
    }

    protected String formatContent(Record record, Converter converter, String parameters) {


        final String metadata = converter.convert(record);
        String verbContent =  templateHelper.formatRecord(record, metadata);

        return templateHelper.fillTopTemplate(this.getClass().getSimpleName(), verbContent, parameters);
    }


}
