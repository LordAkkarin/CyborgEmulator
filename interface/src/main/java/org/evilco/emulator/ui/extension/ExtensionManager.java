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

package org.evilco.emulator.ui.extension;

import org.evilco.emulator.core.IEmulator;
import org.evilco.emulator.extension.AbstractExtensionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ExtensionManager extends AbstractExtensionManager<Extension> {

	/**
	 * Stores a mapping between file extensions and emulators.
	 */
	private Map<String, Class<? extends IEmulator>> fileExtensionMap = new HashMap<> ();

	/**
	 * Stores a mapping between extension identifiers and registered file extensions.
	 */
	private Map<String, String> extensionFileMap = new HashMap<> ();

	/**
	 * Constructs a new ExtensionManager instance.
	 */
	public ExtensionManager () {
		super (Extension.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onSuccessfulExtensionLoad (Extension extension) { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onSuccessfulExtensionUnload (String identifier) {

	}
}