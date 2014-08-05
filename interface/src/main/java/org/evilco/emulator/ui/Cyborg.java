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

package org.evilco.emulator.ui;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.evilco.emulator.core.error.RomException;

import java.util.ResourceBundle;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class Cyborg {

	/**
	 * Stores the internal logger instance.
	 */
	@Getter (AccessLevel.PROTECTED)
	private static final Logger logger = LogManager.getLogger (Cyborg.class);

	/**
	 * Stores all application translations.
	 */
	private static final ResourceBundle translation = ResourceBundle.getBundle ("core");

	/**
	 * Constructs a new Cyborg instance.
	 */
	public Cyborg (ICyborgContainer container) {
		getLogger ().entry ();

		// load

		getLogger ().exit ();
	}

	/**
	 * Enables or disables debug logging.
	 * @param value True if debug logging should be enabled.
	 */
	public static void enableDebugLogging (boolean value) {
		getLogger ().entry ();

		// get context & configuration
		LoggerContext context = ((LoggerContext) LogManager.getContext (false));
		Configuration configuration = context.getConfiguration ();

		// enable/disable debug messages on the root logger
		configuration.getLoggerConfig (LogManager.ROOT_LOGGER_NAME).setLevel ((value ? Level.ALL : Level.INFO));

		// update configuration
		context.updateLoggers (configuration);

		getLogger ().exit ();
	}

	/**
	 * Returns a translation string.
	 * @param name The translation name.
	 * @return The translation.
	 */
	public static String getTranslation (String name) {
		if (!translation.containsKey (name)) return name;
		return translation.getString (name);
	}

	public void loadROM () throws RomException {
		getLogger ().entry ();

		// shutdown previous instances


		getLogger ().exit ();
	}
}