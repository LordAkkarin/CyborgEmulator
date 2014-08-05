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

package org.evilco.emulator.extension.chip8;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.emulator.core.IEmulator;
import org.evilco.emulator.core.error.InvalidRomSizeException;
import org.evilco.emulator.core.error.RomException;
import org.evilco.emulator.core.error.RomLoaderException;
import org.evilco.emulator.core.graphic.matrix.MonochromeMatrixScreen;
import org.evilco.emulator.core.memory.error.MemoryException;
import org.evilco.emulator.extension.chip8.memory.Chip8Memory;
import org.evilco.emulator.extension.chip8.processor.Chip8Processor;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class Chip8Emulator implements IEmulator {

	/**
	 * Stores the internal logger.
	 */
	@Getter (AccessLevel.PROTECTED)
	private static Logger logger = LogManager.getLogger (Chip8Emulator.class);

	/**
	 * Stores the emulator memory.
	 */
	@Getter
	private final Chip8Memory memory;

	/**
	 * Stores the emulator processor.
	 */
	@Getter
	private final Chip8Processor processor;

	/**
	 * Stores the emulator screen.
	 */
	@Getter
	private final MonochromeMatrixScreen screen;

	/**
	 * Constructs a new Code8Emulator instance.
	 */
	public Chip8Emulator () {
		this.memory = new Chip8Memory ();
		this.screen = new MonochromeMatrixScreen (((short) 64), ((short) 32));
		this.processor = new Chip8Processor (this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load (ByteBuf romBuffer) throws RomException {
		getLogger ().debug ("ROM contains " + romBuffer.readableBytes () + " bytes of data.");

		// copy ROM to memory
		try {
			long index = 0x200;

			while ((romBuffer.readableBytes () > 0 && this.memory.isValidAddress ((index)))) {
				this.memory.set (index, romBuffer.readUnsignedByte ());
				index++;
			}
		} catch (MemoryException ex) {
			throw new RomLoaderException (ex);
		}

		// verify
		if (romBuffer.readableBytes () > 0) throw new InvalidRomSizeException ("Found an extra of " + romBuffer.readableBytes () + " bytes at the end of ROM file.");
	}
}