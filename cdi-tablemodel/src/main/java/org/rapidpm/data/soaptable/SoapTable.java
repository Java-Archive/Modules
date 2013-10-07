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

package org.rapidpm.data.soaptable;

/**
 * Sven Ruppert
 * User: svenruppert
 * Date: 09.02.11
 * Time: 12:42
 * This is part of the PrometaJava project please contact chef@sven-ruppert.de
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

public class SoapTable {
    private static final Logger logger = Logger.getLogger(SoapTable.class);

    private List<Row> rowlist = new ArrayList<>();
    private List<ColInfo> colinfolist = new ArrayList<>();


    public List<Row> getRowlist() {
        return rowlist;
    }

    public void setRowlist(final List<Row> rowlist) {
        this.rowlist = rowlist;
    }

    public List<ColInfo> getColinfolist() {
        return colinfolist;
    }

    public void setColinfolist(final List<ColInfo> colinfolist) {
        this.colinfolist = colinfolist;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SoapTable");
        sb.append("{colEntryList=").append(rowlist);
        sb.append(", colinfolist=").append(colinfolist);
        sb.append('}');
        return sb.toString();
    }

    public static class ColInfo {
        private int colNr;
        private String colname;
        private ColInfoType coltype = ColInfoType.STRING;

        public ColInfo() {
        }

        @Override public int hashCode() {
            return Objects.hash(colNr, colname, coltype);
        }

        @Override public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final ColInfo other = (ColInfo) obj;
            return Objects.equals(this.colNr, other.colNr) && Objects.equals(this.colname, other.colname) && Objects.equals(this.coltype, other.coltype);
        }

//        @Override
//        public int hashCode() {
//            int result = colNr;
//            result = 31 * result + colname.hashCode();
//            result = 31 * result + coltype.hashCode();
//            return result;
//        }
//
//        @Override
//        public String toString() {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("ColInfo");
//            sb.append("{colNr=").append(colNr);
//            sb.append(", colname='").append(colname).append('\'');
//            sb.append(", coltype=").append(coltype);
//            sb.append('}');
//            return sb.toString();
//        }

        public int getColNr() {
            return colNr;
        }

        public void setColNr(final int colNr) {
            this.colNr = colNr;
        }

        public String getColname() {
            return colname;
        }

        public void setColname(final String colname) {
            this.colname = colname;
        }

        public ColInfoType getColtype() {
            return coltype;
        }

//        public void setColtype(final ColInfoType coltype){
//            this.coltype = coltype;
//        }
    }

    public static enum ColInfoType {
        STRING,
        //        NUMBER,
        URL,
//        OBJREF
    }

    public static class Row {
        public static class ColEntry {
            private int colEntryNr;
            private String value;

            public ColEntry(final int colEntryNr, final String value) {
                this.colEntryNr = colEntryNr;
                this.value = value;
            }

            public ColEntry() {
            }

            @Override public int hashCode() {
                return Objects.hash(colEntryNr, value);
            }

            @Override public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                final ColEntry other = (ColEntry) obj;
                return Objects.equals(this.colEntryNr, other.colEntryNr) && Objects.equals(this.value, other.value);
            }

//            @Override
//            public int hashCode() {
//                int result = colEntryNr;
//                result = 31 * result + value.hashCode();
//                return result;
//            }
//
//            @Override
//            public String toString() {
//                final StringBuilder sb = new StringBuilder();
//                sb.append("ColEntry");
//                sb.append("{colEntryNr=").append(colEntryNr);
//                sb.append(", value='").append(value).append('\'');
//                sb.append('}');
//                return sb.toString();
//            }

            public int getColEntryNr() {
                return colEntryNr;
            }

            public void setColEntryNr(final int colEntryNr) {
                this.colEntryNr = colEntryNr;
            }

            public String getValue() {
                return value;
            }

            public void setValue(final String value) {
                this.value = value;
            }
        }

        private List<ColEntry> colEntryList;
        private int rowNr;

        public Row() {
            this.colEntryList = new ArrayList<>();
            this.rowNr = -1;
        }

        public Row(final int rowNr) {
            this.colEntryList = new ArrayList<>();
            this.rowNr = rowNr;
        }

        @Override public int hashCode() {
            return Objects.hash(colEntryList, rowNr);
        }

        @Override public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Row other = (Row) obj;
            return Objects.equals(this.colEntryList, other.colEntryList) && Objects.equals(this.rowNr, other.rowNr);
        }

//        @Override
//        public int hashCode() {
//            return colEntryList.hashCode();
//        }
//
//        @Override
//        public String toString() {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("Row");
//            sb.append("{colEntryList=").append(colEntryList);
//            sb.append('}');
//            return sb.toString();
//        }

        public List<ColEntry> getColEntryList() {
            return colEntryList;
        }

        public void setColEntryList(final List<ColEntry> colEntryList) {
            this.colEntryList = colEntryList;
        }
    }

}
