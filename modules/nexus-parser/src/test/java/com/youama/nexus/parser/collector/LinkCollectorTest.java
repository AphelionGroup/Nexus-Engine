package com.youama.nexus.parser.collector;

import com.youama.nexus.core.item.BasicItem;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author David Belicza
 * @since 0.1.0
 */
public class LinkCollectorTest {

    public String stringHTMLSource = "";

    @Before
    public void setUp() {
        try {
            String path = this.getClass().getResource("/simple1.html").getPath();
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            stringHTMLSource = new String(encoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            stringHTMLSource = "";
            e.printStackTrace();
        } catch (IOException e) {
            stringHTMLSource = "";
            e.printStackTrace();
        }
    }

    @Test
    public void testValidateLinks() {
        List<BasicItem> items;
        LinkCollector linkCollector = new LinkCollector(stringHTMLSource);
        linkCollector.parse();

        linkCollector.validateLinks(false);
        items = linkCollector.getItems();
        assertEquals(5, items.get(0).getData().size());

        linkCollector.validateLinks(true);
        items = linkCollector.getItems();
        assertEquals(4, items.get(0).getData().size());
    }

    @Test
    public void testParse() {
        LinkCollector linkCollector = new LinkCollector(stringHTMLSource);
        linkCollector.parse();
        List<BasicItem> items = linkCollector.getItems();
        assertEquals(8, items.get(0).getData().size());
    }

    @Test
    public void testParseByRule() {

        LinkCollector linkCollector = new LinkCollector(stringHTMLSource);
        List<BasicItem> items;

        // All no-follow links
        linkCollector.parseByRule(CollectorSelector.LINK_NO_FOLLOW);
        items = linkCollector.getItems();
        assertEquals(1, items.get(0).getData().size());

        // All kind of links
        linkCollector.parseByRule(CollectorSelector.LINK_ALL);
        items = linkCollector.getItems();
        assertEquals(8, items.get(0).getData().size());

        // Only valid links
        linkCollector.parseByRule(CollectorSelector.LINK_VALID);
        items = linkCollector.getItems();
        assertEquals(5, items.get(0).getData().size());

        // Only valid and non-resource links
        linkCollector.parseByRule(CollectorSelector.LINK_VALID_NO_RESOURCES);
        items = linkCollector.getItems();
        assertEquals(4, items.get(0).getData().size());
    }
}