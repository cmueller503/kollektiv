package net.ddns.jazzsrv.kollektiv.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import net.ddns.jazzsrv.kollektiv.entity.Project;
import net.ddns.jazzsrv.kollektiv.service.ProjectService;

@Route(value = "project-tree", layout = MainLayout.class)
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
public class ProjectTreeView extends VerticalLayout {

    private final ProjectService projectService;
    private final TreeGrid<Project> treeGrid = new TreeGrid<>();
    private final Map<Project, List<Project>> hierarchy = new HashMap<>();
    private final Editor<Project> editor;

    private Project selectedParent = null;

    @Autowired
    public ProjectTreeView(ProjectService projectService) {
        this.projectService = projectService;
        setSizeFull();

        List<Project> allProjects = projectService.findAll();

        // Build parent-child map
        for (Project project : allProjects) {
            Project parent = project.getParent();
            hierarchy.computeIfAbsent(parent, k -> new ArrayList<>()).add(project);
        }

        treeGrid.setItems(hierarchy.get(null), hierarchy::get);
        treeGrid.setWidthFull();

        // Setup binder/editor
        Binder<Project> binder = new Binder<>(Project.class);
        editor = treeGrid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        // Name column
        TextField nameField = new TextField();
        binder.forField(nameField)
              .asRequired("Name is required")
              .bind(Project::getName, Project::setName);

        treeGrid.addColumn(Project::getName)
                .setHeader("Project Name")
                .setEditorComponent(nameField)
                .setAutoWidth(true);

        // Actions column
        treeGrid.addComponentColumn(project -> {
            Button edit = new Button("Edit");
            Button save = new Button("Save");
            Button cancel = new Button("Cancel");
            Button delete = new Button("Delete");

            edit.addClickListener(e -> {
                selectedParent = project;
                editor.editItem(project);
            });

            save.addClickListener(e -> {
                editor.save();
                reloadTree();
            });

            cancel.addClickListener(e -> editor.cancel());

            delete.addClickListener(e -> {
                deleteRecursive(project);
                reloadTree();
            });

            HorizontalLayout actions = new HorizontalLayout();
            if (editor.isOpen() && editor.getItem().equals(project)) {
                actions.add(save, cancel);
            } else {
                actions.add(edit, delete);
            }
            return actions;
        }).setHeader("Actions").setAutoWidth(true);

        // Save handler
        editor.addSaveListener(e -> {
            Project saved = projectService.save(e.getItem());
            Notification.show("Project saved: " + saved.getName());
        });

        // Add button (adds as child of selectedParent if one is selected)
        Button addRoot = new Button("Add Project", e -> {
            Project newProject = new Project();
            if (selectedParent != null) {
                newProject.setParent(selectedParent);
            }
            projectService.save(newProject);
            reloadTree();
            editor.editItem(newProject);
        });
        addRoot.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(addRoot, treeGrid);
    }

    private void reloadTree() {
        List<Project> allProjects = projectService.findAll();
        hierarchy.clear();
        for (Project project : allProjects) {
            Project parent = project.getParent();
            hierarchy.computeIfAbsent(parent, k -> new ArrayList<>()).add(project);
        }
        treeGrid.setItems(hierarchy.get(null), hierarchy::get);
    }

    private void deleteRecursive(Project project) {
        List<Project> children = hierarchy.getOrDefault(project, List.of());
        for (Project child : children) {
            deleteRecursive(child);
        }
        projectService.deleteById(project.getId());
    }
}

