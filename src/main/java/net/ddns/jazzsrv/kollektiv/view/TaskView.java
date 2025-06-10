package net.ddns.jazzsrv.kollektiv.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import net.ddns.jazzsrv.kollektiv.entity.Category;
import net.ddns.jazzsrv.kollektiv.entity.Project;
import net.ddns.jazzsrv.kollektiv.entity.Task;
import net.ddns.jazzsrv.kollektiv.service.CategoryService;
import net.ddns.jazzsrv.kollektiv.service.ProjectService;
import net.ddns.jazzsrv.kollektiv.service.TaskService;

@Route(value = "tasks", layout = MainLayout.class)
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class TaskView extends VerticalLayout {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final CategoryService categoryService;

    private final Grid<Task> grid = new Grid<>(Task.class, false);
    private final List<Task> taskList;
    private final Editor<Task> editor;

    @Autowired
    public TaskView(TaskService taskService, ProjectService projectService, CategoryService categoryService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.categoryService = categoryService;
        this.taskList = new ArrayList<>(taskService.findAll());

        setSizeFull();

        Binder<Task> binder = new Binder<>(Task.class);
        editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        grid.setItems(taskList);
        grid.setWidthFull();

        // --- Title column ---
        TextField titleField = new TextField();
        binder.forField(titleField)
              .asRequired("Title required")
              .bind(Task::getTitle, Task::setTitle);

        grid.addColumn(Task::getTitle)
            .setHeader("Title")
            .setEditorComponent(titleField)
            .setAutoWidth(true);

        // --- Description column ---
        TextField descField = new TextField();
        binder.forField(descField)
              .bind(Task::getDescription, Task::setDescription);

        grid.addColumn(Task::getDescription)
            .setHeader("Description")
            .setEditorComponent(descField)
            .setAutoWidth(true);

        // --- Project column ---
        ComboBox<Project> projectBox = new ComboBox<>();
        List<Project> projects = projectService.findAll();
        projectBox.setItems(projects);
        projectBox.setItemLabelGenerator(Project::getName);

        binder.forField(projectBox)
              .bind(Task::getProject, Task::setProject);

        grid.addColumn(task -> task.getProject() != null ? task.getProject().getName() : "")
            .setHeader("Project")
            .setEditorComponent(projectBox)
            .setAutoWidth(true);

        // --- Categories column ---
        MultiSelectComboBox<Category> categoryBox = new MultiSelectComboBox<>();
        List<Category> categories = categoryService.findAll();
        categoryBox.setItems(categories);
        categoryBox.setItemLabelGenerator(Category::getName);

        binder.forField(categoryBox)
              .bind(Task::getCategories, Task::setCategories);

        grid.addColumn(task -> task.getCategories().stream().map(Category::getName).toList())
            .setHeader("Categories")
            .setEditorComponent(categoryBox)
            .setAutoWidth(true);

        // --- Actions column ---
        grid.addComponentColumn(task -> {
            Button edit = new Button("Edit");
            Button save = new Button("Save");
            Button cancel = new Button("Cancel");
            Button delete = new Button("Delete");

            edit.addClickListener(e -> editor.editItem(task));
            save.addClickListener(e -> editor.save());
            cancel.addClickListener(e -> editor.cancel());

            delete.addClickListener(e -> {
                if (task.getId() != null) {
                    taskService.deleteById(task.getId());
                }
                taskList.remove(task);
                grid.getDataProvider().refreshAll();
            });

            HorizontalLayout actions = new HorizontalLayout();
            if (editor.isOpen() && editor.getItem().equals(task)) {
                actions.add(save, cancel);
            } else {
                actions.add(edit, delete);
            }
            return actions;
        }).setHeader("Actions").setAutoWidth(true);

        // --- Save handler ---
        editor.addSaveListener(e -> {
            Task saved = taskService.save(e.getItem());
            grid.getDataProvider().refreshItem(saved);
            Notification.show("Task saved");
        });

        // --- Add new task button ---
        Button addTaskBtn = new Button("Add New Task", click -> {
            Task newTask = new Task();
            newTask.setCategories(new HashSet<>());
            taskList.add(newTask);
            grid.getDataProvider().refreshAll();
            editor.editItem(newTask);
        });
        addTaskBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(addTaskBtn, grid);
    }
}
