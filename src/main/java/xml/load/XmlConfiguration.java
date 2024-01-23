package xml.load;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class XmlConfiguration {

    private final Map<String, Concept> conceptMap = new HashMap<>();

    public static void main(String[] args) {
        XmlConfiguration xmlConfiguration = new XmlConfiguration();
        xmlConfiguration.loadXmlData();
        log.info("Loaded XML {}", xmlConfiguration.conceptMap);
    }

    public void loadXmlData() {
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
