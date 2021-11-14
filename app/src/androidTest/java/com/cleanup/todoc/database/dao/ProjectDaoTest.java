package com.cleanup.todoc.database.dao;

import static com.cleanup.todoc.database.TodocDatabase.prepopulateDatabase;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.LiveDataTestUtil;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest extends TestCase {

    private TodocDatabase database;

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
    public void GetProjectTest() throws InterruptedException {
        List<Project> projectListTest;
        projectListTest = LiveDataTestUtil.getValue(this.database.projectDao().getProjects());
        assertFalse(projectListTest.isEmpty());
    }

}