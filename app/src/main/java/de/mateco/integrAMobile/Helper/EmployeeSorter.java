package de.mateco.integrAMobile.Helper;

import java.util.Comparator;

import de.mateco.integrAMobile.model.EmployeeModel;

/**
 * Created by mmehta on 01.09.2015.
 */
public class EmployeeSorter implements Comparator<EmployeeModel> {
    @Override
    public int compare(EmployeeModel employee, EmployeeModel employeeCompareTo)
    {
        return employee.getNachname().compareTo(employeeCompareTo.getNachname());
    }
}
