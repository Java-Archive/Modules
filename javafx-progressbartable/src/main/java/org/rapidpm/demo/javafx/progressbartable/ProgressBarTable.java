package org.rapidpm.demo.javafx.progressbartable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.validation.constraints.NotNull;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * User: Sven Ruppert
 * Date: 19.06.13
 * Time: 11:02
 */
public class ProgressBarTable {

    private static final int COL_STEP_WIDTH = 150;
    private static final int COL_STATUS_WIDTH = 150;
    private TableView<WaitableTask> table = createTable();

    public Stage createStage(){
         final BorderPane root = new BorderPane();
         root.setPrefHeight(28 + (table.getItems().size() * 24));
         root.setPrefWidth(COL_STEP_WIDTH + COL_STATUS_WIDTH + 128);
         root.setMinWidth(COL_STEP_WIDTH + COL_STATUS_WIDTH + 128);
         root.setCenter(table);
         final Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.initStyle(StageStyle.UNDECORATED);
         stage.initModality(Modality.APPLICATION_MODAL);
         return stage;
     }

    public void addTask(@NotNull final WaitableTask task){
        table.getItems().add(task);
    }

    private TableView<WaitableTask> createTable() {
        TableView<WaitableTask> table = new TableView<>();

        final TableColumn<WaitableTask, String> stepCol = new TableColumn("Step");  //JIRA MOD-47 CDI i18n
        stepCol.setCellValueFactory(new PropertyValueFactory<WaitableTask, String>("title"));
        stepCol.setPrefWidth(COL_STEP_WIDTH);
        stepCol.setMinWidth(COL_STEP_WIDTH);

        final TableColumn<WaitableTask, String> statusCol = new TableColumn("Status"); //JIRA MOD-48 CDI i18n
        statusCol.setCellValueFactory(new PropertyValueFactory<WaitableTask, String>("message"));
        statusCol.setPrefWidth(COL_STATUS_WIDTH);
        statusCol.setMinWidth(COL_STATUS_WIDTH);

        final TableColumn<WaitableTask, Double> progressCol = new TableColumn("Progress");
        progressCol.setCellValueFactory(new PropertyValueFactory<WaitableTask, Double>("progress"));   //JIRA MOD-49 CDI i18n
        progressCol.setCellFactory(ProgressBarTableCell.<WaitableTask>forTableColumn());


        table.getColumns().addAll(stepCol, statusCol, progressCol);
//        table.getColumns().addAll(progressCol);
        table.setEditable(false);
        return table;
    }

    public void execute(){
        ExecutorService executor = Executors.newFixedThreadPool(table.getItems().size(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                final Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        for (WaitableTask task : table.getItems()) {
            executor.execute(task);
        }
    }




    public static class WaitableTask extends Task<Void>{

        private final String taskname;
        private boolean waiting = true;

        private List<Step> steps = new ArrayList<>();

        public WaitableTask(final String taskname) {
            this.taskname = taskname;
        }

        public boolean isWaiting() {
            return waiting;
        }

        public void setWaiting(boolean waiting) {
            this.waiting = waiting;
        }

        public List<Step> getSteps() {
            return steps;
        }

        @Override
        protected Void call() throws Exception {
            this.updateTitle(taskname);
            this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);
            this.updateMessage("warte...");

            int i = 1;
            for (final Step step : steps) {
                this.updateMessage(step.getMessage());
                this.updateProgress(i, steps.size());
                i= i+1;
                step.doIt();
            }

            this.updateMessage("fertig");
            this.updateProgress(1, 1);
            return null;
        }
    }

    public static abstract class Step{
        private String message;

        protected Step(String messgae) {
            this.message = messgae;
        }

        public abstract void doIt();


        public String getMessage() {
            return this.message;
        }
    }
}
