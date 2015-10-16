package ch.epfl.osper.oai.demoimpl.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by kryvych on 07/09/15.
 */

public class ReadFileToString {

    protected static final Logger logger = LoggerFactory.getLogger(ReadFileToString.class);

    public static String readFileFromClasspath(final String fileName) throws IOException {
        try {

            logger.info("Reading " + fileName);
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            if (!classPathResource.exists()) {
                logger.error("File doesn't exists " + fileName);
                return StringUtils.EMPTY;
            }

            return IOUtils.toString(classPathResource.getInputStream());

        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

