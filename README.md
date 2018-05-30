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

## How to run
Requirements: 
* Python 3 (tested on 3.6.5)
* [pip package](https://www.tecmint.com/install-pip-in-linux/)
* [Android Studio](https://developer.android.com/studio/) 

### To run server
1. Enter the directory and run command:
```bash
pip install -r requirements.txt
```

2. After that we need to create database's models with command:
```bash
./manage.py migrate
```
3. When proccess finished we are ready to run our server, but firstly we need to set IP address of your computer into **ALLOWED_HOST** variable, located in **settings.py**.
4. We are ready to start server with command:
```bash
./manage.py runserver
```

### To run application
To run application you need to download Demo Application. Then you need to change UrlConst.java variables:
* SERVER_URL
* SERVER_URL_MEDIA - without '/' in the end.

Open your browser and go to http://{SERVER_URL}/api/file-input/ upload your **.dex** file there and see your logcat in Android Studio where magic happens.

## Container
The main module responsible for running computation on Android. It uses **reflection** and **object's serialization** for resuming and pausing computation. It also implements AsyncTask for running computation on background thread. This AsyncTask implement `sleep()` method for testing purpose and you can delete it.

You can run container in your application like this:
1. [Download container](https://github.com/Vlado24/Container_for_migrating_software_components/tree/master/androidcontainer) and [add it to your project](https://github.com/MagicMicky/FreemiumLibrary/wiki/Import-the-library-in-Android-Studio)
2. Add it into your Activity:

```java
  AndroidContainer container;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    container = new AndroidContainer();
  }
  
  @Override
  protected void onResume() {
     super.onResume();
     // String classToLoad - name of class to be loaded through reflection
     // dexPath - filepath to .dex file to be reviewed
     // outPath - filepath to directory where output file will be saved.
     container.onContainerCreate(getApplicationContext(), classToLoad, dexPath, outPath);
     container.onContainerResume();
  }
  
  @Override
  protected void onPause() {
     super.onPause();
     
     container.onContainerSuspend();
  }
```

Or you can use it in Service, for example you can see example implementation [here](https://github.com/Vlado24/Container_for_migrating_software_components/blob/master/ContainerDemo/app/src/main/java/cz/vutbr/fit/stud/xscesn00/containerdemo/service/CustomMessagingService.java).




## Component
To run component on Android device using Android Container, you need to implement **wrapper for encapsulating computation** and **these methods**:
* ```public void resumeContainer()``` - here you should implement code, which you would like to run, when container is resumed
* ```public void suspendContainer()``` - here you should implement code, which you would like to run, when container is suspended, for examle release some sources
* ```public void setObject(Object loadingObject)``` - encapsulated object which should be serialized
* ```public Object getObject()``` - return encapsulated object which should be reviewed

You can find example of implementing this [here](https://github.com/Vlado24/Container_for_migrating_software_components/tree/master/BubbleSort/src/cz/vutbr/fit/stud/xscesn00).

## Manager
Example of server written in Python, Django framework using REST API. Provides graphical interface to submiting component to run on container and these main endpoints for working with component.
**Endpoints**
* **POST** `api/device/` -
* **POST** `api/file-input/` -
* **GET** `api/file-input/{id}` -
* **POST** `api/file-response/` - 
* **GET** `api/file-response/{id}` -

All endpoints are available on {SERVER_URL}/doc.

## Demo application
Demo application is located in **ContainerDemo** folder. You can download it and open in Android Studio. The job with Android Container is implemented via Service, which started on receiving message from Firebase Cloud Messaging. All the necessary files is downloaded. After that, two BroadcastReceiver is registred on screen on/off. When screen is off, the computation starts, on screen on, computation suspends.

## Test input
This directory contains **.jar** file of **Bubble sort component** and converted version of this file into **.dex**. For reflection Android uses **DexClassLoader**, so **.jar** file need to be converted to **.dex** file using command, where Android tools location is (/Android/sdk/build-tools/{version} on Mac):

`./dx --dex --output=“/Component/Component.dex” “/Component/Component.jar`






© 2018 FIT BUT
