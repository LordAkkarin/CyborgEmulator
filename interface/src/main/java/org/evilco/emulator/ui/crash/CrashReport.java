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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.emulator.ui.crash.collector.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class CrashReport implements ICrashReport {

	/**
	 * Stores the divider width.
	 */
	public static final int DIVIDER_WIDTH = 110;

	/**
	 * Stores all information collector instances.
	 */
	private final Map<String, IInformationCollector> collectorMap = new LinkedHashMap<> ();

	/**
	 * Stores the internal logger instance.
	 */
	@Getter (AccessLevel.PROTECTED)
	private static Logger logger = LogManager.getLogger (CrashReport.class);

	/**
	 * Stores the throwable which caused this report to be generated.
	 */
	@Getter
	private final Throwable throwable;

	/**
	 * Constructs a new CrashReport instance.
	 * @param throwable The error.
	 */
	public CrashReport (@NonNull Throwable throwable) {
		this.throwable = throwable;

		this.registerCollector ("Java Version", new JavaVersionInformationCollector ());
		this.registerCollector ("Java VM Version", new JavaVirtualMachineVersionInformationCollector ());
		this.registerCollector ("Java VM Flags", new JavaVirtualMachineFlagsInformationCollector ());
		this.registerCollector ("Operating System", new OperatingSystemInformationCollector ());
		this.registerCollector ("Memory", new MemoryInformationCollector ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> collect () {
		getLogger ().entry ();

		// prepare result map
		LinkedHashMap<String, String> resultMap = new LinkedHashMap<> ();

		// collect information
		for (Map.Entry<String, IInformationCollector> collectorEntry : this.collectorMap.entrySet ()) {
			// collect information
			String value = collectorEntry.getValue ().collect (this);

			// skip if no data was found
			if (value == null) continue;

			// add collected information to results
			resultMap.put (collectorEntry.getKey (), value);
		}

		return getLogger ().exit (resultMap);
	}

	/**
	 * Creates a divider within a string builder.
	 * @param builder The builder.
	 */
	public static void createDivider (StringBuilder builder, char character) {
		for (short i = 0; i < DIVIDER_WIDTH; i++) builder.append (character);
		builder.append ("\n");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHumanReadableReport () {
		getLogger ().entry ();

		// collect information
		Map<String, String> information = this.collect ();

		// create string builder
		StringBuilder builder = new StringBuilder ();

		// add header
		builder.append ("/----- Cyborg Crash Report -----\\");
		builder.append ("Time: " + (new SimpleDateFormat ()).format (new Date ()) + "\n");
		builder.append ("Type: " + this.throwable.getClass ().getName () + "\n");
		builder.append ("Message: " + this.throwable.getMessage () + "\n");
		builder.append ("\n");
		builder.append ("A detailed walkthrough of this error, details about the crash path and environmental information is as follows:\n");
		createDivider (builder, '-');

		// add collected environmental information
		for (Map.Entry<String, String> informationEntry : information.entrySet ()) {
			builder.append (informationEntry.getKey () + ": " + informationEntry.getValue () + "\n");
		}
		createDivider (builder, '-');

		// add stacktrace
		StringWriter stringWriter = new StringWriter ();
		PrintWriter printWriter = new PrintWriter (stringWriter);

		this.throwable.printStackTrace (printWriter);
		builder.append (stringWriter.toString ());

		IOUtils.closeQuietly (printWriter);
		IOUtils.closeQuietly (stringWriter);

		// return finished string
		return getLogger ().exit (builder.toString ());
	}

	/**
	 * Registers a new information collector.
	 * @param displayName Ths display name.
	 * @param collector The collector.
	 */
	protected void registerCollector (@NonNull String displayName, @NonNull IInformationCollector collector) {
		this.collectorMap.put (displayName, collector);
	}
}