package org.rapidpm.demo.cdi.commons.se.filesystem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.format.CDISimpleDateFormatter;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 09.06.13
 * Time: 17:33
 * <p/>
 * TargetFile based on the given date. The File will be in an dir
 * with the following structure.
 * <p/>
 * yyyy
 * MM
 * dd
 * For example 30.12.2010 would be transformed into an dir
 * 2010
 * 12
 * 30 this file would be given back.
 */
public class TargetDateFile {

    private @Inject @CDILogger Logger logger;

    @Inject
    @CDISimpleDateFormatter("date.yyyy")
    private SimpleDateFormat sdfYYYY;
    @Inject
    @CDISimpleDateFormatter("date.MM")
    private SimpleDateFormat sdfMM;
    @Inject
    @CDISimpleDateFormatter("date.dd")
    private SimpleDateFormat sdfdd;


    public File createDailyDir(File baseDir, Date date) {
        final File yyyy = new File(baseDir, sdfYYYY.format(date));
        final File mm = new File(yyyy, sdfMM.format(date));
        final File dd = new File(mm, sdfdd.format(date));
        if (yyyy.exists()) {
            //
        } else {
            yyyy.mkdir();
        }

        if (mm.exists()) {
        } else {
            mm.mkdir();
        }
        if (dd.exists()) {
        } else {
            dd.mkdir();
        }
        return dd;
    }

}
