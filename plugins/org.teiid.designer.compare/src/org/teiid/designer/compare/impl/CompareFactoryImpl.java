/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.compare.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.teiid.designer.compare.CompareFactory;
import org.teiid.designer.compare.ComparePackage;
import org.teiid.designer.compare.DifferenceDescriptor;
import org.teiid.designer.compare.DifferenceReport;
import org.teiid.designer.compare.DifferenceType;
import org.teiid.designer.compare.PropertyDifference;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 *
 * @since 8.0
 */
public class CompareFactoryImpl extends EFactoryImpl implements CompareFactory {
    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public CompareFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EObject create( EClass eClass ) {
        switch (eClass.getClassifierID()) {
            case ComparePackage.DIFFERENCE_DESCRIPTOR:
                return createDifferenceDescriptor();
            case ComparePackage.DIFFERENCE_REPORT:
                return createDifferenceReport();
            case ComparePackage.PROPERTY_DIFFERENCE:
                return createPropertyDifference();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object createFromString( EDataType eDataType,
                                    String initialValue ) {
        switch (eDataType.getClassifierID()) {
            case ComparePackage.DIFFERENCE_TYPE: {
                DifferenceType result = DifferenceType.get(initialValue);
                if (result == null) throw new IllegalArgumentException(
                                                                       "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                return result;
            }
            case ComparePackage.ANY_TYPE:
                return createAnyTypeFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String convertToString( EDataType eDataType,
                                   Object instanceValue ) {
        switch (eDataType.getClassifierID()) {
            case ComparePackage.DIFFERENCE_TYPE:
                return instanceValue == null ? null : instanceValue.toString();
            case ComparePackage.ANY_TYPE:
                return convertAnyTypeToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public DifferenceDescriptor createDifferenceDescriptor() {
        DifferenceDescriptorImpl differenceDescriptor = new DifferenceDescriptorImpl();
        return differenceDescriptor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public DifferenceReport createDifferenceReport() {
        DifferenceReportImpl differenceReport = new DifferenceReportImpl();
        return differenceReport;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public PropertyDifference createPropertyDifference() {
        PropertyDifferenceImpl propertyDifference = new PropertyDifferenceImpl();
        return propertyDifference;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Object createAnyTypeFromString( EDataType eDataType,
                                           String initialValue ) {
        return super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String convertAnyTypeToString( EDataType eDataType,
                                          Object instanceValue ) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public ComparePackage getComparePackage() {
        return (ComparePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ComparePackage getPackage() {
        return ComparePackage.eINSTANCE;
    }

} // CompareFactoryImpl
