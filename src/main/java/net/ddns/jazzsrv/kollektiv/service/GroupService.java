package net.ddns.jazzsrv.kollektiv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.ddns.jazzsrv.kollektiv.entity.Group;
import net.ddns.jazzsrv.kollektiv.repository.GroupRepository;

@Service
public class GroupService {
	private final GroupRepository groupRepository;

	public GroupService(GroupRepository groupRepository) {
		super();
		this.groupRepository = groupRepository;
	}

	public Optional<Group> findByGroupName(String name) {
		return groupRepository.findByGroupName(name);
	}

	public void save(Group group) {
		groupRepository.save(group);		
	}
	
	
	public void delete(Group group) {
		groupRepository.delete(group);		
	}
	
	public List<Group> findAll() {
		return groupRepository.findAll();
	}
	
	
}
