package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

//		SellerDao sellerDao = DaoFactory.createSellerDao();
//
//		System.out.println("=== TEST 1: seller findById ===");
//		Seller seller = sellerDao.findById(1);
//		System.out.println(seller);
//
//		System.out.println("\n=== TEST 2: seller findByDepartment ===");
//		Department dep =  new Department(2, null);
//		List<Seller> list = sellerDao.findByDepartment(dep);
//		for(Seller obj : list) {
//			System.out.println(obj);
//		}
//
//		System.out.println("\n=== TEST 3: seller findAll ===");
//		list = sellerDao.findAll();
//		for(Seller obj : list) {
//			System.out.println(obj);
//		}
//
//		System.out.println("\n=== TEST 4: seller insert ===");
//		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
//		sellerDao.insert(newSeller);
//		System.out.println("Inserted! New Id = " + newSeller.getId());
//
//		System.out.println("\n=== TEST 5: seller update ===");
//		seller = sellerDao.findById(1);
//		seller.setName("Teste nome");
//		sellerDao.update(seller);
//		System.out.println("Update completed");
//
//		System.out.println("\n=== TEST 6: seller delete ===");
//		System.out.println("Enter id for delete");
//		int id = sc.nextInt();
//		sellerDao.deleteById(id);
//		System.out.println("Delete completed");
//		sc.close();

		DepartmentDao dpDao = DaoFactory.createDepartmentDao();

		System.out.println("=== TEST 1: department findById ===");
		Department dp = dpDao.findById(1);
		System.out.println(dp);

		System.out.println("\n=== TEST 2: department findAll ===");
		List<Department> list = dpDao.findAll();
		for(Department dp2: list) {
			System.out.println(dp2);
		}

		System.out.println("\n=== TEST 3: department insert ===");
		Department newDp = new Department(null, "Program");
		dpDao.insert(newDp);
		System.out.println("Inserted! New ID = " + newDp.getId());


		System.out.println("\n=== TEST 4: department update ===");


		System.out.println("\n=== TEST 5: department delete ===");


		sc.close();



	}

}
