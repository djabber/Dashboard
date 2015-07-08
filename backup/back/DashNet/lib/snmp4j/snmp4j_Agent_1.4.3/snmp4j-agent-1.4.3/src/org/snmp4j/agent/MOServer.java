/*_############################################################################
  _##
  _##  SNMP4J-Agent - MOServer.java
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


package org.snmp4j.agent;

import org.snmp4j.smi.OctetString;
import java.util.Iterator;
import org.snmp4j.smi.OID;

/**
 * The managed object server interface defines the services that a repository
 * of managed objects needs to provide for a command responder.
 *
 * @author Frank Fock
 * @version 1.4
 */
public interface MOServer {

  /**
   * Adds a context listener to the server. The listener will be informed about
   * context insertion and removal.
   * @param listener
   *    a <code>ContextListener</code> instance to be informed about context
   *    changes.
   */
  void addContextListener(ContextListener listener);

  /**
   * Removes a previously added context listener.
   * @param listener
   *    a <code>ContextListener</code> instance.
   */
  void removeContextListener(ContextListener listener);

  /**
   * Adds the supplied context to the server. The server however may silently
   * ignore the request if local constraints do not allow to add the context
   * (although this should be an exception case only).
   * @param context
   *    an <code>OctetString</code> representing the context name to add.
   */
  void addContext(OctetString context);

  /**
   * Removes a context from the server. Removing a context does not remove
   * any managed objects from the server's registry.
   * @param context
   *    n <code>OctetString</code> representing the context name to remove.
   */
  void removeContext(OctetString context);

  /**
   * Registers a managed object for the specified context. A managed object can
   * be registered for more than one context.
   * @param mo
   *    a <code>ManagedObject</code> instance.
   * @param context
   *    the context name for which to register the <code>mo</code> or
   *    <code>null</code> if the managed oject is to be registered for all
   *    contexts (including the default context).
   * @throws DuplicateRegistrationException
   *    if the registration conflicts (i.e. overlaps) with an already existing
   *    registration.
   */
  void register(ManagedObject mo, OctetString context)
      throws DuplicateRegistrationException;

  /**
   * Removes the registration of the supplied managed object for the specified
   * context.
   * @param mo
   *    a <code>ManagedObject</code> instance.
   * @param context
   *    the context name for which to unregister the <code>mo</code> or
   *    <code>null</code> if the managed oject is to be unregistered for all
   *    contexts (including the default context). In the latter case however,
   *    explicit registrations for a particular context will not be removed!
   */
  void unregister(ManagedObject mo, OctetString context);

  /**
   * Adds a managed object lookup listener for the supplied managed object to
   * this managed object server. A <code>MOServerLookupListener</code> is called
   * before the managed object is returned by {@link #lookup(MOQuery query)}.
   *
   * @param listener
   *    a <code>MOServerLookupListener</code> instance, for example a managed
   *    object that needs to update its state whenever it has been looked up
   * @param mo
   *    the <code>ManagedObject</code> that triggers the
   *    {@link MOServerLookupEvent} to be fired when it has been looked up.
   */
  void addLookupListener(MOServerLookupListener listener,
                         ManagedObject mo);

  /**
   * Removes a managed object lookup listener for the specified managed object.
   * @param listener
   *    a <code>MOServerLookupListener</code> instance.
   * @param mo
   *    the <code>ManagedObject</code> that triggered the
   *    {@link MOServerLookupEvent} to be fired when it has been looked up.
   * @return
   *    <code>true</code> if the listener could be removed or <code>false</code>
   *    if such a listener is not registered.
   */
  boolean removeLookupListener(MOServerLookupListener listener,
                               ManagedObject mo);

  /**
   * Lookups the first (lexicographically ordered) managed object that matches
   * the supplied query.
   * @param query
   *    a <code>MOQuery</code> instance.
   * @return
   *    the <code>ManagedObject</code> that matches the query and
   *    <code>null</code> if no such object exists.
   */
  ManagedObject lookup(MOQuery query);

  /**
   * Returns a read-only <code>Iterator</code> over the content of this server.
   * The iterator is thread safe and can be used while the server is being
   * modified. The remove operation of the iterator is not supported.
   * @return
   *    the <code>Iterator</code> on the Map.Entry instances managed by
   *    this server. Each <code>Entry</code> consists of an {@link MOScope}
   *    key instance and a corresponding {@link ManagedObject} value instance.
   *    If the <code>ManagedObject</code> has been registered for a specific
   *    context, then a {@link MOContextScope} is returned as key, otherwise
   *    the managed objects own <code>MOScope</code> is returned.
   */
  Iterator iterator();

  /**
   * Locks a ManagedObject by the supplied owner. Once a ManagedObject is
   * locked, a lookup attempt will block until it is unlocked or a predefined
   * timeout occurs.
   * @param owner
   *    an Object.
   * @param managedObject
   *    the ManagedObject to lock.
   * @return
   *    <code>true</code> if the lock could be acquired, <code>false</code>
   *    otherwise, i.e. if an InterruptedException has occurred.
   */
  boolean lock(Object owner, ManagedObject managedObject);

  /**
   * Locks a ManagedObject by the supplied owner. Once a ManagedObject is
   * locked, a lookup attempt will block until it is unlocked or a predefined
   * timeout occurs.
   * @param owner
   *    an Object.
   * @param managedObject
   *    the ManagedObject to lock.
   * @param timeoutMillis
   *    the number of 1/1000 seconds to wait for the lock.
   * @return
   *    <code>true</code> if the lock could be acquired, <code>false</code>
   *    otherwise, i.e. if an InterruptedException or timeout has occurred.
   * @since 1.3
   */
  boolean lock(Object owner, ManagedObject managedObject, long timeoutMillis);

  /**
   * Unlocks a ManagedObject that has been locked by the specified owner. If
   * the ManagedObject is currently locked by another owner this method returns
   * silently.
   * <p>
   * Note: In debug log mode a message is locked if the lock owner does not
   * match the current lock owner.
   * @param owner
   *    an Object.
   * @param managedObject
   *    the ManagedObject to unlock.
   */
  void unlock(Object owner, ManagedObject managedObject);

  /**
   * Returns the contexts known by the server.
   * @return
   *    an array of context names.
   */
  OctetString[] getContexts();

  /**
   * Checks whether the supplied context is supported (registered) by this
   * server.
   * @param context
   *    a context name.
   * @return
   *    <code>true</code> if the context is support (thus has previously added
   *    by {@link #addContext}) and <code>false</code> otherwise.
   */
  boolean isContextSupported(OctetString context);

  /**
   * Returns the contexts for which the supplied {@link ManagedObject} has been
   * registered.
   * @param managedObject
   *    a {@link ManagedObject} instance.
   * @return
   *    an array of context strings, for which <code>managedObject</code> has
   *    been registered. If the <code>managedObject</code> has bee registered
   *    for all contexts, a <code>null</code> element is included in the array.
   * @since 1.4
   */
  OctetString[] getRegisteredContexts(ManagedObject managedObject);
}