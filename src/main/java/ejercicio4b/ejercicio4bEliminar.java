package ejercicio4b;


import entidades.University;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ejercicio4bEliminar {
	
	public static void main(String[] args) {
		// creamos el Entity Manager Factory y el entity manager
		EntityManagerFactory factoria = Persistence.createEntityManagerFactory("demodb");
		
		EntityManager manager = factoria.createEntityManager();
		
		// comenzamos la transaccion
		manager.getTransaction().begin();
		
		try {		
			int idUniversidad = 4; // he insertado el registro n 4
			// obtenemos la universidad
			University universidad = manager.find(University.class, idUniversidad);
			
			// eliminamos la universidad, pero no se elimina el estudinte ni la matricula
			manager.remove(universidad);
			
			manager.getTransaction().commit();
			
		}catch(Exception e) {
			// si hay excepcion realizamos un rollback
			System.out.println("Rollback");
			manager.getTransaction().rollback();
			
			e.printStackTrace();
		}finally {
			manager.close();
			factoria.close();
		}

	}
}
