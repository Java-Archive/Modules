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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyFilter {
	
	/*
	 * compare functions
	 */
	
    public enum Compare {EQUAL, NOT_EQUAL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL};
	
    private List<PropertyContainer> propertyContainers = new ArrayList<PropertyContainer>();
    
    public PropertyFilter has(final String key, final Object value, final Compare compare) {
        this.propertyContainers.add(new PropertyContainer(key, value, compare));
        return this;
    }

    public String getFilterString () {
    	if (propertyContainers.isEmpty()) {
    		return "";
    	}
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" FILTER ");
    	    	
    	int i = 1; 
    	
    	for (final PropertyContainer container : propertyContainers) {
    		if (i > 1) {
        		sb.append("&& ");
    		}
    		
    		sb.append(" x.`");
    		sb.append(container.key);
    		sb.append("` ");
    		
            switch (container.compare) {
            case EQUAL:
            	sb.append("==");
            	break;
            case NOT_EQUAL:
            	sb.append("!=");
            	break;
            case GREATER_THAN:
            	sb.append(">");
            	break;
            case LESS_THAN:
            	sb.append("<");
            	break;
            case GREATER_THAN_EQUAL:
            	sb.append(">=");
            	break;
            case LESS_THAN_EQUAL:
            	sb.append("<=");
            	break;
            }

    		sb.append(" @value" +  i++ + " ");
    	}
    	
    	return sb.toString();
    }

    public Map<String, Object> getBindVars () {
    	
    	Map<String, Object> result = new HashMap<String, Object>();    	
    	int i = 1;     	
    	for (final PropertyContainer container : propertyContainers) {
    		result.put("value" +  i++, container.value);
    	}
    	
    	return result;
    }
    
    private class PropertyContainer {
        public String key;
        public Object value;
        public Compare compare;

        public PropertyContainer(final String key, final Object value, final Compare compare) {
            this.key = key.replace("\"", "\\\"");
            this.value = value;
            this.compare = compare;
        }
    }

}
