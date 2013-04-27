package dcd.academic.model;

public class User {
	
	private String username;
	private String name;
	private String password;
	private String email;
	private String weibo_url;
	private String github_url;
	private String interests;
	private String homepage;
	
	public String getWeibo_url() {
		return weibo_url;
	}
	public void setWeibo_url(String weibo_url) {
		this.weibo_url = weibo_url;
	}
	public String getGithub_url() {
		return github_url;
	}
	public void setGithub_url(String github_url) {
		this.github_url = github_url;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
