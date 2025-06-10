package net.ddns.jazzsrv.kollektiv.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import net.ddns.jazzsrv.kollektiv.entity.Category;
import net.ddns.jazzsrv.kollektiv.service.CategoryService;

@Route(value = "admin/categories", layout = MainLayout.class)
@RolesAllowed("ROLE_ADMIN")
public class CategoryView extends VerticalLayout {

    private final CategoryService categoryService;
    private final Grid<Category> grid = new Grid<>();
    private final List<Category> categoryList;
    private final Editor<Category> editor;

    @Autowired
    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.categoryList = new ArrayList<>(categoryService.findAll());

        setSizeFull();
        grid.setItems(categoryList);
        grid.setWidthFull();
        add(grid);

        // Binder for inline editing
        Binder<Category> binder = new Binder<>(Category.class);
        editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        // TextField column
        Grid.Column<Category> nameColumn = grid.addColumn(Category::getName)
            .setHeader("Name")
            .setEditorComponent(new TextField())
            .setAutoWidth(true)
            .setFlexGrow(1);

        TextField nameField = new TextField();
        binder.bind(nameField, Category::getName, Category::setName);
        nameColumn.setEditorComponent(nameField);

        // Action column
        Grid.Column<Category> actionsColumn = grid.addComponentColumn(category -> {
            Button edit = new Button("Edit");
            Button save = new Button("Save");
            Button cancel = new Button("Cancel");
            Button delete = new Button("Delete");

            edit.addClickListener(e -> editor.editItem(category));
            save.addClickListener(e -> {
                editor.save();
            });
            cancel.addClickListener(e -> editor.cancel());

            delete.addClickListener(e -> {
                if (category.getId() != null) {
                    categoryService.deleteById(category.getId());
                }
                categoryList.remove(category);
                grid.getDataProvider().refreshAll();
            });

            HorizontalLayout actions = new HorizontalLayout();
            if (editor.isOpen() && editor.getItem().equals(category)) {
                actions.add(save, cancel);
            } else {
                actions.add(edit, delete);
            }
            return actions;
        }).setHeader("Actions").setAutoWidth(true);

        editor.addSaveListener(e -> {
            Category updated = e.getItem();
            categoryService.save(updated);
            grid.getDataProvider().refreshItem(updated);
        });

        // New category button
        Button addCategoryBtn = new Button("Add New Category");
        addCategoryBtn.addClickListener(e -> {
            Category newCategory = new Category("");
            categoryList.add(newCategory);
            grid.getDataProvider().refreshAll();
            editor.editItem(newCategory);
        });
        add(addCategoryBtn);
    }
}