
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

package org.rapidpm.modul.javafx.textfield.autocomplete;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * @author Narayan G. Maharjan
 */
public interface AutoCompleteTextFieldFactory<T extends AutoCompleteElement> {

    /**
     * Keeps the array of String which contains the
     * words to be matched on typing.
     *
     * @param data value
     */
    void setData(ObservableList<T> data);

    /**
     * Give the data containing possible fast matching words
     *
     * @return <a href="http://download.oracle.com/javafx/2.0/api/javafx/collections/ObservableList.html"> ObservableList </a>
     */
    public ObservableList<T> getData();

    /**
     * the main listview of the AutoCompleteTextField
     *
     * @return <a href="http://download.oracle.com/javafx/2.0/api/javafx/scene/control/ListView.html"> ListView </a>
     */
    public ListView<T> getListview();

    /**
     * the textbox of the AutoCompleteTextField
     *
     * @return <a href="http://download.oracle.com/javafx/2.0/api/javafx/scene/control/ListView.html"> TextView </a>
     */
    public TextField getTextbox();

    /**
     * This defines how many max listcell to be visibled in listview when
     * matched words are occured on typing.
     *
     * @param limit value
     */
    public void setListLimit(int limit);

    /**
     * this gives the limit of listcell to be visibled in listview
     *
     * @return int
     */
    public int getListLimit();

    /**
     * This sets the AutoFilterMode which can show as filter type
     * rather than searched type if value is true.
     *
     * @param filter value
     */
    public void setFilterMode(boolean filter);

    /**
     * @return boolean value of Filtermode
     */
    public boolean getFilterMode();
}
