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

package org.rapidpm.data.table.converter.totable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.rapidpm.data.table.Table;
import org.rapidpm.data.table.TableCreator;

/**
 * Sven Ruppert - www.svenruppert.de
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 22.06.2010
 * Time: 23:21:55
 * <p/>
 * <p/>
 * Dieser Converter kann NICHT einfacheDatentypen wie int und char verarbeiten.
 * Des weiteren werden einfache Lsiten von z.B. Date dazu führen, das der Date in seine Einzelteile zerpflückt wird.
 * <p/>
 * Also nur komplexe Klassen..
 */

public abstract class GenericList2TableConverter extends Abstract2TableConverter<Object> {
    private static final Logger logger = Logger.getLogger(GenericList2TableConverter.class);
    public static final String ZUWEISEN = "zuweisen";
    public static final String EDITIEREN = "editieren";

    private static final String postExtensionZuweisung = "Zuweisung";
    private static final String postExtensionEdit = "Edit";


    private String webAppCtx = "";

    public GenericList2TableConverter() {
    }

    public GenericList2TableConverter(final String webAppCtx) {
        this.webAppCtx = webAppCtx;
    }

    @Override
    protected void setTableName(final Table table) {
        table.setTableName("generic transformed table");
    }


    @Override
    protected <C extends ConfigElements> void convertImpl(final TableCreator tableCreator, final Object entity, final C config) {
        convert2Table(tableCreator, entity);
    }


    private void convert2Table(final TableCreator tableCreator, final Object... valueList) {

        final int length = valueList.length;
        if ((length > 0)) {
            final List<Object> objList = new ArrayList<>();
            final Object o1 = valueList[0];  // hier ist schon der Inhalt
            if (!(o1 instanceof Iterable)) {
                //atomarer Wert
                final boolean isArray = o1.getClass().isArray();
                if (isArray) {
                    //                    final Class<?> type = o1.getClass().getComponentType();
                    final int arrayLength = Array.getLength(o1);
                    for (int i = 0; i < arrayLength; i++) {
                        final Object arrayElement = Array.get(o1, i);
                        objList.add(arrayElement);
                    }

                } else {
                    objList.add(o1);
                }
            } else {
                //Liste von...  [1] - fieldName == elementData dort ist die Liste enthalten
                final Iterable<Object> elementData = (Iterable<Object>) o1;
                for (final Object o : elementData) {
                    objList.add(o);
                }
            }

            //einmal ColInfo erzeugen
            if (objList.isEmpty()) {
                //liste leer...
            } else {
                final Object firstObject = objList.get(0);
                final Class firstObjectClass = firstObject.getClass();

                //java.lang ??
                final Field[] fields = firstObjectClass.getDeclaredFields();
                for (final Field field : fields) {
                    final int modifiers = field.getModifiers();
                    if (!Modifier.isStatic(modifiers)) {
                        if (Modifier.isPrivate(modifiers)) {
                            final Class<?> type = field.getType();
                            final String fieldName = field.getName();
                            tableCreator.addNextColInfo(fieldName.toUpperCase(), type, true, true); //JIRA MOD-31 steuern der ColNamen - Schreibweise
                        } else {
                            //
                        }
                    } else {
                        //not Accessible
                    }
                }
                tableCreator.addNextColInfo(EDITIEREN, String.class, false, false);

                for (final Object actualEntity : objList) {
                    final Long objId = getObjIdIfAvailable(actualEntity);
                    if (objId == null) {
                        //keine ID im Object...
                    } else {
                        tableCreator.addNewRow();
                        for (final Field field : fields) {
                            final int modifiers = field.getModifiers();
                            if (!Modifier.isStatic(modifiers)) {
                                if (Modifier.isPrivate(modifiers)) {
                                    final String fieldName = field.getName();
                                    if (fieldName == null) {
                                        //
                                    } else {
                                        try {
                                            field.setAccessible(true);
                                            final Object attributeObj = field.get(actualEntity);
                                            if (attributeObj == null) {
                                                //was machen mit null werten ?
                                            } else {
                                                //Liste oder einzelner Wert
                                                // aus svenruppert.orm ??
                                                final String ColName = fieldName.toUpperCase();
                                                //                                                if(attributeObj instanceof Iterable || attributeObj instanceof BeanCollection){
                                                if (attributeObj instanceof Iterable) {
                                                    //finde heraus welche Klasse enthalten ist.
                                                    final Iterable oIterable = (Iterable) attributeObj;
                                                    final Iterator iterator = oIterable.iterator();
                                                    if (iterator.hasNext()) {
                                                        //ist nicht leer..
                                                        final Object nextElement = iterator.next();
                                                        final boolean isNeoScioPkg = checkPkg(nextElement);
                                                        if (isNeoScioPkg) {
                                                            //umschalten auf Edit Feld Link
                                                            final Class<? extends Object> nextElementClass = nextElement.getClass();
                                                            final String pageLink = createPageLinkFor(nextElementClass, postExtensionZuweisung);
                                                            final String ctxValue = actualEntity.getClass().getName() + "/" + objId;
                                                            tableCreator.addNextCellRawLink(ColName, pageLink + "/" + ctxValue, ZUWEISEN);
                                                            tableCreator.getTable().getColumnInformationFor(ColName).setExportable(false);
                                                            tableCreator.getTable().getColumnInformationFor(ColName).setSortable(false);
                                                        } else {
                                                            //ein Fall f die Std Formatter...
                                                            tableCreator.addNextCellRawData(ColName, attributeObj);
                                                        }
                                                    } else {
                                                        //leere Menge...  einfach mal adden
                                                        tableCreator.addNextCellRawData(ColName, attributeObj);
                                                    }
                                                } else {
                                                    //komplex und v svenruppert ? dann Link
                                                    if (checkPkg(attributeObj)) {
                                                        final Class<? extends Object> aClass = attributeObj.getClass();
                                                        final String pageLink = createPageLinkFor(aClass, postExtensionZuweisung);
                                                        final String ctxValue = actualEntity.getClass().getName() + "/" + objId;
                                                        tableCreator.addNextCellRawLink(ColName, pageLink + "/" + ctxValue, ZUWEISEN);
                                                        tableCreator.getTable().getColumnInformationFor(ColName).setExportable(false);
                                                        tableCreator.getTable().getColumnInformationFor(ColName).setSortable(false);
                                                    } else {
                                                        //sonst..
                                                        tableCreator.addNextCellRawData(ColName, attributeObj);
                                                    }
                                                }
                                            }
                                            field.setAccessible(false);
                                        } catch (IllegalAccessException e) {
                                            logger.error(e);
                                        }
                                    }

                                } else {
                                    //
                                }
                            } else {
                                //not Accessible
                            }
                        }
                        //Edit Spalte..
                        //erzeuge PageLink   subpkg + Classanem + Edit;
                        if (checkPkg(actualEntity)) {
                            final Class<? extends Object> actualEntityClass = actualEntity.getClass();
                            final String pageLink = createPageLinkFor(actualEntityClass, postExtensionEdit);
                            tableCreator.addNextCellRawLink(EDITIEREN, pageLink + "/" + objId, EDITIEREN);
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Nicht aus dem Basis Pkg org.rapidpm.orm " + actualEntity.getClass().getName());
                            }
                        }
                    }
                }
            }

        } else {

        }

    }

    private Long getObjIdIfAvailable(final Object actualEntity) {
        Long objId = null;

        final Class<? extends Object> entityClass = actualEntity.getClass();
        final Method[] methods = entityClass.getMethods();
        for (final Method method : methods) {
            final String methodName = method.getName();
            if (methodName.equals("getId")) {
                final Class<?> type = method.getReturnType();

                try {
                    final Method methodBeanB = entityClass.getMethod("getId");
                    final Object o = methodBeanB.invoke(actualEntity);
                    if (o instanceof Long) {
                        objId = (Long) o;
                    } else {
                        //falscher typ
                    }
                    //                            break;
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    logger.error(e);
                }
            } else {
                //falsche Methode
            }
        }
        return objId;
    }


    private String createPageLinkFor(final Class<? extends Object> aClass, final String postExtension) {
        final String pkgName = aClass.getPackage().getName();
        final String ctxPath = webAppCtx + "/admin" + pkgName.replace("org.rapidpm.orm", "").replace(".", "/") + "/" + aClass.getSimpleName() + postExtension;
        return ctxPath;
    }

    private boolean checkPkg(final Object nextElement) {
        final Class<? extends Object> nextElementClass = nextElement.getClass();
        final Package aPackage = nextElementClass.getPackage();
        final String pkgname = aPackage.getName();
        final boolean isNeoScioPkg = pkgname.contains("org.rapidpm.orm");
        final boolean isEbeanPkg = pkgname.contains("com.avaje.ebean.bean");
        return isNeoScioPkg || isEbeanPkg;
    }


}
