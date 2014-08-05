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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class KeyMap implements IKeyMap {

	/**
	 * Stores all key bindings within this map.
	 */
	private Map<Short, IKeyBinding> bindings = new HashMap<> ();

	/**
	 * Stores the binding display name.
	 */
	private Map<Short, String> displayNameMap = new HashMap<> ();

	/**
	 * Stores the internal logger instance.
	 */
	@Getter (AccessLevel.PROTECTED)
	private static Logger logger = LogManager.getLogger (KeyMap.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAxis (short keyID) {
		getLogger ().entry ();

		// get binding
		IKeyBinding keyBinding = this.getKeyBinding (keyID);

		// check invalid bindings
		if (keyBinding == null || !(keyBinding instanceof IAxisBinding)) return 0.0f;

		// get axis value
		return getLogger ().exit (((IAxisBinding) keyBinding).getAxisValue ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDeadZone (short keyID) {
		getLogger ().entry ();

		// get binding
		IKeyBinding keyBinding = this.getKeyBinding (keyID);

		// check invalid bindings
		if (keyBinding == null || !(keyBinding instanceof IAxisBinding)) return 0.0f;

		// get axis deadzone value
		return getLogger ().exit (((IAxisBinding) keyBinding).getDeadZone ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IKeyBinding getKeyBinding (short keyID) {
		getLogger ().entry ();

		// skip non-existing key binds
		if (this.bindings.containsKey (keyID)) return null;

		// return data
		return getLogger ().exit (this.bindings.get (keyID));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPressed (short keyID) {
		getLogger ().entry ();

		// get binding
		IKeyBinding keyBinding = this.getKeyBinding (keyID);

		// check invalid bindings
		if (keyBinding == null || !(keyBinding instanceof IButtonBinding)) return false;

		// get pressed value
		return getLogger ().exit (((IButtonBinding) keyBinding).isPressed ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBinding (short keyID, String displayName, @NonNull IKeyBinding defaultBinding) {
		getLogger ().entry ();

		// register display name
		if (this.displayNameMap.containsKey (keyID)) getLogger ().warn ("Overwriting display name for key 0x" + Integer.toHexString (keyID) + ".");
		this.displayNameMap.put (keyID, displayName);

		// check for existing key binds
		if (this.bindings.containsKey (keyID) && ((defaultBinding instanceof IButtonBinding && this.getKeyBinding (keyID) instanceof IButtonBinding) || (defaultBinding instanceof IAxisBinding && this.getKeyBinding (keyID) instanceof IAxisBinding))) {
			// log
			getLogger ().debug ("Key 0x" + Integer.toHexString (keyID) + " was bound by a user. Ignoring defaults.");

			// exit
			getLogger ().exit ();
			return;
		} else if (this.bindings.containsKey (keyID))
			getLogger ().warn ("Overwriting user key binding for key 0x" + Integer.toHexString (keyID) + " due to mismatched key types.");

		// log
		getLogger ().debug ("Using default key binding for key 0x" + Integer.toHexString (keyID) + ".");

		// register default binding
		this.bindings.put (keyID, defaultBinding);

		// exit
		getLogger ().exit ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setKeyBinding (short keyID, @NonNull IKeyBinding keyBinding) {
		getLogger ().entry ();

		// get currently bound key
		IKeyBinding originalKeyBinding = this.getKeyBinding (keyID);

		// check types
		if (!(originalKeyBinding instanceof IButtonBinding && keyBinding instanceof IButtonBinding) && (originalKeyBinding instanceof IAxisBinding && keyBinding instanceof IAxisBinding)) return getLogger ().exit (false);

		// log
		getLogger ().info ("Binding for key 0x" + Integer.toHexString (keyID) + " was set by the user.");

		// store new binding
		this.bindings.put (keyID, keyBinding);
		return getLogger ().exit (true);
	}
}