package ejercicio4b;

import java.time.LocalDate;

import entidades.Address;
import entidades.Student;
import entidades.Tuition;
import entidades.University;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ejercicio4b {
	
	public static void main(String[] args) {
		/* Crea un nuevo objeto Student y otro objeto University y guárdalo en la BD. 
		 * La asociación entre ambas entidades será: @OneToMany bidireccional. 
		 * Definimos una relación bidireccional porque las operaciones en ambas 
		 * direcciones pueden ser frecuentes y útiles. Los administradores pueden 
		 * querer ver todos los estudiantes asociados con una universidad, mientras que también 
		 * puede ser necesario acceder a la información de la universidad desde el 
		 * registro del estudiante. 
		 * 
		 */
		// creamos el Entity Manager Factory y el entity manager
		EntityManagerFactory factoria = Persistence.createEntityManagerFactory("demodb");
		
		EntityManager manager = factoria.createEntityManager();
		
		// comenzamos la transaccion
		manager.getTransaction().begin();
		try {		
			// creamos un objeto de la clase Student
			Student estudiante = crearEstudiante();
			
			// creamos la matricula, que contiene al estudiante, como se indica en el modelo
			Tuition matricula = crearMatricula();
			
			University universidad = crearUniversidad();
			
			//relacion bidireccional OneToMany
			universidad.getStudents().add(estudiante);
	
			// guardamos la universidad
			manager.persist(universidad);
			
			//relacion bidireccional OneToMany
			estudiante.setUniversity(universidad);
			
			// añadimos el dato de la matricula al estudiante
			matricula.setStudent(estudiante);
			
			manager.persist(matricula);
			
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
		
		private static University crearUniversidad() {
			University uni = new University();
			
			uni.setName("EHU");
			
			Address direccion = new Address();
			
			direccion.setAddressLine1("Araba Kalea");
			direccion.setAddressLine2("5");
			direccion.setCity("Gasteiz");
			direccion.setZipCode("01155");
			
			uni.setAddress(direccion);
			
			return uni;	
	}

		private static Tuition crearMatricula() {
			Tuition matri = new Tuition();
			
			matri.setFee(1000.5);
			
			return  matri;
		}

		private static Student crearEstudiante() {
			Student estu = new Student();
			
			estu.setFirstName("Lorena");
			estu.setLastName("Fuente");
			estu.setEmail("lfuente@birt.eus");
			estu.getPhones().add("666666666");
			estu.setBirthdate(LocalDate.parse("1990-01-01"));
			
			Address direccion = new Address();
			direccion.setAddressLine1("C/Alava");
			direccion.setAddressLine2("1, 4A");
			direccion.setCity("Vitoria");
			direccion.setZipCode("1000");
			
			estu.setAddress(direccion);
			
			return estu;
		}
}
