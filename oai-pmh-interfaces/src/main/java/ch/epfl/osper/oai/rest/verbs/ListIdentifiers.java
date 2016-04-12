package ch.epfl.osper.oai.rest.verbs;

import ch.epfl.osper.oai.interfaces.MetadataFormats;
import ch.epfl.osper.oai.interfaces.Record;
import ch.epfl.osper.oai.interfaces.RecordAccessService;
import ch.epfl.osper.oai.rest.OaiListRequestParameters;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

/**
 * Created by kryvych on 07/09/15.
 */
@Named
@Scope("prototype")
public class ListIdentifiers extends ListVerb {


    @Inject
    public ListIdentifiers(MetadataFormats metadataFormats, TemplateHelper templateHelper, RecordAccessService recordAccessService) {
        super(templateHelper, recordAccessService, metadataFormats);
    }


    @Override
    protected String formatContent(Set<Record> records, OaiListRequestParameters parameters) {

        StringBuilder headers = new StringBuilder();
        for (Record record : records) {
            headers.append(templateHelper.formatHeader(record)).append("\n");
        }

        String verbContent = headers.toString();

        return templateHelper.fillTopTemplate(this.getClass().getSimpleName(), verbContent, parameters.getParametersString());
    }

}
