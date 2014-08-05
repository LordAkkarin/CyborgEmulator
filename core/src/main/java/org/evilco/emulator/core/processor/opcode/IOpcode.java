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

package org.evilco.emulator.core.processor.opcode;

import org.evilco.emulator.core.IEmulator;
import org.evilco.emulator.core.processor.opcode.error.OpcodeException;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public interface IOpcode<T extends IEmulator> {

	/**
	 * Executes an opcode.
	 * @param opcode The original opcode.
	 * @return True if the processor should proceed to the next opcode.
	 * @throws OpcodeException Occurs if an error occurs while executing the opcode.
	 */
	public boolean execute (T emulator, long opcode) throws OpcodeException;
}