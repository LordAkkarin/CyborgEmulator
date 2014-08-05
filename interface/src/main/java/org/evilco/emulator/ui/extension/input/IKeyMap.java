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

import java.util.UUID;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public interface IKeyMap {

	/**
	 * Returns the current axis value.
	 * @param keyID The key ID.
	 * @return The axis value.
	 */
	public float getAxis (short keyID);

	/**
	 * Returns the axis dead zone value.
	 * @param keyID The key ID.
	 * @return The dead zone.
	 */
	public float getDeadZone (short keyID);

	/**
	 * Returns a key bindings.
	 * @param keyID The key ID.
	 * @return The key binding.
	 */
	public IKeyBinding getKeyBinding (short keyID);

	/**
	 * Checks whether a key is pressed.
	 * @param keyID The key ID.
	 * @return True if the key is pressed.
	 */
	public boolean isPressed (short keyID);

	/**
	 * Registers a new binding.
	 * @param keyID A numeric keyID.
	 * @param displayName The display name.
	 * @param defaultBinding Defines the default key binding.
	 */
	public void registerBinding (short keyID, String displayName, IKeyBinding defaultBinding);

	/**
	 * Sets a new key binding.
	 * @param keyID The key ID.
	 * @param keyBinding The key binding.
	 * @return True if the key binding was applied successfully.
	 */
	public boolean setKeyBinding (short keyID, IKeyBinding keyBinding);
}