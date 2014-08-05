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

import lombok.NonNull;
import org.evilco.emulator.core.graphic.IScreen;
import org.evilco.emulator.ui.crash.ICrashReport;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class EmulatedScreenInformationCollector implements IInformationCollector {

	/**
	 * Stores the currently active screen.
	 */
	private final IScreen screen;

	/**
	 * Constructs a new EmulatedScreenInformationCollector instance.
	 * @param screen The screen.
	 */
	public EmulatedScreenInformationCollector (@NonNull IScreen screen) {
		this.screen = screen;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String collect (ICrashReport crashReport) {
		return (this.screen.getClass ().getName () + " (running at " + this.screen.getWidth () + "x" + this.screen.getHeight () + " @ " + this.screen.getFrameRate () + "Hz");
	}
}