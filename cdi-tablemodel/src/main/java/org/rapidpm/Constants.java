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

package org.rapidpm;

/**
 * NeoScio
 * @author svenruppert
 * @since 05.08.2008
 * Time: 17:42:55
 * This Source Code is part of the www.svenruppert.de  project.
 * please contact sven.ruppert@web.de
 *
 */

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class Constants {
    private static final Logger logger = Logger.getLogger(Constants.class);


    public static final String PROTOCOL_IDENTIFIER = "://";
    public static final String[] DEFAULT_PROTOCOLS = {"http", "https", "ftp", "file", "hdfs"};
    public static final String HTTP = DEFAULT_PROTOCOLS[0] + PROTOCOL_IDENTIFIER;
    public static final String HTTPS = DEFAULT_PROTOCOLS[1] + PROTOCOL_IDENTIFIER;
    public static final String FTP = DEFAULT_PROTOCOLS[2] + PROTOCOL_IDENTIFIER;
    public static final String FILE = DEFAULT_PROTOCOLS[3] + PROTOCOL_IDENTIFIER;
    public static final String HDFS = DEFAULT_PROTOCOLS[4] + PROTOCOL_IDENTIFIER;

    public static final String DEFAULT_CHARSET = "ISO-8859-1";


    public static final char LINE_BREAK = '\n';

    public static final String YYYY_MM_DD = "yyyy-MM-dd";      //TODO CDI i18n
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd-HH";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd-HH-mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd-HH-mm-ss";
    public static final DecimalFormat DECIMALFORMAT_6_STELLEN = new DecimalFormat("######");
    public static final DecimalFormat FLOATFORMAT_3_NACHKOMMA_STELLEN = new DecimalFormat(",###");

}
