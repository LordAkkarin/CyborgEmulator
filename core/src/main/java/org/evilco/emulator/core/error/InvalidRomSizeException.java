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

package org.evilco.emulator.core.error;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class InvalidRomSizeException extends InvalidRomException {

	/**
	 * Constructs a new InvalidRomSizeException instance.
	 */
	public InvalidRomSizeException () {
		super ();
	}

	/**
	 * Constructs a new InvalidRomSizeException instance.
	 * @param message The error message.
	 */
	public InvalidRomSizeException (String message) {
		super (message);
	}

	/**
	 * Constructs a new InvalidRomSizeException instance.
	 * @param message The error message.
	 * @param cause The error cause.
	 */
	public InvalidRomSizeException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new InvalidRomSizeException instance.
	 * @param cause The error cause.
	 */
	public InvalidRomSizeException (Throwable cause) {
		super (cause);
	}
}