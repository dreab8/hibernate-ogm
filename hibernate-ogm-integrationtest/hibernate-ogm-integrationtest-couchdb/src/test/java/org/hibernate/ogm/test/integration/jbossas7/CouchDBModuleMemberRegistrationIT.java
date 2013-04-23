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
package org.hibernate.ogm.test.integration.jbossas7;

import org.hibernate.ogm.test.integration.jbossas7.model.Member;
import org.hibernate.ogm.test.integration.jbossas7.util.ModuleMemberRegistrationDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceDescriptor;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.runner.RunWith;

import static org.hibernate.ogm.cfg.impl.Version.getVersionString;

/**
 * Test the hibernate OGM module in JBoss AS using CouchDB
 *
 * @author Andrea Boriero <dreborier@gmail.com>
 */
@RunWith(Arquillian.class)
public class CouchDBModuleMemberRegistrationIT extends ModuleMemberRegistrationScenario {
	@Deployment
	public static Archive<?> createTestArchive() {
		return new ModuleMemberRegistrationDeployment.Builder( CouchDBModuleMemberRegistrationIT.class )
				.persistenceXml( persistenceXml() )
				.manifestDependencies( "org.hibernate:ogm services" )
				.createDeployment()
                    .addAsLibraries(
                            DependencyResolvers.use( MavenDependencyResolver.class )
                                .artifact( "org.hibernate.ogm:hibernate-ogm-couchdb:" + getVersionString() )
                                    .exclusion( "org.hibernate.ogm:hibernate-ogm-core" )
                                    .exclusion( "org.hibernate:*" )
                                .resolveAs( JavaArchive.class ) );

	}

	private static PersistenceDescriptor persistenceXml() {
		return Descriptors.create( PersistenceDescriptor.class )
                    .version( "2.0" )
                    .createPersistenceUnit()
                        .name( "primary" )
                        .provider( "org.hibernate.ogm.jpa.HibernateOgmPersistence" )
                        .clazz( Member.class.getName() )
                        .getOrCreateProperties()
                            .createProperty().name( "hibernate.ogm.datastore.provider" ).value( "couchdb" ).up()
                            .createProperty().name( "hibernate.ogm.couchdb.host" ).value( "localhost" ).up()
                            .createProperty().name( "hibernate.ogm.couchdb.port" ).value( "5984" ).up()
                            .createProperty().name( "hibernate.ogm.couchdb.database" ).value( "ogm_test_database" ).up()
                        .up().up();
	}
}
