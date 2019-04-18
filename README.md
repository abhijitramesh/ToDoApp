![GitHub top language](https://img.shields.io/github/languages/top/abhijitramesh/ToDoApp.svg?style=for-the-badge)
![Travis (.org)](https://img.shields.io/travis/abhijitramesh/ToDoApp.svg?style=for-the-badge)
![GitHub last commit](https://img.shields.io/github/last-commit/abhijitramesh/ToDoApp.svg?style=for-the-badge)
![GitHub stars](https://img.shields.io/github/stars/abhijitramesh/ToDoApp.svg?style=for-the-badge)

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

## Creating an Entity

The entity is where we have to declare the table and the table values for the columns for the database would be declared here. I would be creating a Task.java file to represent the entities.



First we have to initialise the table name for this we use the ```@Entity```
To before the class so that the class name would become the table name. Then we have to declare the variables inside the database. First I am using a primary key name id for this I have used the following 

``` java
@Entity
public class Task implements Serializable {
   @PrimaryKey(autoGenerate = true)
   private int id;
```   

As it is clear from the code snippet above I have declared the class name as Task which implements Serializable.

*Side Note*:
Serialization is used for converting the state of an object to java byte streams.

The ```@PrimaryKey``` denotes that the variable I am going to be declared would be a primary key which means it cannot be duplicated or cannot have null as the value. Further I have declared the id to be integer as I want the data to be used in Id to have integer values and also there is ```autoGenerate``` which is set to ```true``` what this do is create the id values automatically.

This code snippet above is followed by:

``` java
@ColumnInfo(name = "task")
private  String task;

@ColumnInfo(name = "desc")
private  String desc;

@ColumnInfo(name = "finishby")
private  String finishby;

@ColumnInfo(name = "finished")
private  Boolean finished;

```
Here I am declaring values for the column variables. The task, desc and finishby are Strings as the data that these columns would be carrying will be Strings. The finished variable would be a Boolean as it would be having only true or false values. I have also set the names of the values by which the associated data should be called by using ``` name = ```

After this is done we need to set getters and setter for accessing and updating these datas.

``` java
public  int getId(){
   return id;
}
public  void setId(int id){
   this.id = id;
}

public  String getTask(){
   return  task;
}

public  void setTask(String task){
   this.task = task;
}

public  String getFinishBy(){
   return finishby;
}

public  void setFinishBy(String finishby){
   this.finishby = finishby;
}

public boolean getFinished(){
   return finished;
}

public  void  setFinished(boolean finished){
   this.finished=finished;
}

```

In the above code snippet I have set the getters and setter method for these variables.

## Creating DAO

DAO is an interface that communicate with the database and does the CRUD operations.

``` java
@Dao
public interface TaskDAO {
   @Query("SELECT * FROM task")
   List<Task> getAll();


   @Insert
   void insert(Task task);

   @Delete
   void delete(Task task);

   @Update
   void update(Task task);

}
```

Here I have made an interface that have list the Selection ```Query``` and have the ```Insert``` ```Delete``` and ```Update``` constructors.


## Creating the Database

After the DAO Now I will be creating the Database this basically extends the RoomDatabase Class and defines the entity and DAO.

``` java

@Database(entities = {Task.class},version = 1)

public  abstract class AppDatabase extends RoomDatabase{

   public abstract TaskDAO taskDAO();

}

```

## Creating a Database Client


As it is very expensive to create objects of Appdatabase , So we have to create a instance of it by creating a class named Database Client.

``` java
public class DatabaseClient {


  private Context mCtx;

  private static DatabaseClient mInstance;


  //app database object
   private AppDatabase appDatabase;

       private DatabaseClient (Context mCtx){

            // Creating appDatabase with room builder


           this.mCtx = mCtx;
       appDatabase = Room.databaseBuilder(mCtx,AppDatabase.class,"MyToDos").build();

       }
   public static synchronized DatabaseClient getInstance(Context mCtx) {
       if (mInstance == null) {
           mInstance = new DatabaseClient(mCtx);
       }
       return mInstance;
   }

   public AppDatabase getAppDatabase() {
       return appDatabase;
   }
```

Here I have created:

1. The object for database.
1. appDatabase room builder to create the database.
1. And methods to get the database instace. 



## Add Task Activity
Now that the database and the database client is set up we can start working on the activity to add the task

``` java


public class AddTaskActivity extends AppCompatActivity {
   private EditText editTextTask, editTextDesc, editTextFinishBy;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_add_task);

       editTextTask = findViewById(R.id.editTextTask);
       editTextDesc = findViewById(R.id.editTextDesc);
       editTextFinishBy = findViewById(R.id.editTextFinishBy);

       findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               saveTask();
           }
       });
   }

   private void saveTask() {
       final String sTask = editTextTask.getText().toString().trim();
       final String sDesc = editTextDesc.getText().toString().trim();
       final String sFinishBy = editTextFinishBy.getText().toString().trim();

       if (sTask.isEmpty()) {
           editTextTask.setError("Task required");
           editTextTask.requestFocus();
           return;
       }

       if (sDesc.isEmpty()) {
           editTextDesc.setError("Desc required");
           editTextDesc.requestFocus();
           return;
       }

       if (sFinishBy.isEmpty()) {
           editTextFinishBy.setError("Finish by required");
           editTextFinishBy.requestFocus();
           return;
       }

       class SaveTask extends AsyncTask<Void, Void, Void> {

           @Override
           protected Void doInBackground(Void... voids) {

               //creating a task
               Task task = new Task();
               task.setTask(sTask);
               task.setDesc(sDesc);
               task.setFinishBy(sFinishBy);
               task.setFinished(false);

               //adding to database
               DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                       .taskDAO()
                       .insert(task);
               return null;
           }

           @Override
           protected void onPostExecute(Void aVoid) {
               super.onPostExecute(aVoid);
               finish();
               startActivity(new Intent(getApplicationContext(), MainActivity.class));
               Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
           }
       }

       SaveTask st = new SaveTask();
       st.execute();
   }

}
```
Here in the background thread a new task is created and the values for these task has been taken by the variables stask sdesc sFinishBy and initially the finished is set to false.

After setting the values a new database is created using by calling the Database client and setting the contents in this database.












