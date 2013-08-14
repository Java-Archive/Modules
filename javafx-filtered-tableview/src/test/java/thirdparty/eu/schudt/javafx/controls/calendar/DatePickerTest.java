package thirdparty.eu.schudt.javafx.controls.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.rapidpm.demo.cdi.commons.format.CDISimpleDateFormatter;
import org.rapidpm.demo.cdi.commons.fx.JavaFXBaseTest;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.demo.cdi.commons.logger.Logger;
import sun.util.BuddhistCalendar;

/**
 * User: Sven Ruppert Date: 14.08.13 Time: 10:51
 */
public class DatePickerTest extends JavaFXBaseTest {
    @Override protected Class<? extends JavaFXBaseTest> getTestClass() {
        return DatePickerTest.class;
    }



    public static class TestImpl extends JavaFXBaseTest.JavaFXBaseTestImpl{
        @Override public boolean isExitAfterTest() {
            return false;
        }

        @Override protected Class<? extends JavaFXBaseTest> getParentTestClass() {
            return DatePickerTest.class;
        }
        @Inject
        @CDISimpleDateFormatter(value = "date.yyyyMMdd")
        SimpleDateFormat sdf;
        @Inject
        @CDILogger
        Logger logger;

        @Inject DatePicker datePicker;
//        DatePicker datePicker = new DatePicker();


        @Override public void testImpl(Stage stage) {
            if (logger.isDebugEnabled()) {
                logger.debug("testrunn at -> " + sdf.format(new Date()));
            }
            final VBox root = new VBox();
            stage.setTitle("DatePickerTest");  //i18n
            stage.setScene(new Scene(root, 300, 275));

            datePicker.getCalendarView().setShowTodayButton(true);
            datePicker.setLocale(Locale.GERMAN);

            root.getChildren().add(datePicker);
        }
    }
}
