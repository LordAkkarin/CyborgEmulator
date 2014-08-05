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

package org.evilco.emulator.core.graphic;

import lombok.Getter;
import lombok.NonNull;
import org.lwjgl.opengl.Display;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public abstract class AbstractScreenThread extends Thread {

	/**
	 * Indicates whether the thread is still alive.
	 */
	private boolean alive;

	/**
	 * Stores the screen.
	 */
	@Getter
	private final IScreen screen;

	/**
	 * Constructs a new ScreenThread.
	 * @param screen The screen.
	 */
	public AbstractScreenThread (@NonNull IScreen screen) {
		this.screen = screen;

		// set thread name
		this.setName ("Screen-" + screen.getClass ().getSimpleName ());
	}

	/**
	 * Initializes the graphics engine.
	 */
	protected abstract void initialize ();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run () {
		super.run ();

		// enable thread
		this.alive = true;

		// initialize engine
		this.initialize ();

		// initialize screen
		this.screen.initialize ();

		// draw
		while (this.alive) {
			// draw
			this.screen.draw ();

			// update screen
			Display.update ();

			// synchronize
			Display.sync (this.screen.getFrameRate ());
		}

		// shutdown screen
		this.screen.shutdown ();

		// shutdown engine
		this.shutdown ();
	}

	/**
	 * Shuts down the graphics engine.
	 */
	protected abstract void shutdown ();

	/**
	 * Stops the thread.
	 */
	public void stopThread () {
		this.alive = false;
	}
}