package com.cleanup.todoc.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectRepository projectSource;
    private final TaskRepository taskSource;
    private final Executor executor;

    public ViewModelFactory(ProjectRepository projectSource, TaskRepository taskSource, Executor executor) {
        this.projectSource = projectSource;
        this.taskSource = taskSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(projectSource, taskSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
