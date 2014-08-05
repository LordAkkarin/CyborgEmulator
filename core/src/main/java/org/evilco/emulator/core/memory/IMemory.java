/*
 * Copyright (C) 2014 Johannes Donath <johannesd@evil-co.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.evilco.emulator.core.memory;

import org.evilco.emulator.core.memory.error.MemoryException;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public interface IMemory<T> {

	/**
	 * Clears the memory.
	 */
	public void clear ();

	/**
	 * Clears the memory from address start (inclusive) to end (inclusive).
	 * @param start The start address.
	 * @param end The end address.
	 * @throws MemoryException Occurs if an address is out of memory bounds or protected.
	 */
	public void clear (long start, long end) throws MemoryException;

	/**
	 * Returns a memory value.
	 * @param address The address.
	 * @return The value.
	 * @throws MemoryException Occurs if an address is out of memory bounds or protected.
	 */
	public T get (long address) throws MemoryException;

	/**
	 * Returns the memory size.
	 * @return The size.
	 */
	public long getSize ();

	/**
	 * Checks whether an address is readable.
	 * @param address The address.
	 * @return True if the address is readable.
	 */
	public boolean isReadableAddress (long address);

	/**
	 * Checks whether an address is valid.
	 * @param address The address.
	 * @return True if the address is within memory bounds.
	 */
	public boolean isValidAddress (long address);

	/**
	 * Checks whether an address is writable.
	 * @param address The address.
	 * @return True if the address is writable.
	 */
	public boolean isWritableAddress (long address);

	/**
	 * Sets a memory value.
	 * @param address The address.
	 * @param value The value.
	 * @throws MemoryException Occurs if an address is out of memory bounds or protected.
	 */
	public void set (long address, T value) throws MemoryException;

	/**
	 * Un-Sets a memory value.
	 * @param address The address.
	 * @throws MemoryException Occurs if an address is out of memory bounds or protected.
	 */
	public void unset (long address) throws MemoryException;
}