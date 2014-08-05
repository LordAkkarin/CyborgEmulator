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

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class JavaVirtualMachineFlagsInformationCollector implements IInformationCollector {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String collect (ICrashReport crashReport) {
		StringBuilder builder = new StringBuilder ();
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean ();
		short flagCounter = 0;

		// collect flags
		for (String argument : runtimeMXBean.getInputArguments ()) {
			// skip other flags
			if (!argument.startsWith ("-X")) continue;

			// add space
			if (flagCounter++ > 0)
				builder.append (" ");

			// add argument
			builder.append (argument);
		}

		// skip information collector
		if (flagCounter == 0) return null;

		// format flags
		return String.format ("%s (%d in total)", builder.toString (), flagCounter);
	}
}