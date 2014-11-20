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

package junit.org.rapidpm.module.iot;

/**
 * Created by Sven Ruppert on 04.11.2014.
 */
public class Demo {
  public static void main(String[] args) {
    final byte[] data2Write = "Dr. Diana Kupfer".getBytes();

    for (final byte b : data2Write) {
      final String s = Integer.toHexString(b);
      System.out.println("s = " + s);
    }

  }
}
