package net.ddns.jazzsrv.kollektiv.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Selbstreferenzierende Beziehung für Hierarchie
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Project parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Project> subProjects = new HashSet<>();

    // Beziehung zu Tasks
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    // Constructors
    public Project() {}

    public Project(String name) {
        this.name = name;
    }

    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getParent() {
		return parent;
	}

	public void setParent(Project parent) {
		this.parent = parent;
	}

	public Set<Project> getSubProjects() {
		return subProjects;
	}

	public void setSubProjects(Set<Project> subProjects) {
		this.subProjects = subProjects;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	// Convenience method
    public void addSubProject(Project subProject) {
        subProjects.add(subProject);
        subProject.setParent(this);
    }

    public void removeSubProject(Project subProject) {
        subProjects.remove(subProject);
        subProject.setParent(null);
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setProject(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setProject(null);
    }
}