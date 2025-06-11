package net.ddns.jazzsrv.kollektiv.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import net.ddns.jazzsrv.kollektiv.entity.Group;
import net.ddns.jazzsrv.kollektiv.entity.User;
import net.ddns.jazzsrv.kollektiv.service.GroupService;
import net.ddns.jazzsrv.kollektiv.service.UserService;

@Route(value = "user-management", layout = MainLayout.class)
@PageTitle("Benutzerverwaltung")
@PermitAll
public class UserGroupEditorView extends VerticalLayout {

    private final UserService userService;
    private final GroupService groupService;

    private Grid<User> userGrid = new Grid<>(User.class);
    private Grid<Group> groupGrid = new Grid<>(Group.class);

    private TextField userNameField = new TextField("Username");
    private TextField passwordField = new TextField("Password");
    private TextField roleField = new TextField("Role");
    private EmailField emailField = new EmailField("Email");

    private TextField groupNameField = new TextField("GroupName");

    private Button addUserButton = new Button("Add User");
    private Button addGroupButton = new Button("Add Group");

    private Binder<User> userBinder = new Binder<>(User.class);
    private Binder<Group> groupBinder = new Binder<>(Group.class);
    
    

    @Autowired
    public UserGroupEditorView(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @PostConstruct
    public void init() {
        configureUserGrid();
        configureGroupGrid();
        configureForm();

        HorizontalLayout actions = new HorizontalLayout(addUserButton, addGroupButton);
        add(actions, userGrid, groupGrid);

        addUserButton.addClickListener(e -> addUser());
        addGroupButton.addClickListener(e -> addGroup());
        userGrid.setItems(userService.findAll());
        groupGrid.setItems(groupService.findAll());
    }

    private void configureUserGrid() {
        userGrid.setColumns("userId", "userName", "passwort", "role", "email");
        userGrid.addItemClickListener(event -> {
            User user = event.getItem();
            if (user != null) {
                userBinder.readBean(user);
            }
        });
    }

    private void configureGroupGrid() {
        groupGrid.setColumns("groupId", "groupName");
        groupGrid.addItemClickListener(event -> {
            Group group = event.getItem();
            if (group != null) {
                groupBinder.readBean(group);
            }
        });
    }

    private void configureForm() {
        userBinder.forField(userNameField).asRequired("Username is required")
                .withValidator(name -> userService.findByUsername(name).isEmpty(), "Username already exists")
                .bind(User::getUserName, User::setUserName);
        userBinder.forField(passwordField).asRequired("Password is required").bind(User::getPasswort, User::setPasswort);
        userBinder.forField(roleField).asRequired("Role is required").bind(User::getRole, User::setRole);
        userBinder.forField(emailField).asRequired("Email is required").withValidator(new EmailValidator("Invalid email"))
                .bind(User::getEmail, User::setEmail);

        groupBinder.forField(groupNameField).asRequired("GroupName is required")
                .withValidator(name -> groupService.findByGroupName(name).isEmpty(), "GroupName already exists")
                .bind(Group::getGroupName, Group::setGroupName);
    }

    private void addUser() {
        User user = new User();
        userBinder.readBean(user);
        userService.save(user);
        userGrid.setItems(userService.findAll());
    }

    private void addGroup() {
        Group group = new Group();
        groupBinder.readBean(group);
        groupService.save(group);
        groupGrid.setItems(groupService.findAll());
    }
}