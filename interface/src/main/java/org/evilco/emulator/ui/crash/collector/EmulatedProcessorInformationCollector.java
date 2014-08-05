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

import lombok.Getter;
import lombok.NonNull;
import org.evilco.emulator.core.processor.IProcessor;
import org.evilco.emulator.ui.crash.ICrashReport;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class EmulatedProcessorInformationCollector implements IInformationCollector {

	/**
	 * Stores the processor to analyze.
	 */
	@Getter
	private final IProcessor processor;

	/**
	 * Constructs a new EmulatedProcessorInformationCollector.
	 * @param processor The processor.
	 */
	public EmulatedProcessorInformationCollector (@NonNull IProcessor processor) {
		this.processor = processor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String collect (ICrashReport crashReport) {
		return (this.processor.getClass ().getName () + " running at " + this.processor.getSpeed () + " Hz");
	}
}