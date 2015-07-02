/*_############################################################################
  _## 
  _##  SNMP4J-Agent - EngineBootsProvider.java  
  _## 
  _##  Copyright (C) 2005-2009  Frank Fock (SNMP4J.org)
  _##  
  _##  Licensed under the Apache License, Version 2.0 (the "License");
  _##  you may not use this file except in compliance with the License.
  _##  You may obtain a copy of the License at
  _##  
  _##      http://www.apache.org/licenses/LICENSE-2.0
  _##  
  _##  Unless required by applicable law or agreed to in writing, software
  _##  distributed under the License is distributed on an "AS IS" BASIS,
  _##  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  _##  See the License for the specific language governing permissions and
  _##  limitations under the License.
  _##  
  _##########################################################################*/

package org.snmp4j.agent.cfg;

/**
 * An <code>EngineBootsProvider</code> holds persistently the number of engine
 * boots.
 *
 * @author Frank Fock
 * @version 1.2
 */
public interface EngineBootsProvider {

  /**
   * Returns the current engine boot counter value incremented by one. If
   * that number would by greater than 2^31-1 then one is returned. The
   * engine boots provider has to make sure that the returned value is
   * persistently stored before the method returns.
   *
   * @return
   *    the last engine boots counter incremented by one.
   */
  public int updateEngineBoots();

  /**
   * Returns current engine boot counter value.
   * @return
   *    the last engine boots counter.
   */
  public int getEngineBoots();

}
