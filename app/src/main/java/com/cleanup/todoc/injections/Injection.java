package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository provideTaskDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskRepository(database.taskDao());
    }

    public static ProjectRepository provideProjectDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectRepository(database.projectDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskRepository taskSource = provideTaskDataSource(context);
        ProjectRepository projectSource = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(projectSource,taskSource, executor);
    }
}
