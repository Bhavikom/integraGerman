package de.mateco.integrAMobile.Helper;

import java.util.Comparator;

import de.mateco.integrAMobile.model_logonsquare.CustomerActivityEmployeeListItem;

/**
 * Created by mmehta on 01.09.2015.
 */
public class EmployeeSorter implements Comparator<CustomerActivityEmployeeListItem> {
    @Override
    public int compare(CustomerActivityEmployeeListItem employee, CustomerActivityEmployeeListItem employeeCompareTo)
    {
        return employee.getNachname().compareTo(employeeCompareTo.getNachname());
    }
}
