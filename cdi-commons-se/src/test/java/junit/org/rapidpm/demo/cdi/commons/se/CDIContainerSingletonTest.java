package junit.org.rapidpm.demo.cdi.commons.se;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;

/**
 * CDIContainerSingleton Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 5, 2013</pre>
 */
public class CDIContainerSingletonTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    public static class TestClassDemoA {
        private String str = "A - "+System.nanoTime();

        @Inject TestClassDemoB testClassDemoB;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("TestClassDemoA{");
            sb.append("str='").append(str).append('\'');
            sb.append(", testClassDemoB=").append(testClassDemoB);
            sb.append('}');
            return sb.toString();
        }

    }


    public static class TestClassDemoB {
        private String str = "B - "+System.nanoTime();

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("TestClassDemoB{");
            sb.append("str='").append(str).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception {
        final CDIContainerSingleton instance = CDIContainerSingleton.getInstance();
        Assert.assertNotNull(instance);
    }

    /**
     * Method: getManagedInstance(final Class<T> clazz)
     */
    @Test
    public void testGetManagedInstance() throws Exception {
        final CDIContainerSingleton instance = CDIContainerSingleton.getInstance();
        Assert.assertNotNull(instance);
        final TestClassDemoA classDemoA = instance.getManagedInstance(TestClassDemoA.class);
        Assert.assertNotNull(classDemoA);

    }

    /**
     * Method: getInstanceReference(final Class<T> clazz)
     */
    @Test
    public void testGetInstanceReference() throws Exception {
        final CDIContainerSingleton instance = CDIContainerSingleton.getInstance();
        Assert.assertNotNull(instance);
        final Instance<TestClassDemoA> ref = instance.getInstanceReference(TestClassDemoA.class);
        Assert.assertNotNull(ref);
        final boolean unsatisfied = ref.isUnsatisfied();
        //Assert.assertFalse(unsatisfied);
        final TestClassDemoA s = ref.get();
        Assert.assertNotNull(s);
        Assert.assertNotNull(s.testClassDemoB);

        System.out.println("s = " + s);

    }


} 
