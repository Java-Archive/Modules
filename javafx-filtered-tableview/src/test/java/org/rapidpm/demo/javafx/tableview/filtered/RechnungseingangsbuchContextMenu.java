package org.rapidpm.demo.javafx.tableview.filtered;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.rapidpm.demo.cdi.commons.logger.Logger;

import siteos.leasware.client.gui.rechnungseingangsbuch.model.TransientRechnung;
import siteos.leasware.util.ExcelUtil;

/**
 * User: Sven Ruppert Date: 14.08.13 Time: 16:30
 */
public class RechnungseingangsbuchContextMenu extends ContextMenu {

    private static final Logger logger = Logger.getLogger(RechnungseingangsbuchContextMenu.class);
//    private @Inject @CDILogger Logger logger;

    public RechnungseingangsbuchContextMenu() {

    }

    private FilteredTableView<TransientRechnung> rechnungseingangsbuchTabelle;

//    @PostConstruct
    public void init(){
        MenuItem openCSV = new MenuItem("Als CSV öffnen");
        openCSV.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {

                    @Override public void run() {
                        try {
                            final StringBuilder stringBuilder = convertTable2CSV();
                            final File temp = File.createTempFile("xls-temp-" + System.nanoTime(), ".csv");
                            final FileOutputStream fos = new FileOutputStream(temp);
                            fos.write(stringBuilder.toString().getBytes());
                            fos.flush();
                            fos.close();
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(temp);
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });
        MenuItem saveCSV = new MenuItem("Als CSV speichern");
        saveCSV.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        try {
                            final StringBuilder stringBuilder = convertTable2CSV();
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Open Resource File");
                            fileChooser.setInitialDirectory(
                                    new File(System.getProperty("user.home"))
                            );
                            final File targetFile = fileChooser.showSaveDialog(rechnungseingangsbuchTabelle.getScene().getWindow());
                            if (targetFile != null) {
                                final FileOutputStream fos = new FileOutputStream(targetFile);
                                fos.write(stringBuilder.toString().getBytes());
                                fos.flush();
                                fos.close();
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("keine Ziel ausgewählt..");
                                }
                            }
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });
        final MenuItem openXlS = new MenuItem("Als XLS öffnen");
        openXlS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (logger.isDebugEnabled()) {
                    logger.debug(openXlS.toString());
                }
                Platform.runLater(new Runnable() {

                    @Override public void run() {
                        try {
                            final byte[] bytes = convertTable2Xls();
                            final File temp = File.createTempFile("xls-temp-" + System.nanoTime(), ".xls");
                            final FileOutputStream fos = new FileOutputStream(temp);
                            fos.write(bytes);
                            fos.flush();
                            fos.close();
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(temp);
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });


        final MenuItem saveXlS = new MenuItem("Als XLS speichern");
        saveXlS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        try {
                            final byte[] bytes = convertTable2Xls();
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Open Resource File");
                            fileChooser.setInitialDirectory(
                                    new File(System.getProperty("user.home"))
                            );
                            final File targetFile = fileChooser.showSaveDialog(rechnungseingangsbuchTabelle.getScene().getWindow());
                            if (targetFile != null) {
                                final FileOutputStream fos = new FileOutputStream(targetFile);
                                fos.write(bytes);
                                fos.flush();
                                fos.close();
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("keine Ziel ausgewählt..");
                                }
                            }
                        } catch (IOException e1) {
                            logger.error(e1);
                        }
                    }
                });
            }
        });
        MenuItem copyTableCSV2Clipboard = new MenuItem("als CSV in die Zwischenablage");
        copyTableCSV2Clipboard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        final StringBuilder clipboardString = convertTable2CSV();
                        final ClipboardContent content = new ClipboardContent();
                        content.putString(clipboardString.toString());
                        Clipboard.getSystemClipboard().setContent(content);
                    }
                });
            }
        });


        getItems().addAll(openCSV, saveCSV, openXlS, saveXlS, copyTableCSV2Clipboard);
    }


    private StringBuilder convertTable2CSV() {
        final StringBuilder clipboardString = new StringBuilder();
        final ObservableList<TableColumn<TransientRechnung, ?>> columns = rechnungseingangsbuchTabelle.getColumns();
        for (final TableColumn<TransientRechnung, ?> column : columns) {
            clipboardString.append(column.getText() + ";");
        }
        clipboardString.append('\n');
        final ObservableList<TransientRechnung> items = rechnungseingangsbuchTabelle.getItems();

        for (final TransientRechnung item : items) {
            final String rechnungseingang = item.getRechnungseingang();
            clipboardString.append(rechnungseingang + ";");
            final String kreditor = item.getKreditor();
            clipboardString.append(kreditor + ";");
            final String rechnungsnummer = item.getRechnungsnummer();
            clipboardString.append(rechnungsnummer + ";");
            final double betrag = item.getBetrag();
            final String betragEUR = NumberFormat.getCurrencyInstance(Locale.GERMANY).format(betrag);
            clipboardString.append(betragEUR + ";");

            final String abteilung = item.getAbteilung();
            clipboardString.append(abteilung + ";");
            final String vermerk = item.getVermerk();
            clipboardString.append(vermerk + ";");
            final String ersteller = item.getErsteller();
            clipboardString.append(ersteller + ";");
            final String erstellungsDatum = item.getErstellungsDatum();
            clipboardString.append(erstellungsDatum + ";");
            final String bearbeiter = item.getBearbeiter();
            clipboardString.append(bearbeiter + ";");
            final String bearbeitungsDatum = item.getBearbeitungsDatum();
            clipboardString.append(bearbeitungsDatum + ";");
            final String status = item.getStatus();
            clipboardString.append(status + ";");
            final String zahlungsstatus = item.getZahlungsstatus();
            clipboardString.append(zahlungsstatus + ";");
            clipboardString.append('\n');
        }
        return clipboardString;
    }

    private byte[] convertTable2Xls() throws IOException {
        final ObservableList<TableColumn<TransientRechnung, ?>> columns = rechnungseingangsbuchTabelle.getColumns();
        final HSSFWorkbook wb = new HSSFWorkbook();
        final HSSFCellStyle csHeader = ExcelUtil.createCellStyle(wb, HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.WHITE.index, null, false);
        csHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        csHeader.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
        csHeader.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        csHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        final HSSFSheet sheet = ExcelUtil.getOrCreateSheet(wb, "Eingansgbuch");
        int row = 0;
        short colNr = 0;
        for (final TableColumn<TransientRechnung, ?> column : columns) {
            final String columnText = column.getText();
            ExcelUtil.setCellValue(sheet, row, colNr, columnText, csHeader);
            colNr++;
        }
        row++;

        final HSSFCellStyle csData = ExcelUtil.createCellStyle(wb, HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index, null, false);

        //data
        final ObservableList<TransientRechnung> items = rechnungseingangsbuchTabelle.getItems();
        for (final TransientRechnung item : items) {
            final String rechnungseingang = item.getRechnungseingang();
            ExcelUtil.setCellValue(sheet, row, (short) 0, rechnungseingang, csData);
            final String kreditor = item.getKreditor();
            ExcelUtil.setCellValue(sheet, row, (short) 1, kreditor, csData);
            final String rechnungsnummer = item.getRechnungsnummer();
            ExcelUtil.setCellValue(sheet, row, (short) 2, rechnungsnummer, csData);
            final double betrag = item.getBetrag();
//                        final String betragEUR = NumberFormat.getCurrencyInstance(Locale.GERMANY).format(betrag);

            HSSFDataFormat cf = wb.createDataFormat();
            HSSFCellStyle currencyCellStyle = wb.createCellStyle();
            currencyCellStyle.setDataFormat(cf.getFormat("#,##0.00\\ €"));
            ExcelUtil.setCellValue(sheet, row, (short) 3, betrag, currencyCellStyle);

            final String abteilung = item.getAbteilung();
            ExcelUtil.setCellValue(sheet, row, (short) 4, abteilung, csData);
            final String vermerk = item.getVermerk();
            ExcelUtil.setCellValue(sheet, row, (short) 5, vermerk, csData);
            final String ersteller = item.getErsteller();
            ExcelUtil.setCellValue(sheet, row, (short) 6, ersteller, csData);
            final String erstellungsDatum = item.getErstellungsDatum();
            ExcelUtil.setCellValue(sheet, row, (short) 7, erstellungsDatum, csData);
            final String bearbeiter = item.getBearbeiter();
            ExcelUtil.setCellValue(sheet, row, (short) 8, bearbeiter, csData);
            final String bearbeitungsDatum = item.getBearbeitungsDatum();
            ExcelUtil.setCellValue(sheet, row, (short) 9, bearbeitungsDatum, csData);
            final String status = item.getStatus();
            ExcelUtil.setCellValue(sheet, row, (short) 10, status, csData);
            final String zahlungsstatus = item.getZahlungsstatus();
            ExcelUtil.setCellValue(sheet, row, (short) 11, zahlungsstatus, csData);
            row++;
        }
        return ExcelUtil.wbToByteArray(wb);
    }

    public void setRechnungseingangsbuchTabelle(FilteredTableView<TransientRechnung> rechnungseingangsbuchTabelle) {
        this.rechnungseingangsbuchTabelle = rechnungseingangsbuchTabelle;
    }
}
