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

package org.evilco.emulator.ui.extension.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
@Data
public class KeyboardBinding implements IButtonBinding {

	/**
	 * Stores the jinput key index.
	 */
	@Getter
	@Setter
	private Integer inputKeyIndex = null;

	/**
	 * Constructs a new KeyboardBinding instance.
	 * @param inputKeyIndex The keyboard index.
	 */
	public KeyboardBinding (Integer inputKeyIndex) {
		this.inputKeyIndex = inputKeyIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPressed () {
		if (this.inputKeyIndex == null) return false;
		return Keyboard.isKeyDown (this.inputKeyIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayName () {
		if (this.inputKeyIndex == null) return null;
		return Keyboard.getKeyName (this.inputKeyIndex);
	}
}