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

package org.evilco.emulator.ui.crash.collector;

import org.evilco.emulator.ui.crash.ICrashReport;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class MemoryInformationCollector implements IInformationCollector {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String collect (ICrashReport crashReport) {
		// copy runtime instance (for lazy people)
		Runtime runtime = Runtime.getRuntime ();

		// add used memory
		return (formatMemorySize ((runtime.totalMemory () - runtime.totalMemory ())) + " out of " + formatMemorySize (runtime.maxMemory ()) + " allocated.");
	}

	/**
	 * Formats the memory size (e.g. XYZ KiB).
	 * @param size The raw size (in bytes).
	 * @return The formatted size.
	 */
	public static String formatMemorySize (long size) {
		// store default symbol
		String symbol = "Byte(s)";

		// kilobytes
		if (size >= 1024) {
			size /= 1024;
			symbol = "KiB";
		}

		// megabytes
		if (size >= 1024) {
			size /= 1024;
			symbol = "MiB";
		}

		// gigabytes
		if (size >= 1024) {
			size /= 1024;
			symbol = "GiB";
		}

		// terabytes (are you fucking crazy?!)
		if (size >= 1024) {
			size /= 1024;
			symbol = "TiB";
		}

		// build string
		return (size + " " + symbol);
	}
}