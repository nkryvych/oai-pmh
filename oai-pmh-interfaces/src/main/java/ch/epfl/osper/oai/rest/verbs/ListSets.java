package ch.epfl.osper.oai.rest.verbs;

import ch.epfl.osper.oai.interfaces.OaiSet;
import ch.epfl.osper.oai.interfaces.RecordAccessService;
import ch.epfl.osper.oai.rest.ErrorOai;
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
public class ListSets {

    private final TemplateHelper templateHelper;
    private final RecordAccessService recordAccessService;


    @Inject
    public ListSets(TemplateHelper templateHelper, RecordAccessService recordAccessService) {
        this.templateHelper = templateHelper;
        this.recordAccessService = recordAccessService;
    }

    public String getResponse(OaiListRequestParameters parameters) {
        if (!recordAccessService.isSetSupported()) {
            return ErrorOai.NO_SET_HIERARCHY.generateMessage(templateHelper, "ListSets", parameters.getParametersString());
        }

        Set<OaiSet> sets = recordAccessService.getSets();
        StringBuilder xmlSets = new StringBuilder();
        for (OaiSet set : sets) {
            xmlSets.append(templateHelper.formatSet(set));
        }

        return templateHelper.fillTopTemplate("ListSets", xmlSets.toString(), parameters.getParametersString());

    }
}

