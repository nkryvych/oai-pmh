package ch.epfl.osper.oai.rest.verbs;

import ch.epfl.osper.oai.interfaces.DataAccessException;
import ch.epfl.osper.oai.interfaces.OaiSet;
import ch.epfl.osper.oai.interfaces.Record;
import ch.epfl.osper.oai.interfaces.RepositoryIdentity;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Created by kryvych on 08/09/15.
 */
@Named
@Scope()
public class TemplateHelper {

    private final Properties templateConfiguration;

    private final TemplateHandler templateHandler;

    private final RepositoryIdentity identity;

    @Inject
    public TemplateHelper(Properties templateConfiguration, TemplateHandler templateHandler, RepositoryIdentity identity) {
        this.templateConfiguration = templateConfiguration;
        this.templateHandler = templateHandler;
        this.identity = identity;
    }

    public Map<String, String> getCommonParameters() {
        Map<String, String> parameters = Maps.newHashMap();
        String date = formatDate(new Date());

        parameters.put("responseDate", date);
        parameters.put("request", identity.baseURL());

        return parameters;
    }

    public String fillTemplate(String templateName, Map<String, String> parameters) {

        String template = templateHandler.getTemplate(templateConfiguration.getProperty(templateName));
        StrSubstitutor substitutor = new StrSubstitutor(parameters);
        return substitutor.replace(template);

    }

    public String fillTemplateWithDefaultParameters(String templateName) {
        return fillTemplate(templateName, getCommonParameters());

    }

    public String formatDate(Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(identity.granularity().replace("DD", "dd"));
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            return format.format(date);
        } catch (Exception e) {
            throw new DataAccessException("Invalid date format", e);
        }
    }


    public String formatHeader(Record record) {
        Map<String, String> headerParmeters = Maps.newHashMap();
        headerParmeters.put("identifier", record.getOAIIdentifier());
        headerParmeters.put("date", formatDate(record.getDateStamp()));

        if (record.isDeleted()) {
            headerParmeters.put("status", "status=\"deleted\"");
        } else {
            headerParmeters.put("status", "");
        }

        headerParmeters.put("sets", formatSetSpec(record));
        return fillTemplate("header.template", headerParmeters);
    }

    public String formatRecord(Record record, String metadata) {

        String header = formatHeader(record);
        Map<String, String> recordParameters = Maps.newHashMap();
        recordParameters.put("header", header);
        recordParameters.put("metadata", metadata);

        return fillTemplate("record.template", recordParameters);
    }

    protected String formatSetSpec(Record record) {
        if (!CollectionUtils.isEmpty(record.getSets())) {
            StringBuilder setSpecs = new StringBuilder();
            for (OaiSet oaiSet : record.getSets()) {
                setSpecs.append("\n");
                Map<String, String > parameters = Maps.newHashMap();
                parameters.put("setSpec", oaiSet.getSpec());
                setSpecs.append(fillTemplate("setSpec.template", parameters));
            }
            return setSpecs.toString();
        } else {
            return "";
        }
    }

    public String formatSet(OaiSet set) {

        Map<String, String> recordParameters = Maps.newHashMap();
        recordParameters.put("setSpec", set.getSpec());
        recordParameters.put("setName", set.getName());
        if (set.hasDescription()) {
            String description = "<setDescription>" + set.getDescription() + "</setDescription>";
            recordParameters.put("setDescription", description);
        }

        return fillTemplate("set.template", recordParameters);
    }

    public String fillTopTemplate(String verb, String verbContent, String parameters) {
        Map<String, String> commonParameters = getCommonParameters();
        commonParameters.put("verb", verb);
        commonParameters.put("parameters", parameters);
        commonParameters.put("verbContent", verbContent);

        return fillTemplate("common.template", commonParameters);
    }

}
