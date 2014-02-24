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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.arangodb.objectmapper.util.ArangoDbAssert;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public class ArangoDbDocument implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7047000745465609916L;

	/**
	 * document id 
	 */
	
	private String _id;

	/**
	 * document key 
	 */
	
	private String _key;

	/**
	 * document revision
	 */
	
	private String _rev;

	@JsonProperty("_id")
	public String getId() {
		return _id;
	}
	
	@JsonProperty("_id")
	public void setId(String s) {
		ArangoDbAssert.hasText(s, "id must have a value");
		if (_id != null && _id.equals(s)) {
		    return;
		}
	    if (_id != null) {
			throw new IllegalStateException("cannot set id, id already set");
		}
		_id = s;
	}

	@JsonProperty("_key")
	public String getKey() {
		return _key;
	}
	
	@JsonProperty("_key")
	public void setKey(String s) {
		ArangoDbAssert.hasText(s, "key must have a value");
		if (_key != null && _id.equals(s)) {
		    return;
		}
	    if (_key != null) {
			throw new IllegalStateException("cannot set key, key already set");
		}
		_key = s;
	}

	@JsonProperty("_rev")
	public String getRevision() {
		return _rev;
	}

	@JsonProperty("_rev")
	public void setRevision(String s) {
		// no empty strings thanks
		if (s != null && s.length() == 0) {
			return;
		}
		this._rev = s;
	}
	
	@JsonIgnore	
	public boolean isNew() {
		return _rev == null;
	}

}
