package xml.load;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Test we can load objects from XML
 */
@Slf4j
class XmlConfigurationTest {

    private final Map<String, Concept> conceptMap = new HashMap<>();

    public static void main(String[] args) {
        XmlConfigurationTest xmlConfigurationTest = new XmlConfigurationTest();
        xmlConfigurationTest.loadXmlData();
        log.info("Loaded XML {}", xmlConfigurationTest.conceptMap);
    }

    private void loadXmlData() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader
                .getResourceAsStream("config.xml")) {

            XmlMapper xmlMapper = new XmlMapper();

            Concepts concepts = xmlMapper.readValue(inputStream, Concepts.class);

            for (Concept concept : concepts.getConcept()) {
                conceptMap.put(concept.getCode(), concept);
            }
        } catch (IOException exception) {
            log.error("Exception caught", exception);
        }
    }
}
