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

import org.evilco.emulator.core.graphic.IScreen;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public interface IMatrixScreen<T> extends IScreen {

	/**
	 * Returns the pixel value.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The value.
	 */
	public T getPixel (short x, short y);

	/**
	 * Sets a pixel value.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param value The value.
	 */
	public void setPixel (short x, short y, T value);
}