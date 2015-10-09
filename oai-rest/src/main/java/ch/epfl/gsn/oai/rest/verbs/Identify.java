package ch.epfl.gsn.oai.rest.verbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by kryvych on 04/09/15.
 */
@Named
@Scope("prototype")
public class Identify{

    protected static final Logger logger = LoggerFactory.getLogger(Identify.class);

    private final TemplateHelper templateHelper;

    @Inject
    public Identify(TemplateHelper templateHelper) {
        this.templateHelper = templateHelper;
    }


    public String getResponse() {

           return templateHelper.fillTemplateWithDefaultParameters("identify.template");

    }

}
