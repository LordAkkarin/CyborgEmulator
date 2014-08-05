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

package org.evilco.emulator.core.processor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public abstract class AbstractProcessorThread extends Thread {

	/**
	 * Stores the maximum amount of cycle times stored by the thread.
	 */
	public static final int MAXIMUM_CYCLE_TIME_MEASURES = 10;

	/**
	 * Indicates whether the processor thread is alive.
	 */
	private boolean alive;

	/**
	 * Stores most recent cycle timings.
	 */
	private Stack<Long> cycleTime;

	/**
	 * Stores the internal logger.
	 */
	@Getter (AccessLevel.PROTECTED)
	private static final Logger logger = LogManager.getLogger (AbstractProcessorThread.class);

	/**
	 * Stores the emulator processor.
	 */
	@Getter
	private final IProcessor processor;

	/**
	 * Constructs a new ProcessorThread instance.
	 * @param processor The processor implementation.
	 */
	public AbstractProcessorThread (@NonNull IProcessor processor) {
		this.processor = processor;

		this.setName ("Processor-" + processor.getClass ().getSimpleName ());
	}

	/**
	 * Returns the average CPU cycle time.
	 * @return The average cycle time.
	 */
	public long getAverageCycleTime () {
		// check size
		if (this.cycleTime.size () == 0) return 0;

		// initialize average
		long average = 0;

		// add all times
		for (long time : this.cycleTime) {
			average += time;
		}

		// divide by amount
		return (average / this.cycleTime.size ());
	}

	/**
	 * Returns the time the thread needs to wait between cycles to keep the cycles per second steady.
	 * @return The sleep time needed per cycle.
	 */
	public long getSleepTimePerCycle () {
		// get average cycle time
		long average = this.getAverageCycleTime ();

		// calculate average time spent per second
		long timePerSecond = (average * this.processor.getSpeed ());

		// calculate time left
		long timeLeft = (1000 - timePerSecond);

		// validate
		if (timeLeft <= 0) return 0;

		// calculate amount of sleep needed per cycle to keep value steady
		return (timeLeft / this.processor.getSpeed ());
	}

	/**
	 * Handles emulation errors.
	 * @param throwable The error.
	 */
	protected abstract void handleError (Throwable throwable);

	/**
	 * Resets the thread.
	 */
	protected void reset () {
		// reset cycle time stack
		this.cycleTime = new Stack<> ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run () {
		super.run ();

		// enable thread
		this.alive = true;

		// initialize values
		this.reset ();

		// initialize processor
		this.processor.initialize ();

		// enter loop
		try {
			while (this.alive) {
				// store start time
				long start = System.currentTimeMillis ();

				// emulate cycle
				this.processor.cycle ();

				// store end time
				long end = System.currentTimeMillis ();

				// add to stack
				this.cycleTime.push ((end - start));

				// fix amount
				if (this.cycleTime.size () > MAXIMUM_CYCLE_TIME_MEASURES) this.cycleTime.remove (0);

				// sleep
				Thread.sleep (this.getSleepTimePerCycle ());
			}
		} catch (Exception ex) {
			// shutdown
			this.shutdown ();

			// report
			getLogger ().error ("An error occurred during processor emulation: " + ex.getMessage (), ex);

			// call handler
			this.handleError (ex);
		}

		// shutdown processor
		this.processor.shutdown ();
	}

	/**
	 * Shuts down the processor.
	 */
	public void shutdown () {
		this.alive = false;
	}
}