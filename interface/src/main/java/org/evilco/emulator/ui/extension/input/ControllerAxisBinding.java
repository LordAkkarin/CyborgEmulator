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

package org.evilco.emulator.ui.extension.input;

import lombok.Getter;
import lombok.Setter;
import org.evilco.emulator.ui.Cyborg;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ControllerAxisBinding implements IAxisBinding {

	/**
	 * Stores the controller axis.
	 */
	@Getter
	@Setter
	private ControllerAxis axis = null;

	/**
	 * Stores the controller index.
	 */
	@Getter
	@Setter
	private Integer controllerIndex = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAxisValue () {
		// get controller
		Controller controller = Controllers.getController (this.controllerIndex);

		// verify
		if (controller == null) return 0.0f;

		// get axis
		switch (this.axis) {
			case LEFT_X: return controller.getXAxisValue ();
			case LEFT_Y: return controller.getYAxisValue ();
			case LEFT_Z: return controller.getZAxisValue ();
			case RIGHT_X: return controller.getRXAxisValue ();
			case RIGHT_Y: return controller.getRYAxisValue ();
			case RIGHT_Z: return controller.getRZAxisValue ();
			default: return 0.0f;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDeadZone () {
		// get controller
		Controller controller = Controllers.getController (this.controllerIndex);

		// verify
		if (controller == null) return 0.0f;

		// get axis
		switch (this.axis) {
			case LEFT_X: return controller.getXAxisDeadZone ();
			case LEFT_Y: return controller.getYAxisDeadZone ();
			case LEFT_Z: return controller.getZAxisDeadZone ();
			case RIGHT_X: return controller.getRXAxisDeadZone ();
			case RIGHT_Y: return controller.getRYAxisDeadZone ();
			case RIGHT_Z: return controller.getRZAxisDeadZone ();
			default: return 0.0f;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayName () {
		if (Controllers.getController (this.controllerIndex) == null) return null;
		if (this.axis == null) return null;

		// create name
		StringBuilder name = new StringBuilder (Controllers.getController (this.controllerIndex).getName ());

		// append axis
		switch (this.axis) {
			case LEFT_X: name.append ("X"); break;
			case LEFT_Y: name.append ("Y"); break;
			case LEFT_Z: name.append ("Z"); break;
			case RIGHT_X: name.append ("RX"); break;
			case RIGHT_Y: name.append ("RY"); break;
			case RIGHT_Z: name.append ("RZ"); break;
		}

		// append axis string
		name.append ("-" + Cyborg.getTranslation ("controller.axis"));

		// return finished name
		return name.toString ();
	}
}