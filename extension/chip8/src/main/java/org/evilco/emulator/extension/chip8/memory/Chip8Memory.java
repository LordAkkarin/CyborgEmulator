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

package org.evilco.emulator.extension.chip8.memory;

import org.evilco.emulator.core.memory.GenericMemory;
import org.evilco.emulator.core.memory.error.MemoryException;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class Chip8Memory extends GenericMemory<Short> {

	/**
	 * Stores the default font.
	 */
	protected static final Short[] DEFAULT_FONT = new Short[] {
		0xF0, 0x90, 0x90, 0x90, 0xF0, //0
		0x20, 0x60, 0x20, 0x20, 0x70, //1
		0xF0, 0x10, 0xF0, 0x80, 0xF0, //2
		0xF0, 0x10, 0xF0, 0x10, 0xF0, //3
		0x90, 0x90, 0xF0, 0x10, 0x10, //4
		0xF0, 0x80, 0xF0, 0x10, 0xF0, //5
		0xF0, 0x80, 0xF0, 0x90, 0xF0, //6
		0xF0, 0x10, 0x20, 0x40, 0x40, //7
		0xF0, 0x90, 0xF0, 0x90, 0xF0, //8
		0xF0, 0x90, 0xF0, 0x10, 0xF0, //9
		0xF0, 0x90, 0xF0, 0x90, 0x90, //A
		0xE0, 0x90, 0xE0, 0x90, 0xE0, //B
		0xF0, 0x80, 0x80, 0x80, 0xF0, //C
		0xE0, 0x90, 0x90, 0x90, 0xE0, //D
		0xF0, 0x80, 0xF0, 0x80, 0xF0, //E
		0xF0, 0x80, 0xF0, 0x80, 0x80  //F
	};

	/**
	 * Constructs a new Code8Memory instance.
	 */
	public Chip8Memory () {
		super (4096);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear () {
		super.clear ();

		for (int i = 0; i < DEFAULT_FONT.length; i++) {
			try { this.set (i, DEFAULT_FONT[i]); } catch (MemoryException ignore) { }
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set (long address, Short value) throws MemoryException {
		super.set (address, ((short) (value & 0xFF)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unset (long address) throws MemoryException {
		this.set (address, ((short) 0));
	}
}