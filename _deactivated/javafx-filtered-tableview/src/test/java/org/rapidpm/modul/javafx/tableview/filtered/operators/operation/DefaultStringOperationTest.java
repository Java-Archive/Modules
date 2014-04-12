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

package org.rapidpm.modul.javafx.tableview.filtered.operators.operation;


import org.junit.Assert;
import org.rapidpm.modul.javafx.tableview.filtered.operators.StringOperator;
import org.rapidpm.modul.javafx.tableview.filtered.operators.operation.DefaultStringOperation;

/**
 * Created with IntelliJ IDEA.
 * User: Sven Ruppert
 * Date: 03.05.13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class DefaultStringOperationTest {

    @org.junit.Test
    public void testNONE01() throws Exception {
        final DefaultStringOperation defOp = new DefaultStringOperation();
        final StringOperator strOp = new StringOperator(StringOperator.Type.NONE, "");
        Assert.assertFalse(defOp.check(strOp, "ABCDEF"));
        Assert.assertFalse(defOp.check(strOp, ""));
        Assert.assertFalse(defOp.check(strOp, null));
    }

    @org.junit.Test
    public void testEQUALS01() throws Exception {
        final DefaultStringOperation defOp = new DefaultStringOperation();
        final StringOperator strOp = new StringOperator(StringOperator.Type.EQUALS, "ABCDEF");
        Assert.assertTrue(defOp.check(strOp, "ABCDE"));
        Assert.assertFalse(defOp.check(strOp, "ABCDEF"));
        Assert.assertTrue(defOp.check(strOp, ""));
        Assert.assertTrue(defOp.check(strOp, null));
    }

    @org.junit.Test
    public void testNOTEQUALS01() throws Exception {
        final DefaultStringOperation defOp = new DefaultStringOperation();
        final StringOperator strOp = new StringOperator(StringOperator.Type.NOTEQUALS, "ABCDEF");
        Assert.assertFalse(defOp.check(strOp, "ABCDE"));
        Assert.assertTrue(defOp.check(strOp, "ABCDEF"));
        Assert.assertFalse(defOp.check(strOp, ""));
        Assert.assertTrue(defOp.check(strOp, null));
    }

    @org.junit.Test
    public void testCONTAINS01() throws Exception {
        final DefaultStringOperation defOp = new DefaultStringOperation();
        final StringOperator strOp = new StringOperator(StringOperator.Type.CONTAINS, "ABCDEF");
        Assert.assertTrue(defOp.check(strOp, "ABCDE"));
        Assert.assertTrue(defOp.check(strOp, ""));
        Assert.assertTrue(defOp.check(strOp, null));

        Assert.assertFalse(defOp.check(strOp, "ABCDEFG"));
        Assert.assertFalse(defOp.check(strOp, "ABCDEF"));
    }

    @org.junit.Test
    public void testSTARTSWITH01() throws Exception {
        final DefaultStringOperation defOp = new DefaultStringOperation();
        final StringOperator strOp = new StringOperator(StringOperator.Type.STARTSWITH, "ABCDEF");
        Assert.assertTrue(defOp.check(strOp, "ABCDE"));
        Assert.assertTrue(defOp.check(strOp, ""));
        Assert.assertTrue(defOp.check(strOp, null));

        Assert.assertFalse(defOp.check(strOp, "ABCDEFG"));
        Assert.assertFalse(defOp.check(strOp, "ABCDEF"));
    }

    @org.junit.Test
    public void testENDSWITHH01() throws Exception {
        final DefaultStringOperation defOp = new DefaultStringOperation();
        final StringOperator strOp = new StringOperator(StringOperator.Type.ENDSWITH, "ABCDEFG");
        Assert.assertTrue(defOp.check(strOp, "ABCDE"));
        Assert.assertTrue(defOp.check(strOp, ""));
        Assert.assertTrue(defOp.check(strOp, null));

        Assert.assertFalse(defOp.check(strOp, "ABCDEFG"));
        Assert.assertTrue(defOp.check(strOp, "ABCDEF"));
    }


}
