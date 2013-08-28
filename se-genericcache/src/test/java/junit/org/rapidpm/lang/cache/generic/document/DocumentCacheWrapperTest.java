package junit.org.rapidpm.lang.cache.generic.document;

import java.util.Collection;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import org.junit.After;
import org.junit.Before;


public class DocumentCacheWrapperTest {
    //public DocumentCacheWrapperTest(String name) {

    public DocumentCacheWrapperTest() {
        //super(name); JUnit3
    }

    private final DocumentCacheWrapper wrapper = new DocumentCacheWrapper();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 1000; i++) {
            final Document document = new Document();
            document.setDocId(i);
            document.setContent(" Das ist der Content " + i);
            wrapper.put2Cache(document);
        }
        System.out.println("Cache filled...");

    }

    @After
    public void tearDown() throws Exception {
        //super.tearDown(); JUnit3
    }

    /**
     * Method: put2Cache(final Document document)
     */
    @org.junit.Test
    public void testPut2Cache() throws Exception {
        //JIRA MOD-22 test Fehlt : testPut2Cache()
    }

    /**
     * Method: removePerson(final Document document)
     */
    @org.junit.Test
    public void testRemovePerson() throws Exception {
        //JIRA MOD-23 test Fehlt : testRemovePerson()
    }

    /**
     * Method: getDocument(final String docId)
     */
    @org.junit.Test
    public void testGetDocument() throws Exception {
        for (int i = 0; i < 1000; i++) {
            final Collection<Document> documents = wrapper.getDocument(i);
            for (final Document document : documents) {
                //JIRA MOD-24 test Fehlt : testGetDocument()
            }
        }

    }


    public static Test suite() {
        return new JUnit4TestAdapter(DocumentCacheWrapperTest.class);
    }
}
