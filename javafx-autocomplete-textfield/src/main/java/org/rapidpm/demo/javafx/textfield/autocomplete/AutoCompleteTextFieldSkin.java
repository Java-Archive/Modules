package org.rapidpm.demo.javafx.textfield.autocomplete;

import java.util.Comparator;
import java.util.List;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Callback;
import org.rapidpm.module.se.commons.logger.Logger;


/**
 * This class helps to change the skin of the AutoCompleteTextField Control
 *
 * @author Narayan G. Maharjan
 */
public class AutoCompleteTextFieldSkin<T extends AutoCompleteElement> extends SkinBase<AutoCompleteTextField<T>, AutoCompleteTextFieldBehavior<T>>
        implements ChangeListener<String>, EventHandler {

    private static final Logger logger = Logger.getLogger(AutoCompleteTextFieldSkin.class);

    //Final Static variables for Window Insets
    private final static int TITLE_HEIGHT = 28;

    //This is listview for showing the matched words
    private ListView<T> listview;

    //This is Textbox where user types
    private TextField textbox;

    //This is the main Control of AutoCompleteTextField
    private AutoCompleteTextField<T> autofillTextbox;

    //This is the ObservableData where the matching words are saved
    private ObservableList<T> data;

    //This is the Popup where listview is embedded.
    private Popup popup;
    private Object finalSelectedActions;


    public Window getWindow() {
        return autofillTextbox.getScene().getWindow();
    }

    private String temporaryTxt = "";


    public AutoCompleteTextFieldSkin(AutoCompleteTextField<T> text) {
        super(text, new AutoCompleteTextFieldBehavior<T>(text));
        //variable Assignment
        autofillTextbox = text;
        //listview for autofill textbox
        listview = text.getListview();
        if (text.getFilterMode()) {
            listview.setItems(text.getData());
        }
        listview.itemsProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (t == null && ((String) t1).length() > 1) {
                    hidePopUp();  //manuelle Eingabe beginnt mit einem Zeichen
                } else if (listview.getItems() != null && listview.getItems().size() > 0) {
                    showPopup();
                } else {
                    hidePopup();
                }
            }


        });
        listview.setOnMouseReleased(this);
        listview.setOnKeyReleased(this);
        //This cell factory helps to know which cell has been selected so that
        //when ever any cell is selected the textbox rawText must be changed
        listview.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
            @Override
            public ListCell<T> call(ListView<T> p) {
                //A simple ListCell containing only Label
                final ListCell<T> cell = new ListCell<T>() {
                    @Override
                    public void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getShortinfo());

                        }
                    }

                };
                //A listener to know which cell was selected so that the textbox
                //we can set the rawTextProperty of textbox

                cell.focusedProperty().addListener(new InvalidationListener() {

                    // @Override
                    public void invalidated(Observable ove) {
                        //ObservableValue<Boolean> ov = (ObservableValue<Boolean>) ove;
                        final AutoCompleteElement cellItem = cell.getItem();
                        if (cellItem != null && cell.isFocused()) {
                            //here we are using 'temporaryTxt' as temporary saving text
                            //If temporaryTxt length is 0 then assign with current rawText()
                            String prev = null;

                            //first check ...(either texmporaryTxt is empty char or not)
                            if (temporaryTxt.length() <= 0) {
                                //second check...
                                if (listview.getItems().size() != data.size())
                                    temporaryTxt = textbox.getText();
                            }

                            prev = temporaryTxt;
                            textbox.textProperty().removeListener(AutoCompleteTextFieldSkin.this);
                            //textbox.rawTextProperty().removeListener(AutoCompleteTextFieldSkin.this);
                            //textbox.setText(prev);
                            textbox.textProperty().setValue(cellItem.getShortinfo());
                            textbox.textProperty().addListener(AutoCompleteTextFieldSkin.this);
                            textbox.selectRange(prev.length(), cellItem.getKey().length());
                            if (logger.isDebugEnabled()) {
                                logger.debug(temporaryTxt.length() + "=" + textbox.getText().length() + "::" +
                                        cellItem.getKey().length());
                            }
                        }
                    }
                });

                return cell;
            }

        });

        //main textbox
        textbox = text.getTextbox();
        //textbox.setSelectOnFocus(false);
        textbox.setOnKeyPressed(this);
        textbox.textProperty().addListener(this);


        textbox.focusedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                textbox.end();
            }
        });

        //popup
        popup = new Popup();
        popup.setAutoHide(true);
        popup.getContent().add(listview);

        //list data and sorted ordered
        data = text.getData();
        FXCollections.sort(data, new Comparator<AutoCompleteElement>() {
            @Override
            public int compare(AutoCompleteElement o1, AutoCompleteElement o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                } else {
                    return o1.getKey().compareTo(o2.getKey());
                }
            }
        });

        //Adding textbox in this control Children
        getChildren().addAll(textbox);
    }

    /**
     * *******************************************************
     * Selects the current Selected Item from the list
     * and the content of that selected Item is set to textbox.
     * ********************************************************
     */
    public T selectList() {
        T selectedItem = listview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            textbox.setText(selectedItem.getKey());
            textbox.requestFocus();
            textbox.requestLayout();
            textbox.end();
            listview.getItems().clear();
            temporaryTxt = "";
            hidePopup();
        }
        return selectedItem;
    }


    /**
     * ***************************************************
     * This is the main event handler which handles all the
     * event of the listview and textbox
     *
     * @param evt
     */
    @Override
    public void handle(Event evt) {

        if (evt.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent t = (KeyEvent) evt;
            if (t.getSource() == textbox) {
                //WHEN USER PRESS DOWN ARROW KEY FOCUS TRANSFER TO LISTVIEW
                if (t.getCode() == KeyCode.DOWN) {
                    if (popup.isShowing()) {
                        listview.requestFocus();
                        listview.getSelectionModel().select(0);
                    }
                }
            }
        } else if (evt.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyEvent t = (KeyEvent) evt;
            if (t.getSource() == listview) {
                if (t.getCode() == KeyCode.ENTER) {
                    final T selectedItem = selectList();
                    executeCustomActions(selectedItem);
                } else if (t.getCode() == KeyCode.UP) {
                    if (listview.getSelectionModel().getSelectedIndex() == 0) {
                        textbox.requestFocus();
                    }
                }
            }
        } else if (evt.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (evt.getSource() == listview) {
                final T selectedItem = selectList();
                executeCustomActions(selectedItem);
            }
        }
    }

    private void executeCustomActions(T selectedItem) {
        final List<AutoCompleteTextFieldAction> customActionList = autofillTextbox.getCustomActionsList();
        for (final AutoCompleteTextFieldAction customAction : customActionList) {
            customAction.execute(selectedItem);
        }
    }

    @Override
    protected double computeMaxHeight(double width) {
        return Math.max(22.0d, textbox.getHeight());
    }

    @Override
    public void setPrefSize(double d, double d1) {
        super.setPrefSize(d, d1);
    }

    @Override
    protected double computePrefHeight(double width) {
        return Math.max(22.0d, textbox.getPrefHeight());
    }

    @Override
    protected double computeMinHeight(double width) {
        return Math.max(22.0d, textbox.getPrefHeight());
    }

    @Override
    protected double computePrefWidth(double height) {
        return Math.max(100.0d, textbox.getPrefWidth());
    }

    @Override
    protected double computeMaxWidth(double height) {
        return Math.max(100.0d, textbox.getPrefWidth());
    }

    @Override
    protected double computeMinWidth(double height) {
        return Math.max(100.0d, textbox.getPrefWidth());
    }

    public void hidePopUp() {
        popup.hide();
    }

    /**
     * A Popup containing Listview is trigged from this function
     * This function automatically resize it's height and width
     * according to the width of textbox and item's cell height
     */
    public void showPopup() {
        listview.setPrefWidth(textbox.getWidth());
        //final int maxShownElements = 6;
        final int heightPerElement = 24;
        final int weigthPerElement = 8;

        final int itemCount = listview.getItems().size();
        if (itemCount > AutoCompleteTextField.DEFAULT_LIMIT) {
            listview.setPrefHeight((AutoCompleteTextField.DEFAULT_LIMIT * heightPerElement));
            listview.setVisible(true);
        } else if (itemCount > 1) {
            final int tmpCount = itemCount + 1;
            listview.setPrefHeight(tmpCount * heightPerElement);
            listview.setVisible(true);
        }
        if (itemCount == 0) {
            listview.setVisible(false);
        } else {
            listview.setPrefHeight(itemCount * heightPerElement); //Sollte dann weg sein
            listview.setVisible(true);
        }

        //longest ShortInfo
        int maxLength = 0;
        for (final T element : listview.getItems()) {
            final int length = element.getShortinfo().length();
            if (length >= maxLength) {
                maxLength = length;
                if (logger.isDebugEnabled()) {
                    logger.debug("maxlength (changed): " + maxLength);
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("maxlength (not changed): " + maxLength);
                }
            }
        }
        if (maxLength > 0) {
            listview.setPrefWidth(maxLength * weigthPerElement);
        } else {
            //
        }

        final Window window = getWindow();
        popup.show(
                window,
                window.getX() + textbox.localToScene(0, 0).getX() + textbox.getScene().getX(),
                window.getY() + textbox.localToScene(0, 0).getY() + textbox.getScene().getY() + TITLE_HEIGHT);

        listview.getSelectionModel().clearSelection();
        listview.getFocusModel().focus(-1);
    }

    /**
     * This function hides the popup containing listview
     */
    public void hidePopup() {
        popup.hide();
    }


    /**
     * ********************************************
     * When ever the the rawTextProperty is changed
     * then this listener is activated
     *
     * @param ov
     * @param t
     * @param t1 **********************************************
     */


    @Override
    public void changed(ObservableValue ov, String t, String t1) {
        final String value = (String) ov.getValue();
        //setzen von Text
        final int deltalength = Math.abs(t.length() - t1.length());
        final boolean b = deltalength > 1;
        if (b) {
            hidePopUp();
        } else {
            if (value.length() > 0) {
                final String txtdata = (textbox.getText()).trim();
                if (logger.isDebugEnabled()) {
                    logger.debug("content textfield [" + txtdata + "]");
                }
                //Limit of data cell to be shown in ListView
                int limit = 0;
                if (txtdata.length() > 0) {
                    final ObservableList<T> list = FXCollections.observableArrayList();
                    final String compare = txtdata.toLowerCase();
                    for (T dat : data) {
                        final String lowerCaseKey = dat.getKey().toLowerCase();
                        boolean add = false;
                        if (autofillTextbox.isFilterModeStartsWith()) {
                            if (lowerCaseKey.startsWith(compare)) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("added one candidate, " + lowerCaseKey);
                                }
                                add = true;
                            }
                        } else {
                            if (lowerCaseKey.contains(compare)) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("added one candidate, " + lowerCaseKey);
                                }
                                add = true;
                            }
                        }
                        if (add) {
                            list.add(dat);
                            limit++;
                        } else {
                            //
                        }

                        if (limit == autofillTextbox.getListLimit())  //JIRA MOD-58 Anzahl aller Treffer anzeigen
                            break;
                    }
                    final ObservableList<T> items = listview.getItems();
                    if (items != null && items.containsAll(list) && items.size() == list.size())
                        showPopup();
                    else
                        listview.setItems(list);
                } else {
                    listview.setItems(autofillTextbox.getFilterMode() ? data : null);
                }
            }

            if (value.length() <= 0) {
                temporaryTxt = "";
                if (autofillTextbox.getFilterMode()) {
                    listview.setItems(data);
                    showPopup();
                } else {
                    hidePopup();
                }
            }
        }
    }
}
