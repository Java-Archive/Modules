/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
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

package org.arangodb.objectmapper;

import java.util.ArrayList;
import java.util.List;

public class PropertySort {
	
	/*
	 * compare functions
	 */
	
    public enum Direction {ASCENDING, DESCENDING};
	
    private List<PropertyContainer> propertyContainers = new ArrayList<PropertyContainer>();
    
    public PropertySort sort(final String key, final Direction direction) {
        this.propertyContainers.add(new PropertyContainer(key, direction));
        return this;
    }

    public String getSortString () {
    	if (propertyContainers.isEmpty()) {
    		return "";
    	}
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" SORT ");
    	    	
    	int i = 1; 
    	
    	for (final PropertyContainer container : propertyContainers) {
    		if (i > 1) {
        		sb.append(", ");
    		}
    		
    		sb.append(" x.`");
    		sb.append(container.key);
    		sb.append("` ");
    		
            switch (container.direction) {
            case ASCENDING:
            	sb.append("ASC");
            	break;
            case DESCENDING:
            	sb.append("DESC");
            	break;
            }

            i++;
    	}
    	
    	return sb.toString();
    }

    private class PropertyContainer {
        public String key;
        public Direction direction;

        public PropertyContainer(final String key, final Direction direction) {
            this.key = key.replace("\"", "\\\"");
            this.direction = direction;
        }
    }

}
