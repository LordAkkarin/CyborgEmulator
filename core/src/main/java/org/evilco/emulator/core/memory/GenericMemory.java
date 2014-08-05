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

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.evilco.emulator.core.memory.error.MemoryAccessViolationException;
import org.evilco.emulator.core.memory.error.MemoryException;
import org.evilco.emulator.core.memory.error.OutOfMemoryBoundsException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class GenericMemory<T> implements IMemory<T> {

	/**
	 * Stores the memory.
	 */
	private final Map<Long, T> memory = new HashMap<> ();

	/**
	 * Stores the memory size.
	 */
	@Getter
	private final long size;

	/**
	 * Constructs a new GenericMemory instance.
	 * @param size The memory size.
	 */
	public GenericMemory (int size) {
		this.size = size;

		// clear
		this.clear ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear () {
		for (long i = 0; i < this.size; i++) {
			try { this.unset (i); } catch (MemoryException ignore) { }
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear (long start, long end) throws MemoryException {
		Preconditions.checkArgument (end > start, "Last address needs to be bigger than first address.");

		for (long i = start; i < end; i++) {
			this.unset (i);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get (long address) throws MemoryException {
		if (!this.isValidAddress (address)) throw new OutOfMemoryBoundsException ("Address 0x" + Long.toHexString (address) + " is out of memory bounds (space starts at 0x0 and ends at 0x" + Long.toHexString ((this.size - 1)) + ").");
		if (!this.isReadableAddress (address)) throw new MemoryAccessViolationException ("Failed to read from address 0x" + Long.toHexString (address) + ": Address is protected against read operations.");
		return this.memory.get (address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadableAddress (long address) {
		return this.isValidAddress (address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidAddress (long address) {
		return (address >= 0x0 && address < this.getSize ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWritableAddress (long address) {
		return this.isValidAddress (address);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set (long address, T value) throws MemoryException {
		if (!this.isValidAddress (address)) throw new OutOfMemoryBoundsException ("Address 0x" + Long.toHexString (address) + " is out of memory bounds (space starts at 0x0 and ends at 0x" + Long.toHexString ((this.size - 1)) + ").");
		if (!this.isWritableAddress (address)) throw new MemoryAccessViolationException ("Failed to write to address 0x" + Long.toHexString (address) + ": Address is protected against write operations.");
		this.memory.put (address, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unset (long address) throws MemoryException {
		this.set (address, null);
	}
}