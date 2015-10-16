package ch.epfl.gsn.oai.rest.verbs;

import ch.epfl.gsn.oai.interfaces.Record;
import ch.epfl.gsn.oai.interfaces.RepositoryIdentity;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


/**
 * Created by kryvych on 09/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class TemplateHelperTest {

    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    @Mock
    private Properties templateConfigurationMock;

    @Mock
    private TemplateHandler templateHandlerMock;

    @Mock
    private Record recordMock;

    @Mock
    private RepositoryIdentity identityMock;
    private TemplateHelper subject;


    @Before
    public void initSubject() {
        when(identityMock.granularity()).thenReturn(DATE_FORMAT);

        subject = new TemplateHelper(templateConfigurationMock, templateHandlerMock, identityMock);
    }


    @Test
    public void testFillTemplate() throws Exception {
        when(templateHandlerMock.getTemplate("test")).thenReturn("test ${replace}");
        when(templateConfigurationMock.getProperty("test")).thenReturn("test");

        Map<String,String> parameters = Maps.newHashMap();
        parameters.put("replace", "REAL VALUE");

        assertThat(subject.fillTemplate("test", parameters), is("test REAL VALUE"));

    }


    @Test
    public void testFormatDate() throws Exception {
        Date date = new Date(0);
        assertThat(subject.formatDate(date), is("1970-01-01T01:00:00"));
    }

    @Test
    public void testFormatHeader() throws Exception {
        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID1");
        when(recordMock.isDeleted()).thenReturn(false);

        when(templateHandlerMock.getTemplate("header.template")).thenReturn("<header ${status}>\n" +
                "    <identifier>${identifier}</identifier>\n" +
                "    <datestamp>${date}</datestamp>\n" +
                "</header>");
        when(templateConfigurationMock.getProperty("header.template")).thenReturn("header.template");

       assertThat(subject.formatHeader(recordMock), is("<header >\n" +
               "    <identifier>ID1</identifier>\n" +
               "    <datestamp>1970-01-01T01:00:00</datestamp>\n" +
               "</header>"));
    }

    @Test
    public void testFormatHeaderDeletedRecord() throws Exception {
        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID1");
        when(recordMock.isDeleted()).thenReturn(true);

        when(templateHandlerMock.getTemplate("header.template")).thenReturn("<header ${status}>\n" +
                "    <identifier>${identifier}</identifier>\n" +
                "    <datestamp>${date}</datestamp>\n" +
                "</header>");
        when(templateConfigurationMock.getProperty("header.template")).thenReturn("header.template");

        assertThat(subject.formatHeader(recordMock), is("<header status=\"deleted\">\n" +
                "    <identifier>ID1</identifier>\n" +
                "    <datestamp>1970-01-01T01:00:00</datestamp>\n" +
                "</header>"));
    }

    @Test
    public void testFormatRecord() throws Exception {
        when(recordMock.getDateStamp()).thenReturn(new Date(0));
        when(recordMock.getOAIIdentifier()).thenReturn("ID1");
        when(recordMock.isDeleted()).thenReturn(true);

        when(templateHandlerMock.getTemplate("record.template")).thenReturn("<record>\n" +
                "    ${header}\n" +
                "    <metadata>\n" +
                "        ${metadata}\n" +
                "    </metadata>\n" +
                "</record>");
        when(templateConfigurationMock.getProperty("record.template")).thenReturn("record.template");

        assertThat(subject.formatRecord(recordMock, "metadata"), is("<record>\n" +
                "    ${header}\n" +
                "    <metadata>\n" +
                "        metadata\n" +
                "    </metadata>\n" +
                "</record>"));
    }

    @Test
    public void testFillTopTmplate() throws Exception {
        when(templateHandlerMock.getTemplate("common.template")).thenReturn(
                "<?xml version='1.0' encoding='UTF-8'?>\n" +
                        "<OAI-PMH xmlns='http://www.openarchives.org/OAI/2.0/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                        "         xsi:schemaLocation='http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd'>\n" +
                        "<request verb=\"${verb}\">${request}</request>\n" +
                        "<${verb}>\n" +
                        "    ${verbContent}\n" +
                        "</${verb}>\n" +
                        "</OAI-PMH>");

        when(templateConfigurationMock.getProperty("common.template")).thenReturn("common.template");

        assertThat(subject.fillTopTmplate("testVerb", "content"), is("<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<OAI-PMH xmlns='http://www.openarchives.org/OAI/2.0/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
                "         xsi:schemaLocation='http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd'>\n" +
                "<request verb=\"testVerb\">${request}</request>\n" +
                "<testVerb>\n" +
                "    content\n" +
                "</testVerb>\n" +
                "</OAI-PMH>"));
    }
}