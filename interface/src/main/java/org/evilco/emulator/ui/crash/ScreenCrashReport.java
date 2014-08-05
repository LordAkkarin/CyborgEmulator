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

package org.evilco.emulator.ui.crash;

import lombok.NonNull;
import org.evilco.emulator.core.graphic.IScreen;
import org.evilco.emulator.ui.crash.collector.EmulatedScreenInformationCollector;
import org.evilco.emulator.ui.crash.collector.LWJGLVersionInformationCollector;
import org.evilco.emulator.ui.crash.collector.OpenGLInformationCollector;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ScreenCrashReport extends CrashReport {

	/**
	 * Constructs a new ScreenCrashReport instance.
	 * @param throwable The error.
	 */
	public ScreenCrashReport (@NonNull Throwable throwable, @NonNull IScreen screen) {
		super (throwable);

		this.registerCollector ("LWJGL Version", new LWJGLVersionInformationCollector ());
		this.registerCollector ("OpenGL Version", new OpenGLInformationCollector ());
		this.registerCollector ("Emulated Display", new EmulatedScreenInformationCollector (screen));
	}
}