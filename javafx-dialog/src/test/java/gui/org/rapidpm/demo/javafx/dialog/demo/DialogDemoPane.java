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

package gui.org.rapidpm.demo.javafx.dialog.demo;

import org.rapidpm.demo.cdi.commons.fx.components.CDIBaseAnchorPane;

/**
 * User: Sven Ruppert
 * Date: 10.09.13
 * Time: 14:39
 */
public class DialogDemoPane extends CDIBaseAnchorPane<DialogDemoPane, DialogDemoPaneController> {

    @Override public Class<DialogDemoPane> getPaneClass() {
        return DialogDemoPane.class;
    }


//    @Inject DialogDemoPaneController controller;
//
//    public DialogDemoPaneController getController() {
//        return controller;
//    }
//
//    public void setController(DialogDemoPaneController controller) {
//        this.controller = controller;
//    }
}
