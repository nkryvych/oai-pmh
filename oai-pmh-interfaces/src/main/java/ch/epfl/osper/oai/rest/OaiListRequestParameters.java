package ch.epfl.osper.oai.rest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kryvych on 07/09/15.
 */
public class OaiListRequestParameters extends RequestParameters {

    private static final String[] DATE_FORMATS = {"yyyy-MM-dd'T'HH:mm'Z'"
            , "yyyy-MM-dd'T'HH:mm:ss'Z'"
            , "yyyy-MM-dd"};


    private String metadataPrefix;
    private String from;
    private String until;
    private String set;
    private String resumptionToken;

    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getResumptionToken() {
        return resumptionToken;
    }

    public void setResumptionToken(String resumptionToken) {
        this.resumptionToken = resumptionToken;
    }

    public Date getFromDate() throws ParseException {
        if (StringUtils.isNotEmpty(from)) {
            return  DateUtils.parseDate(from, DATE_FORMATS);
        }
        return null;
    }

    public Date getUntilDate() throws ParseException {
        if (StringUtils.isNotEmpty(until)) {
            return DateUtils.parseDate(until, DATE_FORMATS);
        }
        return null;
    }

    public String getParametersString() {
        StringBuilder builder = new StringBuilder();
        appendIfNotEmpty(builder, "metadataPrefix", this.getMetadataPrefix());
        appendIfNotEmpty(builder, "from", this.getFrom());
        appendIfNotEmpty(builder, "until", this.getUntil());
        appendIfNotEmpty(builder, "set", this.getSet());
        appendIfNotEmpty(builder, "resumptionToken", this.getResumptionToken());

        return builder.toString();
    }

}
