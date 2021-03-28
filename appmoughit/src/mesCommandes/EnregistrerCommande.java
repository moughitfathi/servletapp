package mesCommandes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EnregistrerCommande
 */

public class EnregistrerCommande extends HttpServlet {

	 Connection connexion=null;
	 Statement stmt=null;
	 PreparedStatement pstmt=null;
	 
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnregistrerCommande() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nom = null;
		 int nbreProduit = 0;
		 Cookie[] cookies = request.getCookies();
		 boolean connu = false;
		 nom = Identification. chercheNom (cookies);
		 OuvreBase();
		 AjouteNomBase(nom);
		 
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 out.println("<html>");
		 out.println("<body>");
		 out.println("<head>");
		 out.println("<title> votre commande </title>");
		 out.println("</head>");
		 out.println("<body bgcolor=\"white\">");
		 
		 out.println("<h3>" + "Bonjour " + nom + " voici ta nouvelle commande" + "</h3>");
		 HttpSession session = request.getSession();
		 Enumeration<String> names = session.getAttributeNames();
		 while (names.hasMoreElements()) {
		 nbreProduit++;
		 String name = (String) names.nextElement(); 
		 String value = session.getAttribute(name).toString();
		 out.println(name + " = " + value + "<br>");
		 }
		 AjouteCommandeBase(nom,session);
		 out.println("<h3>" + "et voici " + nom + " ta commande complete" + "</h3>");
		 MontreCommandeBase(nom, out);
		 out.println("<A HREF=mesCommandes.VidePanier> Vous pouvez commandez un autre disque </A><br> ");
		 out.println("</body>");
		 out.println("</html>");
		 }

		protected void OuvreBase() {
		 try {
		 Class.forName("org.gjt.mm.mysql.Driver"); 
		 connexion = DriverManager.getConnection("jdbc:mysql://localhost/magasin","root","1997");
		 connexion.setAutoCommit(true);
		 stmt = connexion.createStatement();
		 }
		 catch (Exception E) { 
		 log(" -------- probeme " + E.getClass().getName() );
		 E.printStackTrace();
		 }
		 }
		 protected void fermeBase() {
		 try {
		 stmt.close(); 
		 connexion.close(); 
		 }
		 catch (Exception E) { 
		 log(" -------- probeme " + E.getClass().getName() );
		 E.printStackTrace();
		 }
		 }
		 protected void AjouteNomBase(String nom) {
			 try {
			 ResultSet rset = null;
			 pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
			 pstmt.setString(1,nom);
			 rset=pstmt.executeQuery();
			 if (!rset.next()) 
			 stmt.executeUpdate("INSERT INTO personnel VALUES ('' ,'" + nom + "' )" );
			 }
			 catch (Exception E) {
			 log(" - probeme " + E.getClass().getName() );
			 E.printStackTrace();
			 }
			 }
			 
			protected void AjouteCommandeBase(String nom, HttpSession session ) {
				// ajoute le contenu du panier dans la base
				Enumeration names = session.getAttributeNames();
				while (names.hasMoreElements()) {
				String name = (String) names.nextElement(); 
				String value = session.getAttribute(name).toString();
				
				try{
					stmt.executeUpdate("INSERT INTO commande(article,qui) value('"+value+"','"+nom+"')");
				}
				catch(SQLException e) {
					log(" -probeme " + e.getClass().getName() );
					e.printStackTrace();
				}
				}
			}
			
			
			
			protected void MontreCommandeBase(String nom, PrintWriter out) {
				// affiche les produits présents dans la base
				try {
					ResultSet rs=stmt.executeQuery("SELECT article FROM commande ");
					out.println("tes produits sont :");
					out.println("< br>");
					while(rs.next()){
						
						out.println("<br>"+rs.getString(1));
					}
				}
				catch(Exception e){
					log(" -probeme " + e.getClass().getName() );
					e.printStackTrace();
				}
				
				}
					
					
		 
		 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
