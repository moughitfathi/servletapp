package mesCommandes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InscriptionClien
 */
public class InscriptionClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscriptionClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nomRecu=null,motPasseRecu=null;
		String nomCookie =null , motPasseCookie = null;
		// Initialisation cookies et paramètres
		nomRecu=request.getParameter("nom");
		motPasseRecu=request.getParameter("motdepasse");
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				if(c.getName().equals("nomCookie")) {
					nomCookie=c.getValue();
				}
				if(c.getName().equals("motPasseCoookie")) {
					motPasseCookie=c.getValue();
				}
			}
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (nomCookie==null && nomRecu==null){
			// Cas 1 : cas où il n'y a ni de cookies ni de parametres
			out.println("<html>");
			out.println("<body>");
			out.println("<head>");
			out.println("<title> inscription d'un client </title>");
			out.println("</head>");
			out.println("<body bgcolor='white' >");
			out.println( nomRecu +" | "+ motPasseRecu +" | "+ nomCookie +" | "+ motPasseCookie );
			out.println("<h3>" + "Bonjour, vous devez vous inscrire " + "</h3>");
			out.println("<h3>" + "Attention mettre nom et le mot de passe avec plus de 3 caracteres" + "</h3>");
			out.print(" <form action='sinscrire' method='GET' > ");
			out.println("nom");
			out.println("<input type='text' size='20' name='nom' >");
			out.println("<br>");
			out.println("mot de passe");
			out.println("<input type='password' size='20' name='motdepasse'> <br>");
			out.println("<input type='submit' value='inscription'>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			} else if (nomCookie==null && nomRecu!=null){
			// Cas 2 : cas où il n'y a pas de cookies mais les paramètres nom et mot de passes sont présents :
			
		        //creation cookies
		        Cookie c1 =new Cookie("nomCookie",nomRecu);
		        Cookie c2 =new Cookie("motPasseCookie",motPasseRecu);
		        response.addCookie(c1);
		        response.addCookie(c2);
				getServletContext().getRequestDispatcher("/servlet/achat").forward(request, response);

			}
			else if (identique(nomRecu,nomCookie) && identique(motPasseRecu,motPasseCookie))
			{
			// Cas 4 : cas où le nom et le mot passe sont correctes, appel à la servlet achat
				getServletContext().getRequestDispatcher("/servlet/achat").forward(request, response);

			}
			else {
			// Cas 3 : les cookies sont présents demande de s'identifier
				out.println("<html>");
				out.println("<body>");
				out.println("<head>");
				out.println("<title> IDENTIFICATION </title>");
				out.println("</head>");
				out.println("<body bgcolor='white' >");
				out.println( nomRecu +" | "+ motPasseRecu +" | "+ nomCookie +" | "+ motPasseCookie );
				out.print(" <form action='sinscrire' method='GET' > ");
				out.println("nom");
				out.println("<input type='text' size='20' name='nom' >");
				out.println("<br>");
				out.println("mot de passe");
				out.println("<input type='password' size='20' name='motdepasse'> <br>");
				out.println("<input type='submit' value='s'identifier'>");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
				nomRecu = request.getParameter("nom");
				
				motPasseRecu = request.getParameter("motdepasse");
				getServletContext().getRequestDispatcher("/servlet/achat").forward(request, response);

			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	boolean identique (String recu, String cookie) {
		return ((recu != null) && (recu.length() >3) && (cookie != null) && (recu.equals(cookie) ));
		}

}
