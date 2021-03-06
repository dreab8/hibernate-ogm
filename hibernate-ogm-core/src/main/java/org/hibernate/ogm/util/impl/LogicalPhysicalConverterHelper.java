/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
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
package org.hibernate.ogm.util.impl;


import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.ogm.datastore.impl.EmptyTupleSnapshot;
import org.hibernate.ogm.datastore.spi.Tuple;
import org.hibernate.ogm.type.GridType;

/**
 * Helper methods to convert an object value into its column values
 *
 * @author Emmanuel Bernard
 */
public class LogicalPhysicalConverterHelper {
	public static Object[] getColumnValuesFromResultset(Tuple resultset, String[] propertyColumnNames) {
		Object[] columnValues;
		final int nbrOfColumns = propertyColumnNames.length;
		columnValues = new Object[nbrOfColumns];
		for ( int columnIndex = 0; columnIndex < nbrOfColumns; columnIndex++ ) {
			columnValues[columnIndex] = resultset.get( propertyColumnNames[columnIndex] );
		}
		return columnValues;
	}

	public static Object[] getColumnsValuesFromObjectValue(Object uniqueKey, GridType gridUniqueKeyType, String[] propertyColumnNames, SessionImplementor session) {
		Tuple tempResultset = new Tuple( EmptyTupleSnapshot.SINGLETON );
		gridUniqueKeyType.nullSafeSet( tempResultset, uniqueKey, propertyColumnNames, session) ;
		Object[] columnValuesFromResultset = LogicalPhysicalConverterHelper.getColumnValuesFromResultset( tempResultset, propertyColumnNames );
		return columnValuesFromResultset;
	}
}
