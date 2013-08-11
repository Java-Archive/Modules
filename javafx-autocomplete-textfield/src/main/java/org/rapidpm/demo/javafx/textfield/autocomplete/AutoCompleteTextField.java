
package org.rapidpm.demo.javafx.textfield.autocomplete;


import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * This class is main Control class which extends from Control <br>
 * and also implements basic functions of  the AutoCompleteTextFieldFactory<br>
 * <p></p>
 * You can easily utilize the AutoCompleteTextField in your application<br>
 * <p></p>
 * e.g <br>
 * <pre>
 *      //..codes
 *      AutoCompleteTextField autobox = new AutoCompleteTextField("hello","prefix","dog","city");
 *      autobox.setLimit(7);
 *      //..add autobox to your scene then the output must be like this:
 * </pre>
 *
 * @author Narayan G. Maharjan
 * @author Sven Ruppert
 */
public class AutoCompleteTextField<T extends AutoCompleteElement> extends Control implements AutoCompleteTextFieldFactory<T> {

    public static final int DEFAULT_LIMIT = 6;

    private boolean filterModeStartsWith = true;
    private TextField textbox;
    private ListView<T> listview;
    private ObservableList<T> data = FXCollections.observableArrayList();
    private boolean filterMode;
    private int limit;

    private List<AutoCompleteTextFieldAction> customActionsList = new ArrayList<>();


    public AutoCompleteTextField(ObservableList<T> data) {
        init();
        this.data = data;
    }

    public AutoCompleteTextField() {
        init();
    }

    public AutoCompleteTextField(AutoCompleteTextField other) {
        this.filterModeStartsWith = other.filterModeStartsWith;
        this.textbox = other.textbox;
        this.listview = other.listview;
        this.data = other.data;
        this.filterMode = other.filterMode;
        this.limit = other.limit;
        this.customActionsList = other.customActionsList;
    }

    private void init() {
        getStyleClass().setAll("autofill-text");
        textbox = new TextField();
        listview = new ListView<T>();
        limit = DEFAULT_LIMIT;
        filterMode = false;

        listen();

    }


    public void requestFocus() {
        super.requestFocus();
        textbox.requestFocus();
    }

    public T getItem() {
        return listview.getSelectionModel().getSelectedItem();
    }

    public String getText() {
        return textbox.getText();
    }

    @Override
    public void setData(ObservableList<T> data) {
        this.data = data;
    }

    @Override
    public ObservableList<T> getData() {
        return data;
    }

    @Override
    public ListView<T> getListview() {
        return listview;
    }

    @Override
    public TextField getTextbox() {
        return textbox;
    }


    @Override
    public void setListLimit(int limit) {

        this.limit = limit;

    }

    @Override
    public int getListLimit() {
        return limit;
    }

    @Override
    public void setFilterMode(boolean filter) {
        filterMode = filter;
    }

    @Override
    public boolean getFilterMode() {
        return filterMode;
    }

    @Override
    public void setMinSize(double d, double d1) {
        super.setMinSize(d, d1);
        textbox.setMinSize(d, d1);
    }

    @Override
    public void setPrefSize(double d, double d1) {
        super.setPrefSize(d, d1);
        textbox.setPrefSize(d, d1);
    }

    @Override
    public void resize(double d, double d1) {
        super.resize(d, d1);
        textbox.resize(d, d1);
    }

    @Override
    public void setMaxSize(double d, double d1) {
        super.setMaxSize(d, d1);
        textbox.setMaxSize(d, d1);

    }

    public boolean isFilterModeStartsWith() {
        return filterModeStartsWith;
    }

    public void setFilterModeStartsWith(boolean filterModeStartsWith) {
        this.filterModeStartsWith = filterModeStartsWith;
    }

    public List<AutoCompleteTextFieldAction> getCustomActionsList() {
        return customActionsList;
    }

    private void listen() {
        this.prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setPrefHeight(t1.doubleValue());
            }

        });
        this.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setPrefWidth(t1.doubleValue());
            }

        });
        this.minHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMinHeight(t1.doubleValue());
            }

        });
        this.maxHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMaxHeight(t1.doubleValue());
            }

        });
        this.minWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMinWidth(t1.doubleValue());
            }

        });
        this.maxWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMaxWidth(t1.doubleValue());
            }

        });
    }

}
