package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    private static volatile TodocDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    public static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues_1 = new ContentValues();
                ContentValues contentValues_2 = new ContentValues();
                ContentValues contentValues_3 = new ContentValues();

                contentValues_1.put("id", 1);
                contentValues_1.put("name", "Projet Tartampion");
                contentValues_1.put("color", 0xFFEADAD1);

                contentValues_2.put("id", 2);
                contentValues_2.put("name", "Projet Lucidia");
                contentValues_2.put("color", 0xFFB4CDBA);

                contentValues_3.put("id", 3);
                contentValues_3.put("name", "Projet Circus");
                contentValues_3.put("color", 0xFFA3CED2);

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues_1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues_2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues_3);
            }
        };
    }
}
