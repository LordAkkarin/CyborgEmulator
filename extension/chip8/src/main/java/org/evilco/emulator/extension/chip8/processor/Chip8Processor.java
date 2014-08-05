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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.emulator.core.error.EmulatorException;
import org.evilco.emulator.core.processor.IProcessor;
import org.evilco.emulator.core.processor.opcode.IOpcode;
import org.evilco.emulator.extension.chip8.Chip8Emulator;

import java.util.Stack;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class Chip8Processor implements IProcessor {

	/**
	 * Stores the delay timer.
	 */
	@Getter
	private short delayTimer;

	/**
	 * Stores the parent emulator.
	 */
	@Getter
	private final Chip8Emulator emulator;

	/**
	 * Stores the index register.
	 */
	@Getter
	private int indexRegister;

	/**
	 * Stores the internal logger.
	 */
	@Getter (AccessLevel.PROTECTED)
	private static Logger logger = LogManager.getLogger (Chip8Processor.class);

	/**
	 * Stores the processor pointer.
	 */
	@Getter
	private int pointer;

	/**
	 * Stores the processor opcode register.
	 */
	@Getter
	private Chip8OpcodeRegister opcodeRegister = new Chip8OpcodeRegister ();

	/**
	 * Stores the processor register.
	 */
	@Getter
	private Chip8ProcessorRegister register;

	/**
	 * Stores the processor stack.
	 */
	@Getter
	private Stack<Integer> stack;

	/**
	 * Constructs a new Code8Processor instance.
	 * @param emulator The parent emulator instance.
	 */
	public Chip8Processor (@NonNull Chip8Emulator emulator) {
		this.emulator = emulator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cycle () throws EmulatorException {
		// fetch next opcode
		int opcode = (this.emulator.getMemory ().get (this.getPointer ()) << 8);
		opcode |= this.emulator.getMemory ().get ((this.getPointer () + 1));

		// find opcode
		IOpcode<Chip8Emulator> opcodeInstance = this.opcodeRegister.getOpcode (opcode);

		// log
		getLogger ().debug ("Executing opcode " + opcodeInstance.getClass ().getName () + " (0x" + Integer.toHexString (opcode) + ").");

		// execute
		if (opcodeInstance.execute (this.getEmulator (), opcode)) this.setPointer ((this.getPointer () + 2));

		// decrease delay timer
		if (this.delayTimer > 0) this.delayTimer--;
		// TODO: Add sound timer
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		this.register = new Chip8ProcessorRegister ();
		this.stack = new Stack<Integer> ();
		this.delayTimer = 0;

		this.pointer = 0x200;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSpeed () {
		return 60;
	}

	/**
	 * Sets a new delay timer value.
	 * @param timer The timer.
	 */
	public void setDelayTimer (short timer) {
		this.delayTimer = ((short) (timer & 0xFF));
	}

	/**
	 * Sets a new index register value.
	 * @param indexRegister The value.
	 */
	public void setIndexRegister (int indexRegister) {
		this.indexRegister = (indexRegister & 0xFFFF);
	}

	/**
	 * Sets a new pointer position.
	 * @param pointer The pointer.
	 */
	public void setPointer (int pointer) {
		this.pointer = (pointer & 0xFFFF);
	}

	/**
	 * Skips the next instruction.
	 */
	public void skipInstruction () {
		this.setPointer ((this.getPointer () + 2));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown () {
		this.register = null;
	}
}