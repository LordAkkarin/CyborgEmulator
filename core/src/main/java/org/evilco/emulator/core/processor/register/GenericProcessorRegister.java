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

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.evilco.emulator.core.processor.register.error.OutOfRegisterBoundsException;
import org.evilco.emulator.core.processor.register.error.ProcessorRegisterException;
import org.evilco.emulator.core.processor.register.error.RegisterAccessViolationException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class GenericProcessorRegister<T> implements IProcessorRegister<T> {

	/**
	 * Stores the register.
	 */
	private final Map<Long, T> register = new HashMap<> ();

	/**
	 * Stores the register size.
	 */
	@Getter
	private final long size;

	/**
	 * Constructs a new GenericProcessorRegister instance.
	 * @param size The register size.
	 */
	public GenericProcessorRegister (long size) {
		this.size = size;

		// clear
		this.clear ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear (long start, long end) throws ProcessorRegisterException {
		Preconditions.checkArgument (end > start, "Last address needs to be bigger than first address.");

		for (long i = start; i <= end; i++) {
			this.unset (i);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear () {
		for (int i = 0; i < this.size; i++) {
			try { this.unset (i); } catch (ProcessorRegisterException ignore) { }
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get (long address) throws ProcessorRegisterException {
		if (!this.isValid (address)) throw new OutOfRegisterBoundsException ("Address 0x" + Long.toHexString (address) + " is out of register bounds (space starts at 0x0 and ends at 0x" + Long.toHexString ((this.size - 1)) + ").");
		if (!this.isReadable (address)) throw new RegisterAccessViolationException ("Failed to read from address 0x" + Long.toHexString (address) + ": Address is protected against read operations.");
		return this.register.get (address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadable (long address) {
		return this.isValid (address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid (long address) {
		return (address >= 0x0 && address < this.size);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWritable (long address) {
		return this.isValid (address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set (long address, T value) throws ProcessorRegisterException {
		if (!this.isValid (address)) throw new OutOfRegisterBoundsException ("Address 0x" + Long.toHexString (address) + " is out of register bounds (space starts at 0x0 and ends at 0x" + Long.toHexString ((this.size - 1)) + ").");
		if (!this.isWritable (address)) throw new RegisterAccessViolationException ("Failed to write to address 0x" + Long.toHexString (address) + ": Address is protected against write operations.");
		this.register.put (address, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unset (long address) throws ProcessorRegisterException {
		this.set (address, null);
	}
}