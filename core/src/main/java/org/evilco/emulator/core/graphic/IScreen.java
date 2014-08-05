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

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public interface IScreen {

	/**
	 * Clears the screen.
	 */
	public void clear ();

	/**
	 * Draws the screen.
	 */
	public void draw ();

	/**
	 * Returns the desired frame rate.
	 * @return The frame rate.
	 */
	public int getFrameRate ();

	/**
	 * Returns the screen height.
	 * @return The height.
	 */
	public short getHeight ();

	/**
	 * Returns the screen width.
	 * @return The width.
	 */
	public short getWidth ();

	/**
	 * Initializes the screen.
	 */
	public void initialize ();

	/**
	 * Shuts down the screen.
	 */
	public void shutdown ();
}