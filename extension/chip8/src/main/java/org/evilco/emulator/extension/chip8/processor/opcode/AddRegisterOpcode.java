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

import org.evilco.emulator.core.processor.opcode.IOpcode;
import org.evilco.emulator.core.processor.opcode.annotation.Opcode;
import org.evilco.emulator.core.processor.opcode.error.OpcodeException;
import org.evilco.emulator.core.processor.opcode.error.OpcodeExecutionException;
import org.evilco.emulator.core.processor.register.error.ProcessorRegisterException;
import org.evilco.emulator.extension.chip8.Chip8Emulator;

/**
 * 0x8XY4: Adds the value of register Y to register X.
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
@Opcode (0x8004)
public class AddRegisterOpcode implements IOpcode<Chip8Emulator> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute (Chip8Emulator emulator, long opcode) throws OpcodeException {
		short registerA = ((short) ((opcode & 0x0F00) >> 8));
		short registerB = ((short) ((opcode & 0x00F0) >> 4));

		try {
			short valueA = emulator.getProcessor ().getRegister ().get (registerA);
			short valueB = emulator.getProcessor ().getRegister ().get (registerB);
			short value = ((short) (valueA + valueB));

			emulator.getProcessor ().getRegister ().set (0xF, ((short) (value > 0xFF ? 1 : 0)));
			emulator.getProcessor ().getRegister ().set (registerA, value);
		} catch (ProcessorRegisterException ex) {
			throw new OpcodeExecutionException (ex);
		}

		return true;
	}
}