package gbbGame;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User implements Serializable {

	private static final long serialVersionUID = 5297246109597766213L;


	
	private String id;                 
    private String password;           
    private LocalDateTime lastLogin;   
    private GameRecord record;         

    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.lastLogin = null;                
        this.record = new GameRecord();       
    }


}
