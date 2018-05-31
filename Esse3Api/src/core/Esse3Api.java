package core;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import exceptionhandling.ConnectionException;
import exceptionhandling.LoginException;

public class Esse3Api {
	private static final String URLBASE = 
			"https://www.studenti.ict.uniba.it/esse3/";
	private static final String URLLOGIN =
			"https://www.studenti.ict.uniba.it/esse3/auth/Logon.do";
	private static final String URLLOGOUT = 
			"https://www.studenti.ict.uniba.it/esse3/Logout.do";
	private static final String URLLIBRETTO = 
			"https://www.studenti.ict.uniba.it/esse3/auth/studente/Libretto/LibrettoHome.do";
	
	private DomRequester requester;
	private String domLibretto;
	private HashMap<String, String> subSubjs;
	
	
	public Esse3Api(String name, String password) {
		this.requester = new DomRequester(name, password);
		domLibretto = new String();
		subSubjs = new HashMap<String, String>();
		
	}
	
	public void Login() throws ConnectionException, LoginException{
		this.requester.setUrl(URLLOGIN);
		this.requester.retriveDom();
	}
	
	public void Logout() throws ConnectionException {
		this.requester.setUrl(URLLOGOUT);
		this.requester.doSingleReq();	
	}
	
	
	private void retriveDomLibretto() throws ConnectionException, LoginException{
		this.requester.setUrl(URLLIBRETTO);
		this.domLibretto = this.requester.retriveDom();
	}
	
	public List<Float> getAvrg() throws ConnectionException, LoginException {
		List<Float> avrgVotes = new ArrayList<Float>();
		
		if (domLibretto.isEmpty()) {
			this.retriveDomLibretto();
		}
		
		Document doc = Jsoup.parse(domLibretto);
		Elements elements = doc.select("td.tplMaster");
		for (Element e : elements) {
			if (!e.text().isEmpty()) {
				String num = e.text()
						.split("/")[0]
								.trim();
				avrgVotes.add(Float.valueOf(num));
			}
		}
		return avrgVotes;
	}
	
	public List<String> getSubjects() throws ConnectionException, LoginException {
		List<String> subjects = new ArrayList<String>();
		
		if (domLibretto.isEmpty()) {
			this.retriveDomLibretto();
		}
		
		Document doc = Jsoup.parse(domLibretto);
		Elements elements = doc.select("td.detail_table_middle");
		for (Element e : elements) {
			if (!e.text().isEmpty()) {
				this.subSubjs.put(e.text(), e.select("a").attr("href").trim());
				subjects.add(e.text());
			}
		}
		return subjects;
	}
	
	
	public HashMap<String, Float> getDetailSubj(String subj) throws ConnectionException, LoginException {
		HashMap<String, Float> blockSubjHours = new HashMap<String, Float>();
		int div;
		int counter = 0;
		String key = new String();
		Float value = new Float(0);
		Document doc;
		Elements elements;
		
		if (subSubjs.isEmpty()) {
			this.getSubjects();
		}
		this.requester.setUrl(URLBASE + this.subSubjs.get(subj));
		String html = this.requester.retriveDom();
		doc = Jsoup.parse(html);
		elements = doc.select("th.detail_table");
		div = elements.size();
		counter = 0;
		elements = doc.select("td.detail_table");
		
		for (Element e : elements) {
			counter = counter % div;
			if (counter == 0) {
				key = e.text();
			} else if (counter == div - 1) {
				value = Float.valueOf(e.
						text()
						.trim());
				blockSubjHours.put(key, value);
			}
			counter++;
		}
		return blockSubjHours;
	}
	
	public Float getTotalBlockHours(String subj) throws ConnectionException, LoginException{
		HashMap<String, Float> res = this.getDetailSubj(subj);
		Float totalHours = new Float(0);
		for (Float hrs : res.values()) {
			totalHours += hrs;
		}
		return totalHours;
	}
	
	
	public static void main(String argv[]) throws ConnectionException, LoginException {
		/*
		Scanner sc = new Scanner(System.in);
		System.out.println("name: ");
		String name = sc.nextLine();
		System.out.println("password: ");
		String psw = sc.nextLine();
		Esse3Api prova = new Esse3Api(name, psw);*/
		Esse3Api prova = new Esse3Api("i.napoli2", "H*97!79*2a");
		prova.Login();
		//System.out.println(prova.getAvrg());
		List<String> tmp = prova.getSubjects();
		System.out.println(tmp);
		System.out.println(prova.getDetailSubj(tmp.get(2)));
		System.out.println(prova.getTotalBlockHours(tmp.get(2)));
		
	}

}
