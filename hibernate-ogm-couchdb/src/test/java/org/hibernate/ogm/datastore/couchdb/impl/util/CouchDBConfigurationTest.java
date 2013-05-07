/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.ogm.datastore.couchdb.impl.util;

import org.hibernate.ogm.dialect.couchdb.Environment;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Andrea Boriero <dreborier@gmail.com>
 */
public class CouchDBConfigurationTest {

	private Map<String, String> configurationsVaue;
	private CouchDBConfiguration configuration;

	@Before
	public void setUp() {
		configurationsVaue = new HashMap<String, String>();
		configuration = new CouchDBConfiguration();
	}

	@Test
	public void shouldReturnTheDefaultValueIfThePortKeyIsNotPresentAsAConfigurationValue() {
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabasePort(), is( Integer.valueOf( CouchDBConfiguration.DEFAULT_COUCHDB_PORT ) ) );
	}

	@Test
	public void shouldReturnTheDefaultValueIfThePortConfigurationValueIsTheEmptyString() {
		configurationsVaue.put( Environment.COUCHDB_PORT, "" );
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabasePort(), is( Integer.valueOf( CouchDBConfiguration.DEFAULT_COUCHDB_PORT ) ) );
	}

	@Test
	public void shouldReturnThePortConfigured() {
		final int configuredPortValue = 8080;
		configurationsVaue.put( Environment.COUCHDB_PORT, String.valueOf( configuredPortValue ) );
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabasePort(), is( configuredPortValue ) );
	}

	@Test
	public void shouldReturnLocalhostIfTheHostNotPresentAsAConfigurationValue() {
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabaseHost(), is( "localhost" ) );
	}

	@Test
	public void shouldReturnLocalhostIfTheHostConfigurationValueIsTheEmptyString() {
		configurationsVaue.put( Environment.COUCHDB_HOST, " " );
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabaseHost(), is( "localhost" ) );
	}

	@Test
	public void shouldReturnTheHostConfiguredValue() {
		final String configuredHostValue = "192.168.2.2";
		configurationsVaue.put( Environment.COUCHDB_HOST, configuredHostValue );
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabaseHost(), is( configuredHostValue ) );
	}

	@Test
	public void shouldReturnNullIfTheDatabaseNameIsNotPresentAsAConfigurationValue() {
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabaseName(), nullValue() );
	}

	@Test
	public void shouldReturnNullStringIfTheDatabaseNameConfigurationValueIsTheEmptyString() {
		configurationsVaue.put( Environment.COUCHDB_DATABASE, "" );
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabaseName(), nullValue() );
	}

	@Test
	public void shouldReturnTheDatabaseNameConfiguredValue() {
		final String configuredDatabaseName = "test";
		configurationsVaue.put( Environment.COUCHDB_DATABASE, configuredDatabaseName );
		configuration.setConfigurationValues( configurationsVaue );

		assertThat( configuration.getDatabaseName(), is( configuredDatabaseName ) );
	}

}
