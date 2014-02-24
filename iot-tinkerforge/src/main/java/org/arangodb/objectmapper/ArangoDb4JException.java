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

/**
 * ArangoDb exception
 * 
 * @author abrandt
 *
 */

public class ArangoDb4JException extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5301373222706274918L;

	/**
	 * Http status error code 
	 */
	
	private Integer statusCode;

	/**
	 * ArangoDB error number 
	 */
	
	private Integer errorNum;
	
	/**
	 * Constructor
	 * 
	 * @param t       Throwable
     */

	public ArangoDb4JException(Throwable t) {
		super(t);
	}
		
	/**
	 * Constructor
	 * 
	 * @param message       error message
     */

    public ArangoDb4JException(String message) {
        super(message);
        this.statusCode = 0;
        this.errorNum = 0;
    }

	/**
	 * Constructor
	 * 
	 * @param message      error message
	 * @param statusCode   http status error code (> 299)
	 * @param errorNum     ArangoDB error number
     */

    public ArangoDb4JException(String message, Integer statusCode, Integer errorNum) {
        super(message);
        this.statusCode = statusCode;
        this.errorNum = errorNum;
    }
    
    public Integer getErrorNumber () {
    	return this.errorNum;
    }

    public Integer getStatusCode () {
    	return this.statusCode;
    }
    
}
