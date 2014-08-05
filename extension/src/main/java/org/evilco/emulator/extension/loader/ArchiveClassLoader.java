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

package org.evilco.emulator.extension.loader;

import lombok.Getter;
import lombok.NonNull;
import org.evilco.emulator.extension.IExtension;
import org.evilco.emulator.extension.error.ExtensionException;
import org.evilco.emulator.extension.error.InvalidExtensionException;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

/**
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.com>
 */
public class ArchiveClassLoader<T extends IExtension> extends URLClassLoader {

	/**
	 * Stores the extension stored within this class loader.
	 */
	@Getter
	private T extension;

	/**
	 * Constructs a new ArchiveClassLoader instance.
	 * @param url The archive URL.
	 * @param parent The parent class loader.
	 * @param type The extension type.
	 * @throws ExtensionException
	 */
	public ArchiveClassLoader (@NonNull URL url, @NonNull ClassLoader parent, @NonNull Class<T> type) throws ExtensionException {
		super (new URL [] { url }, parent);

		// get reflections
		Reflections reflections = buildReflectionsInstance (this);

		// search for extensions
		Set<Class<? extends T>> extensions = reflections.getSubTypesOf (type);

		// verify amount
		if (extensions.size () == 0) throw new InvalidExtensionException ("Could not find any extension classes in archive " + url.toString () + ".");
		if (extensions.size () > 1) throw new InvalidExtensionException ("More than one extension found in archive " + url.toString () + ".");

		// get first class
		Class<? extends T> extensionClass = extensions.iterator ().next ();

		// initialize
		try {
			this.extension = extensionClass.newInstance ();
		} catch (InstantiationException ex) {
			throw new InvalidExtensionException ("Could not initialize instance of extension class " + extensionClass.getName () + ".", ex);
		} catch (IllegalAccessException ex) {
			throw new InvalidExtensionException ("Default constructor for extension class " + extensionClass.getName () + " is not accessible.", ex);
		}
	}

	/**
	 * Constructs a new ArchiveClassLoader instance.
	 * @param file The file.
	 * @param parent The parent class loader.
	 * @param type The extension type.
	 * @throws ExtensionException
	 * @throws MalformedURLException
	 */
	public ArchiveClassLoader (File file, ClassLoader parent, Class<T> type) throws ExtensionException, MalformedURLException {
		this (file.toURI ().toURL (), parent, type);
	}

	/**
	 * Builds an instance of Reflections for the specified class loader.
	 * @param classLoader The class loader.
	 * @return The reflections instance.
	 */
	public static Reflections buildReflectionsInstance (ClassLoader classLoader) {
		return (new Reflections (new ConfigurationBuilder ().setUrls (ClasspathHelper.forClassLoader (classLoader)).addClassLoader (classLoader)));
	}
}