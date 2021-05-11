// IPerson.aidl
package com.qiu.base;

// Declare any non-default types here with import statements

interface IPerson {
    void setName(String s);
    String getName();
    List<String> getNameList();
    String getThreadPid();
}
