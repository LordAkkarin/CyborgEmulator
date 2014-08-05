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

import org.evilco.emulator.core.processor.opcode.GenericOpcodeRegister;
import org.evilco.emulator.extension.chip8.Chip8Emulator;
import org.evilco.emulator.extension.chip8.processor.opcode.*;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class Chip8OpcodeRegister extends GenericOpcodeRegister<Chip8Emulator> {

	/**
	 * Constructs a new Code8OpcodeRegister instance.
	 */
	public Chip8OpcodeRegister () {
		super (0xF000);

		// register sub masks
		this.registerMask (0x0000, 0xF0FF);
		this.registerMask (0x8000, 0xF00F);
		this.registerMask (0xE000, 0xF0FF);
		this.registerMask (0xF000, 0xF0FF);

		// register opcodes
		this.registerOpcode (new ClearScreenOpcode ());
		this.registerOpcode (new ReturnOpcode ());
		this.registerOpcode (new JumpOpcode ());
		this.registerOpcode (new CallOpcode ());
		this.registerOpcode (new EqualsValueOpcode ());
		this.registerOpcode (new NotEqualsOpcode ());
		this.registerOpcode (new EqualsRegisterOpcode ());
		this.registerOpcode (new SetRegisterOpcode ());
		this.registerOpcode (new AddRegisterValueOpcode ());
		this.registerOpcode (new CopyRegisterValueOpcode ());
		this.registerOpcode (new OrOpcode ());
		this.registerOpcode (new AndOpcode ());
		this.registerOpcode (new XorOpcode ());
		this.registerOpcode (new AddRegisterOpcode ());
		this.registerOpcode (new SubtractRegisterOpcode ());
		this.registerOpcode (new ShiftRightOpcode ());
		this.registerOpcode (new SubtractOpcode ());
		this.registerOpcode (new ShiftLeftOpcode ());
		this.registerOpcode (new RegisterNotEqualsOpcode ());
		this.registerOpcode (new SetIndexRegisterOpcode ());
		this.registerOpcode (new JumpRegisterOpcode ());
		this.registerOpcode (new RandomOpcode ());
		this.registerOpcode (new DrawSpriteOpcode ());
		this.registerOpcode (new KeyPressedOpcode ());
		this.registerOpcode (new KeyNotPressedOpcode ());
		this.registerOpcode (new GetDelayTimerOpcode ());
		this.registerOpcode (new AwaitKeyPressOpcode ());
		this.registerOpcode (new SetDelayTimerOpcode ());
		this.registerOpcode (new SetSoundTimerOpcode ());
		this.registerOpcode (new AddIndexRegisterOpcode ());
		this.registerOpcode (new CopySpriteLocationOpcode ());
		this.registerOpcode (new CopyDecimalOpcode ());
		this.registerOpcode (new CopyRegisterOpcode ());
		this.registerOpcode (new CopyMemoryOpcode ());
	}
}