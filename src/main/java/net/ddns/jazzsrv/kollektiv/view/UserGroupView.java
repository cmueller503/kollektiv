package net.ddns.jazzsrv.kollektiv.view;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import net.ddns.jazzsrv.kollektiv.entity.Group;
import net.ddns.jazzsrv.kollektiv.entity.Role;
import net.ddns.jazzsrv.kollektiv.entity.User;
import net.ddns.jazzsrv.kollektiv.service.GroupService;
import net.ddns.jazzsrv.kollektiv.service.UserService;

@Route(value = "user-management3", layout = MainLayout.class)
@PageTitle("Benutzerverwaltung UserGroupView")
@PermitAll
public class UserGroupView extends VerticalLayout {

    private final UserService userService;
    private final GroupService groupService;

    private final Grid<User> userGrid = new Grid<>(User.class, false);
    private final Grid<Group> groupGrid = new Grid<>(Group.class, false);

    public UserGroupView(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        userGrid.setSizeFull();

        add(new H3("Benutzerverwaltung"), getUserToolbar(), userGrid);
        configureUserGrid();

        add(new H3("Gruppenverwaltung"), getGroupToolbar(), groupGrid);
        configureGroupGrid();

        updateGrids();
    }

    // ────────────────────────────────────────────────────────────────
    // Benutzer-Grid
    private void configureUserGrid() {
        userGrid.setHeight("350px");
        userGrid.setColumns(); // keine Standard-Spalten
        
        Binder<User> userBinder = new Binder<>(User.class);

        Grid.Column<User> nameCol = userGrid.addColumn(User::getUserName).setHeader("Benutzername");
        Grid.Column<User> emailCol = userGrid.addColumn(User::getEmail).setHeader("E-Mail");
        //Grid.Column<User> roleCol = userGrid.addColumn(User::getRole).setHeader("Rolle");
        Grid.Column<User> rolesCol = userGrid.addColumn(User::getRoles).setHeader("Rollen");
        Grid.Column<User> groupCol = userGrid.addColumn(user ->
                user.getGroups().stream().map(Group::getGroupName).collect(Collectors.joining(", ")))
                .setHeader("Gruppen");

        Grid.Column<User> actionCol = userGrid.addComponentColumn(this::createUserActionButtons).setHeader("Aktionen");

        
        Editor<User> editor = userGrid.getEditor();
        editor.setBinder(userBinder);
        editor.setBuffered(true);

        TextField nameField = new TextField();
        userBinder.bind(nameField, User::getUserName, User::setUserName);
        nameCol.setEditorComponent(nameField);

        TextField emailField = new TextField();
        userBinder.forField(emailField).withValidator(new EmailValidator("Ungültige E-Mail"))
              .bind(User::getEmail, User::setEmail);
        emailCol.setEditorComponent(emailField);

//        TextField roleField = new TextField();
//        userBinder.bind(roleField, User::getRole, User::setRole);
//        roleCol.setEditorComponent(roleField);

        MultiSelectComboBox<Group> groupSelector = new MultiSelectComboBox<>("Gruppen");
        groupSelector.setItems(groupService.findAll());
        groupSelector.setItemLabelGenerator(Group::getGroupName);
        userBinder.forField(groupSelector).bind(User::getGroups, User::setGroups);
        groupCol.setEditorComponent(groupSelector);
        //groupSelector.addValueChangeListener(e -> Logger.getLogger("ValueChangeListener").warning("CM_DEBUG!!!! Gruppen: " + e.getValue().size()));

        MultiSelectComboBox<Role> rolesSelector = new MultiSelectComboBox<>("Rollen");
        rolesSelector.setItems(Role.valuesAsSet());
        rolesSelector.setItemLabelGenerator(Role::getName);
        userBinder.forField(rolesSelector).bind(User::getRoles, User::setRoles);
        rolesCol.setEditorComponent(rolesSelector);

        
        editor.addCloseListener(e -> updateGrids());
    }

    private HorizontalLayout createUserActionButtons(User user) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT), e -> userGrid.getEditor().editItem(user));
        Button save = new Button(new Icon(VaadinIcon.CHECK), e -> {
            userGrid.getEditor().save();
            
            Logger.getLogger("MyLogger").warning("CM_DEBUG saveButton clicked, user=" + user.getUserName() + "  Gruppen: " + user.getGroups().size());
            userService.save(user);
            updateGrids();
        });
        Button cancel = new Button(new Icon(VaadinIcon.CLOSE), e -> userGrid.getEditor().cancel());
        Button delete = new Button(new Icon(VaadinIcon.TRASH), e -> {
            userService.delete(user);
            updateGrids();
        });
        return new HorizontalLayout(edit, save, cancel, delete);
    }

    private Component getUserToolbar() {
        Button add = new Button("Neuer Benutzer", e -> {
            User user = new User();
            userService.save(user);
            updateGrids();
            userGrid.getEditor().editItem(user);
        });
        return new HorizontalLayout(add);
    }

    // ────────────────────────────────────────────────────────────────
    // Gruppen-Grid
    private void configureGroupGrid() {
        groupGrid.setHeight("300px");
        groupGrid.setColumns();

        Grid.Column<Group> nameCol = groupGrid.addColumn(Group::getGroupName).setHeader("Gruppenname");
        Grid.Column<Group> userCol = groupGrid.addColumn(group ->
                group.getUsers().stream().map(User::getUserName).collect(Collectors.joining(", ")))
                .setHeader("Benutzer");

        Grid.Column<Group> actionCol = groupGrid.addComponentColumn(this::createGroupActionButtons).setHeader("Aktionen");

        Binder<Group> binder = new Binder<>(Group.class);
        Editor<Group> editor = groupGrid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField groupField = new TextField();
        binder.bind(groupField, Group::getGroupName, Group::setGroupName);
        nameCol.setEditorComponent(groupField);

//        groupGrid.addItemClickListener(e -> {
//            editor.editItem(e.getItem());
//            groupField.focus();
//        });

        editor.addCloseListener(e -> updateGrids());
    }

    private HorizontalLayout createGroupActionButtons(Group group) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT), e -> groupGrid.getEditor().editItem(group));
        Button save = new Button(new Icon(VaadinIcon.CHECK), e -> {
            groupGrid.getEditor().save();
            groupService.save(group);
            updateGrids();
        });
        Button cancel = new Button(new Icon(VaadinIcon.CLOSE), e -> groupGrid.getEditor().cancel());
        Button delete = new Button(new Icon(VaadinIcon.TRASH), e -> {
            groupService.delete(group);
            updateGrids();
        });
        return new HorizontalLayout(edit, save, cancel, delete);
    }

    private Component getGroupToolbar() {
        Button add = new Button("Neue Gruppe", e -> {
            Group group = new Group();
            groupService.save(group);
            updateGrids();
            groupGrid.getEditor().editItem(group);
        });
        return new HorizontalLayout(add);
    }

    // ────────────────────────────────────────────────────────────────
    // Grid-Daten aktualisieren
    private void updateGrids() {
        userGrid.setItems(userService.findAll());
        groupGrid.setItems(groupService.findAll());
    }
}