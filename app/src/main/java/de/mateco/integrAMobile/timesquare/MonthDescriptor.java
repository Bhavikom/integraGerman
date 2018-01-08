// Copyright 2012 Square, Inc.
package de.mateco.integrAMobile.timesquare;

import java.util.Date;

public class MonthDescriptor {
  public  final int month;
  public final int year;
  public final Date date;
  public String label;

  public MonthDescriptor(int month, int year, Date date, String label) {
    this.month = month;
    this.year = year;
    this.date = date;
    this.label = label;
  }

  public int getMonth() {
    return month;
  }

  public int getYear() {
    return year;
  }

  public Date getDate() {
    return date;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override public String toString() {
    return "MonthDescriptor{"
        + "label='"
        + label
        + '\''
        + ", month="
        + month
        + ", year="
        + year
        + '}';
  }
}
