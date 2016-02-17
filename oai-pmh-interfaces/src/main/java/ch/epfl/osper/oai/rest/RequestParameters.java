package ch.epfl.osper.oai.rest;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by kryvych on 16/02/16.
 */
public abstract class RequestParameters {

    public abstract String getParametersString();

    protected StringBuilder appendIfNotEmpty(StringBuilder builder, String name, String value) {
        if (StringUtils.isNotEmpty(value)) {
            builder.append(name).append("=").append("\"").append(value).append("\" ");
        }
        return builder;
    }

}
