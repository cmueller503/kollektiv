package net.ddns.jazzsrv.kollektiv.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import net.ddns.jazzsrv.kollektiv.entity.Project;
import net.ddns.jazzsrv.kollektiv.service.ProjectService;

@Route(value = "projects", layout = MainLayout.class)
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class ProjectView extends VerticalLayout {

    private final ProjectService projectService;
    private final Grid<Project> grid = new Grid<>(Project.class, false);
    private final List<Project> projectList;
    private final Editor<Project> editor;

    @Autowired
    public ProjectView(ProjectService projectService) {
        this.projectService = projectService;
        this.projectList = new ArrayList<>(projectService.findAll());

        setSizeFull();
        setPadding(true);

        Binder<Project> binder = new Binder<>(Project.class);
        editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        grid.setItems(projectList);
        grid.setWidthFull();

        // --- Name column ---
        TextField nameField = new TextField();
        binder.forField(nameField)
              .asRequired("Name is required")
              .bind(Project::getName, Project::setName);

        grid.addColumn(Project::getName)
            .setHeader("Project Name")
            .setEditorComponent(nameField)
            .setAutoWidth(true);

        // --- Parent Project column ---
        ComboBox<Project> parentBox = new ComboBox<>();
        parentBox.setItemLabelGenerator(Project::getName);
        parentBox.setAllowCustomValue(false);

        binder.forField(parentBox)
              .bind(Project::getParent, Project::setParent);

        grid.addColumn(project -> {
            Project parent = project.getParent();
            return parent != null ? parent.getName() : "";
        }).setHeader("Parent Project")
          .setEditorComponent(parentBox)
          .setAutoWidth(true);

        // --- Action column ---
        grid.addComponentColumn(project -> {
            Button edit = new Button("Edit");
            Button save = new Button("Save");
            Button cancel = new Button("Cancel");
            Button delete = new Button("Delete");

            edit.addClickListener(e -> {
                parentBox.setItems(projectList.stream()
                        .filter(p -> !Objects.equals(p.getId(), project.getId()))
                        .toList());
                editor.editItem(project);
            });

            save.addClickListener(e -> editor.save());
            cancel.addClickListener(e -> editor.cancel());

            delete.addClickListener(e -> {
                if (project.getId() != null) {
                    projectService.deleteById(project.getId());
                }
                projectList.remove(project);
                grid.getDataProvider().refreshAll();
            });

            HorizontalLayout actions = new HorizontalLayout();
            if (editor.isOpen() && editor.getItem().equals(project)) {
                actions.add(save, cancel);
            } else {
                actions.add(edit, delete);
            }
            return actions;
        }).setHeader("Actions").setAutoWidth(true);

        editor.addSaveListener(e -> {
            Project saved = projectService.save(e.getItem());
            grid.getDataProvider().refreshItem(saved);
            Notification.show("Project saved");
        });

        // --- Add new project ---
        Button addProject = new Button("Add New Project", e -> {
            Project newProject = new Project();
            projectList.add(newProject);
            grid.getDataProvider().refreshAll();
            editor.editItem(newProject);
        });
        addProject.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(addProject, grid);
    }
}