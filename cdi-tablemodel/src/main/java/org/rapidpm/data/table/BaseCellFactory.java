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

package org.rapidpm.data.table;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rapidpm.data.SelectList;
import org.rapidpm.data.table.annotation.FactoryFor;
import org.rapidpm.data.table.cellvaluefactory.Factory;
import org.rapidpm.data.table.cellvaluefactory.FactoryRegistry;

/**
 * Sven Ruppert - www.svenruppert.de
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 28.03.2010
 * Time: 21:32:53
 */

public class BaseCellFactory {
    private static final Logger logger = Logger.getLogger(BaseCellFactory.class);

    protected final Map<Class, TypedCellFactory> class2CellMap = new HashMap<>();

    public BaseCellFactory() {
        class2CellMap.put(String.class, new StringCellFactory());
        class2CellMap.put(Integer.class, new IntegerCellFactory());
        class2CellMap.put(Float.class, new FloatCellFactory());
        class2CellMap.put(Double.class, new DoubleCellFactory());
        class2CellMap.put(Long.class, new LongCellFactory());
        class2CellMap.put(Boolean.class, new BooleanCellFactory());
        class2CellMap.put(Date.class, new DateCellFactory());
        class2CellMap.put(Timestamp.class, new DateCellFactory());

        class2CellMap.put(List.class, new ListCellFactory());
        class2CellMap.put(Collection.class, new ListCellFactory());//JIRA MOD-25 ListCellFactory ersetzen durch CollectionCellFactory
        class2CellMap.put(ArrayList.class, new ListCellFactory());
        class2CellMap.put(LinkedList.class, new ListCellFactory());

        class2CellMap.put(HashSet.class, new IterableCellFactory());
        class2CellMap.put(Set.class, new IterableCellFactory());
        class2CellMap.put(Collection.class, new IterableCellFactory());

//        class2CellMap.put(PersistentBag.class, new ListCellFactory());
//        class2CellMap.put(PersistentList.class, new ListCellFactory());
//        class2CellMap.put(PersistentSet.class, new ListCellFactory());
//        class2CellMap.put(PersistentMap.class, new ListCellFactory());

//        class2CellMap.put(GenderEnum.class, new GenderEnumCellFactory());

        class2CellMap.put(SelectList.class, new SelectListFactory());

    }

    //    public <T> void registerNewCellForClass(T clazz){
    //        final Cell<T> tCell = new Cell<T>();
    //        final Class<? extends Cell> cellClass = tCell.getClass();
    //        class2CellMap.put(clazz.getClass(), tCell);
    //    }


    //JIRA MOD-26 Null values verarbeiten bei createNewCell(final C value, final String valueContext)

    public <C> Cell<C> createNewCell(final C value, final String valueContext) {
        final Cell<C> cell;
        final Class<? extends Object> aClass = value.getClass();
        final TypedCellFactory cellFactory = class2CellMap.get(aClass);
        cell = cellFactory.newInstance();
        cell.setValue(value);
        cell.setValueContext(valueContext);
        return cell;
    }

    public <C> Cell<C> createNewCell(final C value, final String label, final String valueContext) {
        final Cell<C> cell;
        final Class<? extends Object> aClass = value.getClass();
        final TypedCellFactory cellFactory = class2CellMap.get(aClass);
        cell = cellFactory.newInstance();
        cell.setValue(value);
        cell.setLabel(label);
        cell.setValueContext(valueContext);
        return cell;
    }

    //JIRA MOD-27 Null values verarbeiten bei createNewCell(final C value)

    public <C> Cell<C> createNewCell(final C value) {
        final Cell<C> cell;
        if (value == null) {
            cell = new Cell();
        } else {
            final Class<? extends Object> aClass = value.getClass();
            final TypedCellFactory cellFactory = class2CellMap.get(aClass);
            if (cellFactory == null) {
                cell = new Cell();
                logger.warn("Typedef fehlt in CellFactory " + aClass.getName());
            } else {
                cell = cellFactory.newInstance();
                cell.setValue(value);
            }
        }
        return cell;
    }

    public Cell createNewEmptyCell(final Class cellValueClass) {
        Cell<Object> cell = null;
        final AbstractRegistry<FactoryFor, Factory<?>> registry = Registry.get(FactoryRegistry.class);
        if (registry.hasClassFor(cellValueClass)) {
            final Factory factoryFor = registry.getClassFor(cellValueClass);
            try {
                final Object object = factoryFor.createInstance();
                cell = createNewCell(object);
            } catch (InstantiationException e) {
                logger.error(e);
            }
        } else {
            try {
                final Object object = cellValueClass.newInstance();
                cell = createNewCell(object);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e);
            }
        }
        return cell;
    }

    protected interface TypedCellFactory<T> {
        public Cell<T> newInstance();
    }

    //JIRA MOD-28 laden und assoziieren per annotation

    private class IterableCellFactory implements TypedCellFactory<Iterable> {
        @Override
        public Cell<Iterable> newInstance() {
            return new Cell<>();
        }
    }

    private class ListCellFactory implements TypedCellFactory<List> {
        @Override
        public Cell<List> newInstance() {
            return new Cell<>();
        }
    }


    private class StringCellFactory implements TypedCellFactory<String> {
        @Override
        public Cell<String> newInstance() {
            return new Cell<>();
        }
    }

    private class IntegerCellFactory implements TypedCellFactory<Integer> {
        @Override
        public Cell<Integer> newInstance() {
            return new Cell<>();
        }
    }

    private class FloatCellFactory implements TypedCellFactory<Float> {
        @Override
        public Cell<Float> newInstance() {
            return new Cell<>();
        }
    }

    private class DoubleCellFactory implements TypedCellFactory<Double> {
        @Override
        public Cell<Double> newInstance() {
            return new Cell<>();
        }
    }

    private class LongCellFactory implements TypedCellFactory<Long> {
        @Override
        public Cell<Long> newInstance() {
            return new Cell<>();
        }
    }

    private class BooleanCellFactory implements TypedCellFactory<Boolean> {
        @Override
        public Cell<Boolean> newInstance() {
            return new Cell<>();
        }
    }

    private class DateCellFactory implements TypedCellFactory<Date> {
        @Override
        public Cell<Date> newInstance() {
            return new Cell<>();
        }
    }


    private class SelectListFactory implements TypedCellFactory<SelectList> {
        @Override
        public Cell<SelectList> newInstance() {
            return new Cell<>();
        }
    }

}
