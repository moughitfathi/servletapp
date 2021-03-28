package mesCommandes;

import javax.servlet.http.Cookie;

public class Identification {
	
	

	public static String chercheNom(Cookie[] cookies) {
		// cherche dans les cookies la valeur de celui qui se nomme "nom"
		// retourne la valeur de ce nom au lieu de inconnu
		
		 String c=null ;
		 if(cookies!=null) {
	         for(Cookie cookie :cookies){
			     if (cookie.getName().equals("nom")) {
				      c = cookie.getValue();
		
	             }
	         }
	     }
	         return c;
	}
}
		 
		
	         
