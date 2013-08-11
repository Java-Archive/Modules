package junit.org.rapidpm.demo.cdi.commons.se.format;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.demo.cdi.commons.format.CDISimpleDateFormatter;
import org.rapidpm.demo.cdi.commons.se.CDIContainerSingleton;

/**
 * User: Sven Ruppert
 * Date: 20.06.13
 * Time: 16:51
 */
public class SimpleDateFormatTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    public static class TestClassDemoA {
        public  String str = "A - "+System.nanoTime();

        @Inject
        @CDISimpleDateFormatter
        SimpleDateFormat sdf;

        @Inject
        @CDISimpleDateFormatter("date.yyyy")
        SimpleDateFormat sdfYYYY;

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
        final SimpleDateFormat simpleDateFormat = instance.getManagedInstance(SimpleDateFormat.class);
        Assert.assertNotNull(simpleDateFormat);
    }




    /**
     * Method: createDefault(InjectionPoint injectionPoint)
     */
    @Test
    public void testProduceSimpleDateFormatter01() throws Exception {
        final CDIContainerSingleton instance = CDIContainerSingleton.getInstance();
        final TestClassDemoA testClassDemoA = instance.getManagedInstance(TestClassDemoA.class);
        final SimpleDateFormat sdfYYYY = testClassDemoA.sdfYYYY;
        Assert.assertNotNull(sdfYYYY);

        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2010, Calendar.JANUARY, 1, 00, 00, 00);
        final Date date = calendar.getTime();
        System.out.println("date = " + date);
        final String format = sdfYYYY.format(date);
        System.out.println("format = " + format);
        Assert.assertTrue(format.equals("2010"));
    }

    @Test
    public void testProduceSimpleDateFormatter02() throws Exception {
        final CDIContainerSingleton instance = CDIContainerSingleton.getInstance();
        final TestClassDemoA testClassDemoA = instance.getManagedInstance(TestClassDemoA.class);
        final SimpleDateFormat sdfYYYY = testClassDemoA.sdfYYYY;

        Assert.assertNotNull(sdfYYYY);
        final Date parse = sdfYYYY.parse("2010");
        System.out.println("parse = " + parse);
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2010, Calendar.JANUARY, 1, 00, 00, 00);
        final Date date = calendar.getTime();
        System.out.println("date = " + date);
        Assert.assertTrue(date.equals(parse));
    }


}
