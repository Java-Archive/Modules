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

package thirdparty.eu.schudt.javafx.controls.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.GridPane;

/**
 * Abstract base class for the {@link MonthView}, {@link YearView} and {@link DecadesView}.
 *
 * @author Christian Schudt
 */
abstract class DatePane extends GridPane {

    /**
     * Sets basic stuff
     *
     * @param calendarView The calendar view.
     */
    protected DatePane(final CalendarView calendarView) {
        this.calendarView = calendarView;

        // When the date changed, update the days.
        calendarView.calendarDate.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateContent();
            }
        });

        // Every time the calendar changed, rebuild the pane and update the content.
        calendarView.calendarProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                getChildren().clear();
                buildContent();
                updateContent();
            }
        });

        buildContent();
        updateContent();
    }

    /**
     * The calendar view.
     */
    protected CalendarView calendarView;

    /**
     * This is the date, this pane operates on.
     *
     * @param date The date.
     */
    protected void setDate(Date date) {
        calendarView.getCalendar().setTime(date);
        updateContent();
        // Restore
        calendarView.getCalendar().setTime(calendarView.calendarDate.get());
    }

    /**
     * Builds the content.
     */
    protected abstract void buildContent();

    /**
     * Updates the content.
     */
    protected abstract void updateContent();

    protected StringProperty title = new SimpleStringProperty();

    /**
     * The title property which is defined by the pane.
     *
     * @return The property.
     */
    public ReadOnlyStringProperty titleProperty() {
        return title;
    }

    /**
     * Gets the date format, associated with the current calendar.
     *
     * @param format The date format as String.
     * @return The date format.
     */
    protected DateFormat getDateFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, calendarView.localeProperty().get());
        dateFormat.setCalendar(calendarView.getCalendar());
        return dateFormat;
    }
}
