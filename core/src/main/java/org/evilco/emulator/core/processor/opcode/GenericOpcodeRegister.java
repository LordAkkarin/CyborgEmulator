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

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.evilco.emulator.core.IEmulator;
import org.evilco.emulator.core.processor.opcode.annotation.Opcode;
import org.evilco.emulator.core.processor.opcode.error.OpcodeException;
import org.evilco.emulator.core.processor.opcode.error.UnknownOpcodeException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class GenericOpcodeRegister<T extends IEmulator> implements IOpcodeRegister<T> {

	/**
	 * Stores the default opcode mask.
	 */
	private final int defaultMask;

	/**
	 * Stores a map of opcode masks.
	 */
	private final Map<Integer, Integer> maskMap = new HashMap<> ();

	/**
	 * Stores the opcode map.
	 */
	private final Map<Integer, IOpcode<T>> opcodeMap = new HashMap<> ();

	/**
	 * Constructs a new GenericOpcodeRegister instance.
	 * @param defaultMask The default mask.
	 */
	public GenericOpcodeRegister (int defaultMask) {
		this.defaultMask = defaultMask;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IOpcode<T> getOpcode (int opcode) throws OpcodeException {
		// extract opcode based on default mask
		int currentOpcode = (opcode & this.defaultMask);

		// get sub-opcode if needed
		if (this.maskMap.containsKey (currentOpcode)) currentOpcode = (opcode & this.maskMap.get (currentOpcode));

		// search opcode
		if (!this.opcodeMap.containsKey (currentOpcode)) throw new UnknownOpcodeException ("Could not find opcode 0x" + Integer.toHexString (currentOpcode) + ".");
		return this.opcodeMap.get (currentOpcode);
	}

	/**
	 * Registers a new opcode mask.
	 * @param opcode The root opcode.
	 * @param mask The mask.
	 */
	public void registerMask (int opcode, int mask) {
		this.maskMap.put (opcode, mask);
	}

	/**
	 * Registers a new opcode.
	 * @param opcode The opcode.
	 * @param opcodeInstance The opcode instance.
	 */
	public void registerOpcode (int opcode, @NonNull IOpcode<T> opcodeInstance) {
		this.opcodeMap.put (opcode, opcodeInstance);
	}

	/**
	 * Registers a new opcode.
	 * @param opcode The opcode instance.
	 */
	public void registerOpcode (@NonNull IOpcode<T> opcode) {
		Preconditions.checkArgument (opcode.getClass ().isAnnotationPresent (Opcode.class), "Opcode " + opcode.getClass () + " is missing @Opcode annotation.");
		this.registerOpcode (opcode.getClass ().getAnnotation (Opcode.class).value (), opcode);
	}
}