package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final ProjectRepository projectSource;
    private final TaskRepository taskSource;
    private final Executor executor;



    private LiveData<List<Task>> currentTasks;

    public TaskViewModel(ProjectRepository projectSource, TaskRepository taskSource, Executor executor) {
        this.projectSource = projectSource;
        this.taskSource = taskSource;
        this.executor = executor;
    }

    public void init(){
        if(this.currentTasks !=null){
            return;
        }
        currentTasks = taskSource.getTasks();
    }


    //---------
    // Project
    //---------

    public LiveData<List<Project>> getProjects(){ return projectSource.getProjects(); }

    //---------
    // TASK
    //---------
    public void createTask(Task task){
        executor.execute(()-> taskSource.createTask(task));
    }

    public void deleteTask(long taskId){
        executor.execute(()-> taskSource.deleteTask(taskId));
    }

    public LiveData<List<Task>> getTasks(){ return taskSource.getTasks(); }

}
