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

package org.arangodb.objectmapper.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.arangodb.objectmapper.ArangoDb4JException;

/** 
 * Original file from "Java API for CouchDB http://www.ektorp.org"
 * 
 * @author henrik lundgren
 * 
 */

public class ResponseCallback<T> {
		
	/**
	 * Error handler (throws ArangoDb4JException)
	 * 
	 * @param hr
	 *            Request response
	 *            
	 * @return T
	 * 
	 * @throws ArangoDb4JException
	 */

	public T error(ArangoDbHttpResponse hr) throws ArangoDb4JException {
		ObjectMapper om = new ObjectMapper();
		
		JsonNode root; 
		
		try {
			root = om.readTree(hr.getContentAsStream());
		}
		catch (Exception e) {
			throw new ArangoDb4JException(e.getMessage(), hr.getCode(), 0);
		}
		
		String errorMessage = root.has("errorMessage") ? root.get("errorMessage").asText() : null;
		Integer errorNum = root.has("errorNum") ? root.get("errorNum").asInt() : null;
		
		throw new ArangoDb4JException(errorMessage, hr.getCode(), errorNum);
	}

	/**
	 * Success handler (returns null)
	 * 
	 * @param hr
	 *            Request response
	 *            
	 * @return T
	 * 
	 * @throws ArangoDb4JException
	 */

	public T success(ArangoDbHttpResponse hr) throws ArangoDb4JException {
		return null;
	}

}
