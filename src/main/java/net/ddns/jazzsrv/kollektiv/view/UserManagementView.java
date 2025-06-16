package net.ddns.jazzsrv.kollektiv.view;

import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import net.ddns.jazzsrv.kollektiv.entity.Group;
import net.ddns.jazzsrv.kollektiv.entity.User;
import net.ddns.jazzsrv.kollektiv.service.GroupService;
import net.ddns.jazzsrv.kollektiv.service.UserService;

@Route(value = "admin/user-management", layout = MainLayout.class)
@PageTitle("Benutzerverwaltung UserManagementView")
@PermitAll
public class UserManagementView extends VerticalLayout {

    private final UserService userService;
    private final GroupService groupService;

    private final Grid<User> userGrid = new Grid<>(User.class);
    private final Grid<Group> groupGrid = new Grid<>(Group.class);

    private final TextField userNameField = new TextField("Username");
    private final TextField passwordField = new TextField("Password");
//    private final TextField roleField = new TextField("Role");
    private final TextField emailField = new TextField("Email");

    private final Button addUserButton = new Button("Add User");
    private final Button saveUserButton = new Button("Save User");
    private final Button deleteUserButton = new Button("Delete User");

    private final Binder<User> userBinder = new Binder<>(User.class);

    private final MultiSelectComboBox<Group> groupComboBox = new MultiSelectComboBox<>("Groups");

    public UserManagementView(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;

        // Configure grids
        userGrid.setColumns("userId", "userName", //"role", 
        		"email");
        userGrid.addColumn(user ->
        user.getGroups().stream().map(Group::getGroupName).collect(Collectors.joining(", ")))
        .setHeader("Gruppen");
        userGrid.setSelectionMode(SelectionMode.SINGLE);
        userGrid.addItemClickListener(e -> {
      		userBinder.setBean(userGrid.asSingleSelect().getValue());
      		updateFormVisibility();
        }
        );

        groupGrid.setColumns("groupId", "groupName");

        // Configure fields
        userBinder.forField(userNameField).withValidator(new EmailValidator("Invalid email format")).bind(User::getUserName, User::setUserName);
        userBinder.forField(passwordField).bind(User::getPasswort, User::setPasswort);
//        userBinder.forField(roleField).bind(User::getRole, User::setRole);
        userBinder.forField(emailField).withValidator(new EmailValidator("Invalid email format")).bind(User::getEmail, User::setEmail);

        // Configure ComboBox
        groupComboBox.setItems(groupService.findAll());
        groupComboBox.setItemLabelGenerator(Group::getGroupName);
        userBinder.forField(groupComboBox).bind(User::getGroups, User::setGroups);

        // Configure layout
        HorizontalLayout header = new HorizontalLayout(addUserButton, saveUserButton, deleteUserButton);
        add(header, userGrid, new FormLayout(userNameField, passwordField, //roleField, 
        		emailField, groupComboBox), groupGrid);

        // Load data
        userGrid.setItems(userService.findAll());
        groupGrid.setItems(groupService.findAll());

        // Event handlers
        addUserButton.addClickListener(e -> {
            User user = new User();
            userBinder.setBean(user);
            // Show form for editing
        });

        saveUserButton.addClickListener(e -> {
            User user = userBinder.getBean();
            if (user != null) {
                userService.save(user);
                userGrid.setItems(userService.findAll());
            }
        });

        deleteUserButton.addClickListener(e -> {
            User selectedUser = userGrid.getSelectedItems().iterator().next();
            if (selectedUser != null) {
                userService.delete(selectedUser);
                userGrid.setItems(userService.findAll());
            }
        });
        updateFormVisibility();
        
        
    }
    
    void updateFormVisibility() {
    	boolean visible = userGrid.asSingleSelect().getValue() != null;
    	userNameField.setVisible(visible);
    	passwordField.setVisible(visible);
    	//roleField.setVisible(visible);
    	emailField.setVisible(visible);
    	groupComboBox.setVisible(visible);
    }
}