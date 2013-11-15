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

package org.rapidpm.modul.javafx.progressbartable;

/**
 * User: Sven Ruppert
 * Date: 19.06.13
 * Time: 10:36
 */

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressBarTableCellTest extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final TableView<WorkingTask> table = createTable();

        Random rng = new Random();
        for (int i = 0; i < 20; i++) {
            final int waitTime = rng.nextInt(3000) + 2000;
            final int pauseTime = rng.nextInt(30) + 20;
            final WorkingTask workingTask = new WorkingTask(waitTime, pauseTime, "Step_" + i);
            table.getItems().add(workingTask);
        }

        final BorderPane root = new BorderPane();
        root.setPrefHeight(28 + (20 * 24));
        root.setPrefWidth(75 + 75 + 128);
        root.setCenter(table);
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        ExecutorService executor = Executors.newFixedThreadPool(table.getItems().size(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                final Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });


        for (WorkingTask task : table.getItems()) {
            executor.execute(task);
        }
    }

    private TableView<WorkingTask> createTable() {
        TableView<WorkingTask> table = new TableView<>();

        final TableColumn<WorkingTask, String> stepCol = new TableColumn("Step");
        stepCol.setCellValueFactory(new PropertyValueFactory<WorkingTask, String>("title"));
        stepCol.setPrefWidth(75);

        final TableColumn<WorkingTask, String> statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<WorkingTask, String>("message"));
        statusCol.setPrefWidth(75);

        final TableColumn<WorkingTask, Double> progressCol = new TableColumn("Progress");
        progressCol.setCellValueFactory(new PropertyValueFactory<WorkingTask, Double>("progress"));
        progressCol.setCellFactory(ProgressBarTableCell.<WorkingTask>forTableColumn());


        table.getColumns().addAll(stepCol, statusCol, progressCol);
        return table;
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class WorkingTask extends Task<Void> {

        private final int waitTime; // milliseconds
        private final int pauseTime; // milliseconds
        private final String title;

        public static final int NUM_ITERATIONS = 100;

        WorkingTask(int waitTime, int pauseTime, String title) {
            this.waitTime = waitTime;
            this.pauseTime = pauseTime;
            this.title = title;
        }

        @Override
        protected Void call() throws Exception {
            this.updateTitle(title);
            this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);
            this.updateMessage("Waiting...");
            Thread.sleep(waitTime);
            this.updateMessage("Running..."); //setze Status
            for (int i = 0; i < NUM_ITERATIONS; i++) {
                updateProgress((1.0 * i) / NUM_ITERATIONS, 1);
                Thread.sleep(pauseTime);
            }
            this.updateMessage("Done");
            this.updateProgress(1, 1);
            return null;
        }

    }
}