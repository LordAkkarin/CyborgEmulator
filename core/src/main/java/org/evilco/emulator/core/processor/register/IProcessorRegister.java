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

package org.evilco.emulator.core.processor.register;

import org.evilco.emulator.core.processor.register.error.ProcessorRegisterException;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public interface IProcessorRegister<T> {

	/**
	 * Clears all writable registers.
	 */
	public void clear ();

	/**
	 * Clears all addresses from start (inclusive) to end (inclusive).
	 * @param start The start address.
	 * @param end The end address.
	 * @throws ProcessorRegisterException ProcessorRegisterException Occurs if the operation would violate security
	 *         policies or would be out of register bounds.
	 */
	public void clear (long start, long end) throws ProcessorRegisterException;

	/**
	 * Returns the register size.
	 * @return The register size.
	 */
	public long getSize ();

	/**
	 * Returns a register value.
	 * @param address The address.
	 * @return The value.
	 * @throws ProcessorRegisterException Occurs if the operation would violate security policies or would be out of
	 *         register bounds.
	 */
	public T get (long address) throws ProcessorRegisterException;

	/**
	 * Checks whether the address is readable.
	 * @param address The address.
	 * @return True if the address is readable.
	 */
	public boolean isReadable (long address);

	/**
	 * Checks whether the address is valid.
	 * @param address The address.
	 * @return True if the address is valid.
	 */
	public boolean isValid (long address);

	/**
	 * Checks whether the address is writable.
	 * @param address The address.
	 * @return True if the address is writable.
	 */
	public boolean isWritable (long address);

	/**
	 * Sets a register value.
	 * @param address The address.
	 * @param value The value.
	 * @throws ProcessorRegisterException Occurs if the operation would violate security policies or would be out of
	 *         register bounds.
	 */
	public void set (long address, T value) throws ProcessorRegisterException;

	/**
	 * Un-Sets an address.
	 * @param address The address.
	 * @throws ProcessorRegisterException Occurs if the operation would violate security policies or would be out of
	 *         register bounds.
	 */
	public void unset (long address) throws ProcessorRegisterException;
}