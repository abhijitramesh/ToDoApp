# Room Persistent Library

## What is room ?
Room provides an abstraction layer over SQLite for managing databases. Room makes it easier for creating,reading,updating and deleting data from databases.

## Three main components of Room Persistent Library
1. #### Database
   1. This is an abstract class where we define all our entities.
   1. This is the main access point that interacts with the data that is stored by the app.
1. #### Entity
   1. This is a model class which would be given a name and the name of this class would be the name of out table
   1. The variables that is contained on this class would represent the columns of the table
1. #### DAO (Data Access Objects)
   1. This is an interface.
   1. It contains methods for accessing the database.

## Adding Dependencies

The dependencies to be added to import the Room Persistent Library are:
 ``` java
 implementation "android.arch.persistence.room:runtime:$room_version"
annotationProcessor "android.arch.persistence.room:compiler:$room_version"
testImplementation "android.arch.persistence.room:testing:$room_version"
```

_Note:_ $room_version should be replaced with the latest room version or the variable should be predefined with the value for latest room version.

For this app I have created three activities now

1.  MainActivity.java
1.  AddTaskActivity.java
1.  UpdateTask.java

The three activities have three xml files and one more file for the recycler view

The files are:

1. activity_add_task.xml
1. activity_main.xml
1. activity_update_task.xml
1. recyclerview_task.xml

