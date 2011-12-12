package de.bananaco.permissions.set;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.bananaco.permissions.util.Group;
import de.bananaco.permissions.util.Permission;
import de.bananaco.permissions.util.User;

public class YamlPermissionSet extends WorldPermissionSet {

	private final YamlConfiguration config = new YamlConfiguration();
	private final File file = new File(getWorld()+".yml");
	
	private static final String PERMISSIONS = "permissions";
	private static final String GROUPS = "groups";
	
	public YamlPermissionSet(String world) {
		super(world);
	}
	
	public void load() {
		try {
			loadUnsafe();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			saveUnsafe();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveUnsafe() throws Exception {

		if(!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		
		Set<User> users = getUsers();
		
		for(User user : users) {
			String name = user.getName();
			config.set("users." +name+ "." + PERMISSIONS, new ArrayList(user.getPermissionsAsString()));
			config.set("users."+name + "." + GROUPS, new ArrayList(user.getGroupsAsString()));
		}
		
		Set<Group> groups = getGroups();
		
		for(Group group : groups) {
			String name = group.getName();
			config.set("groups."+name+ "." + PERMISSIONS, new ArrayList(group.getPermissionsAsString()));
			config.set("groups."+name + "." + GROUPS, new ArrayList(group.getGroupsAsString()));
		}
		
		config.save(file);
	}
	
	private void loadUnsafe() throws Exception {
		
		if(!file.exists()) {
			if(file.getParentFile() != null)
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		
		config.load(file);
		
		ConfigurationSection usersConfig = config.getConfigurationSection("users");
		if(usersConfig != null) {
			
		Set<String> names = usersConfig.getKeys(false);
		
		for(String name : names) {
			List<String> nPerm = usersConfig.getStringList(name + "." + PERMISSIONS);
			List<String> nGroup = usersConfig.getStringList(name+ "." + GROUPS);
			Set<Permission> perms = Permission.loadFromString(nPerm);
			// Create the new user
			add(new User(name, nGroup, perms, this));
		}
		
		}
		
		ConfigurationSection groupsConfig = config.getConfigurationSection("groups");
		if(groupsConfig != null) {
		
		Set<String> names = groupsConfig.getKeys(false);
		
		for(String name : names) {
			List<String> nPerm = groupsConfig.getStringList(name + "." + PERMISSIONS);
			List<String> nGroup = groupsConfig.getStringList(name+ "." + GROUPS);
			Set<Permission> perms = Permission.loadFromString(nPerm);
			// Create the new group
			this.add(new Group(name, nGroup, perms, this));
		}
		
		}
	}

}
