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

package org.evilco.emulator.extension.chip8.processor;

import org.evilco.emulator.core.processor.register.GenericProcessorRegister;
import org.evilco.emulator.core.processor.register.error.ProcessorRegisterException;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class Chip8ProcessorRegister extends GenericProcessorRegister<Short> {

	/**
	 * Constructs a new Code8ProcessorRegister instance.
	 */
	public Chip8ProcessorRegister () {
		super (16);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set (long address, Short value) throws ProcessorRegisterException {
		super.set (address, ((short) (value & 0xFF)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unset (long address) throws ProcessorRegisterException {
		this.set (address, ((short) 0));
	}
}