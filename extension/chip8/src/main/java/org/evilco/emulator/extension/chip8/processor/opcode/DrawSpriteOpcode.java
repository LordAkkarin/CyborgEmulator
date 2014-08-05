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

package org.evilco.emulator.extension.chip8.processor.opcode;

import org.evilco.emulator.core.memory.error.MemoryException;
import org.evilco.emulator.core.processor.opcode.IOpcode;
import org.evilco.emulator.core.processor.opcode.annotation.Opcode;
import org.evilco.emulator.core.processor.opcode.error.OpcodeException;
import org.evilco.emulator.core.processor.opcode.error.OpcodeExecutionException;
import org.evilco.emulator.core.processor.register.error.ProcessorRegisterException;
import org.evilco.emulator.extension.chip8.Chip8Emulator;

/**
 * 0xDXYH: Draws a sprite of height H at location X, Y. The sprite data is read from the address the processor is currently pointing to.
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
@Opcode (0xD000)
public class DrawSpriteOpcode implements IOpcode<Chip8Emulator> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute (Chip8Emulator emulator, long opcode) throws OpcodeException {
		short registerA = ((short) ((opcode & 0x0F00) >> 8));
		short registerB = ((short) ((opcode & 0x00F0) >> 4));
		short height = ((short) (opcode & 0x000F));

		try {
			// get coordinates
			short x = emulator.getProcessor ().getRegister ().get (registerA);
			short y = emulator.getProcessor ().getRegister ().get (registerB);

			// reset register
			emulator.getProcessor ().getRegister ().set (0xF, ((short) 0));

			for (short lineY = 0; lineY < height; lineY++) {
				// get line from memory
				short line = emulator.getMemory ().get ((emulator.getProcessor ().getIndexRegister () + lineY));

				for (byte lineX = 0; lineX < 8; lineX++) {
					// skip empty pixels
					if ((line & (0x80 >> lineX)) == 0) continue;

					// toggle pixel
					if (!emulator.getScreen ().togglePixel (((short) (lineX + x)), ((short) (lineY + y)))) emulator.getProcessor ().getRegister ().set (0xF, ((short) 1));
				}
			}
		} catch (ProcessorRegisterException ex) {
			throw new OpcodeExecutionException (ex);
		} catch (MemoryException ex) {
			throw new OpcodeExecutionException (ex);
		}

		return true;
	}
}