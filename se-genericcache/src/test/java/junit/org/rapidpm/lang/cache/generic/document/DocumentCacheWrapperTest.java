/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
