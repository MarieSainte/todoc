package com.cleanup.todoc.database.dao;

import static com.cleanup.todoc.database.TodocDatabase.prepopulateDatabase;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.LiveDataTestUtil;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private TodocDatabase database;

    private static final Task task_Test = new Task(1,1,"Nom",123);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database =
                Room.inMemoryDatabaseBuilder(
                        InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class)
                .allowMainThreadQueries().addCallback(prepopulateDatabase())
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void CRUDTest() throws InterruptedException {

        // INSERT
        this.database.taskDao().insertTask(task_Test);

        //GETTER
        List<Task> taskListTest = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());

        //GETTER TEST
        assertFalse(taskListTest.isEmpty());

        //DELETE
        this.database.taskDao().deleteTask(1);
        taskListTest = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        //DELETE TEST
        assertTrue(taskListTest.isEmpty());
    }
}
