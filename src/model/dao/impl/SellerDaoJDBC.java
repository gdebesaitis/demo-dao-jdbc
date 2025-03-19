package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {

		String sql = "insert into seller " +
				 	 "(name, email, birthdate, basesalary, departmentid) " +
				 	 "values " +
				 	 "(?, ?, ?, ?, ?) ";

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if( rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller obj) {


		String sql = "update seller " +
				 	 "set name = ?, email = ?, birthdate = ?, basesalary = ?, departmentid = ? " +
				 	 "where id = ?";

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate();

		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {

		String sql = " delete from seller where id = ?";

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		String sql = "select seller.*, department.name as depname " +
					 "from seller inner join department " +
					 "on seller.departmentid = department.id " +
					 "where seller.id = ?";

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				// seta o department primeiro pois o seller depende dele
				Department dep = instanciateDepartment(rs);

				Seller obj = instanciateSeller(rs, dep);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Seller instanciateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("id"));
		obj.setName(rs.getString("name"));
		obj.setEmail(rs.getString("email"));
		obj.setBaseSalary(rs.getDouble("basesalary"));
		obj.setBirthDate(rs.getDate("birthdate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("departmentid"));
		dep.setName(rs.getString("depname"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		String sql = "select seller.*, department.name as depname " +
					 "from seller inner join department " +
					 "on seller.departmentid = department.id ";

		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {

				Department dep = map.get(rs.getInt("departmentId"));

				if( dep == null ) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("departmentId"), dep);
				}
;
				Seller obj = instanciateSeller(rs, dep);
				list.add(obj);

			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;

		String sql = "select seller.*, department.name as depname " +
					 "from seller inner join department " +
					 "on seller.departmentid = department.id " +
					 "where departmentid = ?";

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, department.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {

				Department dep = map.get(rs.getInt("departmentId"));

				if( dep == null ) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("departmentId"), dep);
				}
;
				Seller obj = instanciateSeller(rs, dep);
				list.add(obj);

			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
