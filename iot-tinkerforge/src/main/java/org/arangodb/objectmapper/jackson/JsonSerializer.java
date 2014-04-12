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

package org.arangodb.objectmapper.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.arangodb.objectmapper.ArangoDb4JException;

import java.io.InputStream;

public class JsonSerializer {

	private final ObjectMapper objectMapper;
	
	public JsonSerializer () {
		this.objectMapper = new ObjectMapper();
		
		// std config:
		this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		this.objectMapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);		
	}
	
	public String toJson(Object o) throws ArangoDb4JException {
		try {
			String json = objectMapper.writeValueAsString(o);
			return json;
		} catch (Exception e) {
			throw new ArangoDb4JException(e);
		}
	}

	public <T> T toObject(InputStream istream,  Class<T> valueType) throws ArangoDb4JException {
		try {
			return objectMapper.readValue(istream, valueType);
		} catch (Exception e) {
			throw new ArangoDb4JException(e);
		}
	}

	public <T> T toObject(JsonNode node,  Class<T> valueType) throws ArangoDb4JException {
		try {
			return objectMapper.convertValue(node, valueType);
		} catch (Exception e) {
			throw new ArangoDb4JException(e);
		}
	}

	public JsonNode toJsonNode(InputStream istream) throws ArangoDb4JException {
		try {
			return objectMapper.readTree(istream);
			
		} catch (Exception e) {
			throw new ArangoDb4JException(e);
		}
	}
}
