package ch.epfl.osper.oai.rest.verbs;

import ch.epfl.osper.oai.interfaces.OaiSet;
import ch.epfl.osper.oai.interfaces.Record;
import ch.epfl.osper.oai.interfaces.RepositoryIdentity;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TemplateHelperTestWithSpring.Config.class)

public class TemplateHelperTestWithSpring {

    @Configuration
    static class Config {

        // this bean will be injected into the OrderServiceTest class
        @Bean(name = "templateConfigurationMock")
        public PropertiesFactoryBean configuration() {
            PropertiesFactoryBean bean = new PropertiesFactoryBean();
            bean.setLocation(new ClassPathResource("templates/template.properties"));
            return bean;
        }

        @Bean
        public TemplateHandler templateHandler() {
            return new TemplateHandler();
        }
    }

    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @Autowired
    private Properties templateConfigurationMock;

    @Autowired
    private TemplateHandler templateHandlerMock;

    private RepositoryIdentity identityMock;

    private TemplateHelper subject;

    private Record recordMock;

    private OaiSet setMock;

    @Before
    public void initSubject() {
        identityMock = mock(RepositoryIdentity.class);
        setMock = mock(OaiSet.class);


        when(identityMock.granularity()).thenReturn(DATE_FORMAT);
        when(identityMock.baseURL()).thenReturn("testURL");

        recordMock = mock(Record.class);

        subject = new TemplateHelper(templateConfigurationMock, templateHandlerMock, identityMock);
    }


    @Test
    public void testFormatHeaderWithoutSets() throws Exception {
        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID1");
        when(recordMock.isDeleted()).thenReturn(false);

        assertThat(subject.formatHeader(recordMock), is("<header >\n" +
                "    <identifier>ID1</identifier>\n" +
                "    <datestamp>1970-01-01T00:00:00</datestamp>\n" +
                "</header>"));
    }

    @Test
    public void testFormatHeaderWithSets() throws Exception {
        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID1");
        when(recordMock.isDeleted()).thenReturn(false);
        when(recordMock.getSets()).thenReturn(Sets.newHashSet(new OaiSet("set", "name", "")));

        String result = "<header >\n    <identifier>ID1</identifier>\n    <datestamp>1970-01-01T00:00:00</datestamp>\n<setSpec>set</setSpec>\n</header>";
        assertThat(subject.formatHeader(recordMock).trim(), is(result.trim()));
    }

    @Test
    public void testFormatHeaderDeletedRecord() throws Exception {

        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID1");
        when(recordMock.isDeleted()).thenReturn(true);

        assertThat(subject.formatHeader(recordMock), is("<header status=\"deleted\">\n" +
                "    <identifier>ID1</identifier>\n" +
                "    <datestamp>1970-01-01T00:00:00</datestamp>\n" +
                "</header>"));
    }

    @Test
    public void testFormatRecord() throws Exception {

        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID1");
        when(recordMock.isDeleted()).thenReturn(false);

        String record = subject.formatRecord(recordMock, "metadata");

        assertThat(record, is("<record>\n" +
                "    <header >\n    <identifier>ID1</identifier>\n    <datestamp>1970-01-01T00:00:00</datestamp>\n</header>\n" +
                "    <metadata>\n" +
                "        metadata\n" +
                "    </metadata>\n" +
                "</record>"));
    }

    @Test
    public void testFormatDeletedRecord() throws Exception {
        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID2");
        when(recordMock.isDeleted()).thenReturn(true);

        String actual = subject.formatRecord(recordMock, "metadata");
        assertThat(actual, is("<record>\n    <header status=\"deleted\">\n    <identifier>ID2</identifier>\n    <datestamp>1970-01-01T00:00:00</datestamp>\n</header>\n    <metadata>\n        metadata\n    </metadata>\n</record>"));
    }

    @Test
    public void testFillTopTmplate() throws Exception {

        String actual = subject.fillTopTemplate("testVerb", "content", "identifier=\"test\"");
        String date = subject.formatDate(new Date());
        assertThat(actual, is("<?xml version='1.0' encoding='UTF-8'?>\n<OAI-PMH xmlns='http://www.openarchives.org/OAI/2.0/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "         xsi:schemaLocation='http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd'>\n" +
                "    <responseDate>" + date + "</responseDate>\n" +
                "    <request verb=\"testVerb\" identifier=\"test\">testURL</request>\n" +
                "<testVerb>\n    content\n</testVerb>\n</OAI-PMH>"));
    }

    @Test
    public void testFormatSetWithDescription() throws Exception {

        when(setMock.getName()).thenReturn("name");
        when(setMock.getSpec()).thenReturn("spec:name");
        when(setMock.getDescription()).thenReturn("description");
        when(setMock.hasDescription()).thenReturn(true);


        assertThat(subject.formatSet(setMock), is("<set>\n    <setSpec>spec:name</setSpec>\n    <setName>name</setName>\n    <setDescription>description</setDescription>\n</set>"));
    }

    @Test
    public void testRecordWithSets() throws Exception {

        when(setMock.getName()).thenReturn("name");
        when(setMock.getSpec()).thenReturn("spec:name1");
        when(setMock.getDescription()).thenReturn("description");
        when(setMock.hasDescription()).thenReturn(true);


        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID2");
        when(recordMock.isDeleted()).thenReturn(false);
        when(recordMock.getSets()).thenReturn(Sets.newHashSet(setMock));

        String actual = subject.formatRecord(recordMock, "metadata");
        assertThat(actual, is("<record>\n    <header >\n    <identifier>ID2</identifier>\n    <datestamp>1970-01-01T00:00:00</datestamp>\n<setSpec>spec:name1</setSpec>\n</header>\n    <metadata>\n        metadata\n    </metadata>\n</record>"));
    }
}
