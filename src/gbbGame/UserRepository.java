package gbbGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

	private File dbFile = null;

	private Map<String, User> users;
	// private Map <String,User> users= new HashMap<>();

	public UserRepository(String filePath) {
		this.dbFile = new File(filePath);
		this.users = new HashMap<>();
		load();
	}

	public User findById(String id) {
		return users.get(id);
	}

	public boolean exists(String id) {
		return users.containsKey(id);
	}

	public void saveUser(User user) {
		users.put(user.getId(), user);
	}

	public List<User> findAll() {
		return new ArrayList<>(users.values());
	}

	public void save() throws FileNotFoundException, IOException {

		File parent = dbFile.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}

		ObjectOutputStream oos = 
				new ObjectOutputStream(
						new FileOutputStream(dbFile));
		oos.writeObject(users);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	private void load() {
	    if (!dbFile.exists()) {
	        users = new HashMap<>();
	        return;
	    }

	    try (ObjectInputStream ois = 
	    		new ObjectInputStream(new FileInputStream(dbFile))) {
	        Object obj = ois.readObject();
	        if (obj instanceof Map) {
	            users = (Map<String, User>) obj;
	        } else {
	            users = new HashMap<>();
	        }
	    } catch (Exception e) {
	        System.out.println("로드 실패(초기화 처리): " + e.getMessage());
	        users = new HashMap<>();
	    }
	}

		
		

}


