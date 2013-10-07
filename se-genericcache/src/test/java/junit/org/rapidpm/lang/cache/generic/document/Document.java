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

package junit.org.rapidpm.lang.cache.generic.document;

import java.util.Date;

import org.apache.log4j.Logger;
import org.rapidpm.lang.cache.generic.Cacheable;

@Cacheable(primaryKeyAttributeName = "docId")
public class Document {
    private static final Logger logger = Logger.getLogger(Document.class);

    private long docId;
    //    private byte[] contentPacked;
    private String content;
    private String url;
    private String domain;
    private String title;
    private Date date;

    //    private boolean packed = false;

    public Document() {
    }


    //    public Document(final boolean packed){
    //        this.packed = packed;
    //    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(final long docId) {
        this.docId = docId;
    }

    //    public byte[] getContentPacked(){
    //        if(packed){
    //            return contentPacked;
    //        }else{
    //            return tools.compress(content);
    //        }
    //    }

    public void setContent(final String content) {
        this.content = content;
    }

    //    public void setContentPacked(final byte[] contentPacked){
    //        this.contentPacked = contentPacked;
    //    }

    public String getContent() {
        //        if(packed){
        //            return tools.decompress(contentPacked);
        //        }else{
        return content;
        //        }
    }

    //    public boolean isPacked(){
    //        return packed;
    //    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Document");
        sb.append("{docId=").append(docId);
        //        sb.append(", contentPacked=").append(contentPacked == null ? "null" : "");
        //        for (int i = 0; contentPacked != null && i < contentPacked.length; ++i) {
        //            sb.append(i == 0 ? "" : ", ").append(contentPacked[i]);
        //        }
        sb.append(", url='").append(url).append('\'');
        sb.append(", domain='").append(domain).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
