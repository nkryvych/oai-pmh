package ch.epfl.gsn.oai.rest.verbs;

import ch.epfl.gsn.oai.interfaces.DataAccessException;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

/**
 * Created by kryvych on 09/10/15.
 */
@Named
class TemplateHandler {

    protected static final Logger logger = LoggerFactory.getLogger(TemplateHandler.class);

    private Map<String, String> templates = Maps.newHashMap();

    public String getTemplate(String name) {
        if (templates.containsKey(name)) {
            return templates.get(name);
        } else {
            try {
                String template = readFromFile(name);
                templates.put(name, template);
                return template;
            } catch (IOException e) {
                throw new DataAccessException("Cannot read template file " + name, e);
            }
        }

    }

    private String readFromFile(final String fileName) throws IOException {

        ClassPathResource classPathResource = new ClassPathResource(fileName);
        if (!classPathResource.exists()) {

            logger.error("File doesn't exists " + fileName);
            return StringUtils.EMPTY;
        }

        return IOUtils.toString(classPathResource.getInputStream());

    }
}
