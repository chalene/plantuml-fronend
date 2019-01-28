package com.example.demo.pojo;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="uml")
public class FilePath {
 
	public Integer getId() {
		return id;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
 
	public String getPath() {
		return Path;
	}
 
	public void setPath(String path) {
		Path = path;
	}
	
	public String getUml() {
		return uml;
	}

	public void setUml(String uml) {
		this.uml = uml;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	public String Path;
	
	public String uml;

	
}
