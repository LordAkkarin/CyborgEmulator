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

package org.evilco.emulator.extension;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.emulator.extension.error.ExtensionException;
import org.evilco.emulator.extension.error.InvalidExtensionDirectoryException;
import org.evilco.emulator.extension.error.InvalidExtensionException;
import org.evilco.emulator.extension.error.UnknownExtensionException;
import org.evilco.emulator.extension.loader.ArchiveClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public abstract class AbstractExtensionManager<T extends IExtension> {

	/**
	 * Defines a list of valid file extensions.
	 */
	public static final String[] VALID_FILE_EXTENSIONS = new String[] {
		"jar",
		"zip"
	};

	/**
	 * Stores the extension type.
	 */
	@Getter
	private final Class<T> extensionType;

	/**
	 * Stores all extension class loaders.
	 * Note: This is a private field as we need to be able to delete contents in order to unload extensions.
	 */
	private final Map<String, ArchiveClassLoader<T>> loaderMap = new HashMap<> ();

	/**
	 * Stores the logger instance.
	 */
	@Getter
	private static final Logger logger = LogManager.getLogger (AbstractExtensionManager.class);

	/**
	 * Constructs a new ExtensionManager instance.
	 * @param extensionType The extension type.
	 */
	public AbstractExtensionManager (Class<T> extensionType) {
		this.extensionType = extensionType;
	}

	/**
	 * Checks whether the specified extension is already loaded.
	 * @param identifier The extension identifier.
	 * @return True if the extension is loaded.
	 */
	public boolean isLoaded (String identifier) {
		return this.loaderMap.containsKey (identifier);
	}

	/**
	 * Loads a extension from a JAR file.
	 * @param file The file.
	 * @return The extension identifier.
	 * @throws ExtensionException Occurs if an extension is malformed.
	 */
	public String load (@NonNull File file) throws ExtensionException {
		try {
			// log
			getLogger ().info ("Attempting to load extension from file " + file.getAbsolutePath () + ".");

			// create class loader
			ArchiveClassLoader<T> classLoader = new ArchiveClassLoader<T> (file, this.getClass ().getClassLoader (), this.extensionType);

			// check identifier
			if (this.isLoaded (classLoader.getExtension ().getIdentifier ())) throw new InvalidExtensionException ("An extension with the identifier \"" + classLoader.getExtension ().getIdentifier () + "\" does already exist.");

			// call enable method
			classLoader.getExtension ().onEnable (this);

			// call handler
			this.onSuccessfulExtensionLoad (classLoader.getExtension ());

			// store
			this.loaderMap.put (classLoader.getExtension ().getIdentifier (), classLoader);

			// log
			getLogger ().info ("Loaded extension " + classLoader.getExtension ().getIdentifier () + ".");

			// finish
			return classLoader.getExtension ().getIdentifier ();
		} catch (MalformedURLException ex) {
			throw new InvalidExtensionException ("Could not load archive " + file.getAbsolutePath () + ": " + ex.getMessage (), ex);
		}
	}

	/**
	 * Loads all extensions from a directory.
	 * @param file The directory.
	 * @throws ExtensionException Occurs if the plugin directory is invalid.
	 */
	public void loadAll (@NonNull File file) throws ExtensionException {
		// verify
		if (!file.isDirectory ()) throw new InvalidExtensionDirectoryException (file.getAbsolutePath () + " is not a directory.");
		if (!file.canRead ()) throw new InvalidExtensionDirectoryException (file.getAbsolutePath () + " is not readable.");

		// log
		getLogger ().info ("Attempting to load all extensions from directory " + file.getAbsolutePath () + ".");

		// store data
		int extensionCount = this.loaderMap.size ();

		// get iterator
		Iterator<File> iterator = FileUtils.iterateFiles (file, VALID_FILE_EXTENSIONS, false);

		// iterate
		while (iterator.hasNext ()) {
			try {
				this.load (iterator.next ());
			} catch (ExtensionException ex) {
				getLogger ().error ("Could not load one or more extensions: " + ex.getMessage (), ex);
			}
		}

		// log
		getLogger ().info ("Loaded " + (this.loaderMap.size () - extensionCount) + " extensions.");
	}

	/**
	 * Handles successful extension loads.
	 * @param extension The extension instance.
	 */
	protected abstract void onSuccessfulExtensionLoad (T extension);

	/**
	 * Handles successful extension unloads.
	 * @param identifier The extension identifier.
	 */
	protected abstract void onSuccessfulExtensionUnload (String identifier);

	/**
	 * Unloads an extension.
	 * @param identifier The extension identifier.
	 * @throws ExtensionException Occurs if an extension is not known to the system.
	 */
	public void unload (@NonNull String identifier) throws ExtensionException {
		// log
		getLogger ().info ("Attempting to unload extension " + identifier + ".");

		// verify
		if (!this.isLoaded (identifier)) throw new UnknownExtensionException ("Cannot unload unknown extension \"" + identifier + "\".");

		// call disable method
		this.loaderMap.get (identifier).getExtension ().onDisable (this);

		// call handler
		this.onSuccessfulExtensionUnload (identifier);

		// remove from map
		this.loaderMap.remove (identifier);

		// request garbage collection
		System.gc ();
	}

	/**
	 * Unloads all extensions.
	 * @throws ExtensionException
	 */
	public void unloadAll () throws ExtensionException {
		// log
		getLogger ().info ("Attempting to unload all extensions.");

		// iterate over elements
		for (String identifier : this.loaderMap.keySet ()) {
			this.unload (identifier);
		}

		// log
		getLogger ().info ("Successfully unloaded all extensions.");
	}
}