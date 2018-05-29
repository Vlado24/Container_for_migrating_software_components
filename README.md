# A Container for Migratating Software Components Running on Android OS
Master's thesis: A Container for Migratating Software Components Running on Android

This diploma thesis deals with creating a container for the migration components that are used on the Android operating system, how they operate and distribute, the life cycle of the container, and the ability to customize other components for this container. It also deals with the implementation of container-containing library and a sample application that illustrates how it is possible to link all these elements together.

You can find the fullscript of these thesis [here](https://github.com/Vlado24/Container_for_migrating_software_components/blob/master/projekt.pdf), but in Slovak language.

**This repository contains:**
* [Android Container - separated Android library (module), implementing behaviour](#container)
* [Component - written in Java, adapted for using with Android Container](#component)
* [Manager - server in Django using REST API, responsible for components distribution](#manager)
* [Container Demo - simple example of using component/manager/container written in Android](#demo-application)
* [Test input - Bubble sort .jar and .dex file](#test-input)

## Container
TODO - Reflection and objects serialiation.

## Component
To run component on Android device using Android Container, you need to implement **wrapper for encapsulating computation** and **this methods**:
* ```public void resumeContainer()``` - here you should implement code, which you would like to run, when container is resumed
* ```public void suspendContainer()``` - here you should implement code, which you would like to run, when container is suspended, for examle release some sources
* ```public void setObject(Object loadingObject)``` - encapsulated object which should be serialized
* ```public Object getObject()``` - return encapsulated object which should be reviewed

You can find example of implementing this [here](https://github.com/Vlado24/Container_for_migrating_software_components/tree/master/BubbleSort/src/cz/vutbr/fit/stud/xscesn00).

## Manager
Example of server written in Python, Django framework with REST API. Provides graphical interface to submiting component to run on container and these main endpoints for working with component.
**Endpoints**
* **POST** `api/device/` -
* **POST** `api/file-input/` -
* **GET** `api/file-input/{id}` -
* **POST** `api/file-response/` - 
* **GET** `api/file-response/{id}` -


## Demo application
TODO

## Test input
TODO





© 2018 FIT BUT
