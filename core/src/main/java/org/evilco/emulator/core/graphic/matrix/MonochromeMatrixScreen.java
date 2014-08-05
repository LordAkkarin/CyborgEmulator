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

package org.evilco.emulator.core.graphic.matrix;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class MonochromeMatrixScreen implements IMatrixScreen<Boolean> {

	/**
	 * Stores the screen height.
	 */
	@Getter
	private final short height;

	/**
	 * Stores the screen matrix.
	 */
	private boolean[][] matrix;

	/**
	 * Stores the screen width.
	 */
	@Getter
	private final short width;

	/**
	 * Constructs a new MonochromeMatrixScreen instance.
	 * @param width The width.
	 * @param height The height.
	 */
	public MonochromeMatrixScreen (short width, short height) {
		this.width = width;
		this.height = height;

		// reset screen
		this.clear ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getFrameRate () {
		return 60;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getPixel (short x, short y) {
		return this.matrix[y][x];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize () {
		GL11.glMatrixMode (GL11.GL_PROJECTION);
		GL11.glLoadIdentity ();

		GL11.glOrtho (0, this.getWidth (), this.getHeight (), 0, 1, -1);

		GL11.glMatrixMode (GL11.GL_MODELVIEW);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void clear () {
		// reset array
		this.matrix = new boolean[this.height][];

		// create lines
		for (short y = 0; y < this.height; y++) {
			// create line
			this.matrix[y] = new boolean[this.width];

			// fill line
			for (short x = 0; x < this.width; x++) {
				this.matrix[y][x] = false;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void draw () {
		// clear screen
		GL11.glClearColor (0, 0, 0, 1);
		GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glColor3f (1f, 1f, 1f); // TODO: Make color adjustable

		// draw pixels
		for (short y = 0; y < this.height; y++) {
			for (short x = 0; x < this.width; x++) {
				// skip disabled pixels
				if (!this.getPixel (x, y)) continue;

				// draw
				GL11.glRecti (x, y, (x + 1), (y + 1));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void setPixel (short x, short y, Boolean value) {
		this.matrix[y][x] = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown () { }

	/**
	 * Toggles a pixel state.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return True if the pixel was turned on.
	 */
	public synchronized boolean togglePixel (short x, short y) {
		this.setPixel (x, y, !this.getPixel (x, y));
		return this.getPixel (x, y);
	}
}