package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import exceptionhandling.ConnectionException;

public class DomRequester {
	private static final String AUTH = "Authorization";
	private static final String COOKIE = "Cookie";
	private static final String SET_COOKIE = "Set-Cookie";
	private static final String BASIC = "Basic ";
	
	private String base64Auth;
	private String cookie = null;
	private URL url = null;	
	private HttpURLConnection connection;
	private ConnectionException error = new ConnectionException("Internet connection missing!");
	
	
	
	
	public DomRequester(String name, String password) {
		String auth = name + ":" + password;
		this.base64Auth = Base64.getEncoder().encodeToString(auth.getBytes());
	}
	
	public DomRequester(String name, String password, String url) {
		this(name, password);
		this.setUrl(url);
	}
	
	
	public void setUrl(String url) {
		try {
			this.url = new URL(url);
		} catch (IOException e) {
			this.url = null;
		}
	}
	
	private void retCookie() throws ConnectionException {
		if (this.url == null) {
			throw error;
		}
		try {
			connection = (HttpURLConnection)this.url.openConnection();
			connection.setRequestProperty(AUTH, BASIC + base64Auth);
			this.cookie = connection.getHeaderField(SET_COOKIE);
		}catch (IOException e) {
			throw error;
		}
	}
	
	
	public String retriveDom() throws ConnectionException {
		this.retCookie();
		String dom = new String();
		String line;
		try {
			connection = (HttpURLConnection)this.url.openConnection();
			connection.setRequestProperty(AUTH, BASIC + base64Auth);
			connection.setRequestProperty(COOKIE, this.cookie);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			
			while ((line = bufferedReader.readLine()) != null) {
				dom += line + "\n";
			}
			return dom;				
					
		} catch (IOException e) {
			throw error;
		}
		
	}
	
	
	public static void main(String argv[]) {
	
		System.out.println();
	}
	

}
