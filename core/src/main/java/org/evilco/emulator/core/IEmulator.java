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

package org.evilco.emulator.core;

import io.netty.buffer.ByteBuf;
import org.evilco.emulator.core.error.EmulatorException;
import org.evilco.emulator.core.error.RomException;
import org.evilco.emulator.core.graphic.IScreen;
import org.evilco.emulator.core.memory.IMemory;
import org.evilco.emulator.core.processor.IProcessor;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public interface IEmulator {

	/**
	 * Returns the emulator memory.
	 * @return The memory.
	 */
	public IMemory getMemory ();

	/**
	 * Returns the emulator processor.
	 * @return The processor.
	 */
	public IProcessor getProcessor ();

	/**
	 * Returns the emulator screen.
	 * @return The screen.
	 */
	public IScreen getScreen ();

	/**
	 * Loads a ROM.
	 * @param romBuffer The ROM buffer.
	 * @throws EmulatorException Occurs if a rom is malformed.
	 */
	public void load (ByteBuf romBuffer) throws RomException;
}