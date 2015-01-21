package it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users;

import java.io.Serializable;
import java.util.Objects;

 
public abstract class AbstractUser implements Serializable {
	
	
	protected int id;
	
	
	protected String nickname;
	

	public AbstractUser() {
		nickname="";
	}
	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 37 * hash + Objects.hashCode(this.nickname);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		
		final AbstractUser other = (AbstractUser) obj;
		if(!Objects.equals(this.nickname, other.nickname)){
			return false;
		}
		return true;
	}
	
	
	
	
}
