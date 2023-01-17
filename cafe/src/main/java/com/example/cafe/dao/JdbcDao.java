package com.example.cafe.dao;

import com.example.cafe.data.CategoryData;
import com.example.cafe.data.DishData;
import com.example.cafe.dbutil.MySqlDBUtil;
import com.example.cafe.dto.CategoryInfoDto;
import com.example.cafe.dto.DishInfoDto;
import com.example.cafe.dto.DishQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcDao {

    @Autowired
    private MySqlDBUtil mySqlDBUtil;

    public List<DishInfoDto> getAllDishes() {
        List<DishInfoDto> dishes = new ArrayList<>();
        try(Connection conn = mySqlDBUtil.getConnection();
            Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("select d.dish_name, d.weight, d.price, c.category_name, d.description from dish d join category c on c.id_category = d.fk_category order by id_dish");
            while (rs.next()) {
                dishes.add(mapDish(rs));
            }
            return dishes;
        } catch (Exception e) {
            throw new RuntimeException("getAllDishes Error", e);
        }
    }

    private DishInfoDto mapDish(ResultSet rs) throws SQLException {
        return DishInfoDto.builder()
                .dish_name(rs.getString("dish_name"))
                .weight(rs.getInt("weight"))
                .price(rs.getDouble("price"))
                .dish_category(rs.getString("category_name"))
                .description(rs.getString("description"))
                .build();
    }

    public List<DishInfoDto> search(DishQueryDto query){
        List<DishInfoDto> dishes = new ArrayList<>();
        if(query.getFrom() == 1){
            try(Connection conn = mySqlDBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select d.dish_name, d.weight, d.price, c.category_name, d.description \n" +
                        "from cafe.dish d join cafe.category c on c.id_category = d.fk_category " +
                        "where d.price <= ? and c.category_name = ? order by id_dish limit ?")){
                stmt.setDouble(1, query.getPrice());
                stmt.setString(2, query.getCategory_name());
                stmt.setInt(3, query.getSize());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
                return dishes;
            } catch (Exception e) {
                throw new RuntimeException("getPartition Error", e);
            }
        }
        else{
            try(Connection conn = mySqlDBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select d.dish_name, d.weight, d.price, c.category_name, d.description \n" +
                        "from cafe.dish d join cafe.category c on c.id_category = d.fk_category " +
                        "where d.price <= ? and c.category_name = ? order by id_dish limit ?,?")){
                stmt.setDouble(1, query.getPrice());
                stmt.setString(2, query.getCategory_name());
                stmt.setInt(3, query.getFrom() - 1);
                stmt.setInt(4, query.getSize() - query.getFrom() + 1);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
                return dishes;
            } catch (Exception e) {
                throw new RuntimeException("getPartition Error", e);
            }
        }
    }

    public List<CategoryInfoDto> getAllCategories() {
        List<CategoryInfoDto> categories = new ArrayList<>();
        try(Connection conn = mySqlDBUtil.getConnection();
            Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("select * from category");
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
            return categories;
        } catch (Exception e) {
            throw new RuntimeException("getAllCategories Error", e);
        }
    }

    private CategoryInfoDto mapCategory(ResultSet rs) throws SQLException {
        return CategoryInfoDto.builder()
                .id(rs.getInt("id_category"))
                .category_name(rs.getString("category_name"))
                .build();
    }

    public int createDish(DishData dishData) {
        try(Connection conn = mySqlDBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into dish (dish_name, weight, price, fk_category, description) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1,dishData.getDish_name());
            stmt.setInt(2, dishData.getWeight());
            stmt.setDouble(3, dishData.getPrice());
            stmt.setInt(4, dishData.getCategoryId());
            stmt.setString(5, dishData.getDescription());
            stmt.executeUpdate();
            ResultSet gk = stmt.getGeneratedKeys();
            if (gk.next()) {
                return gk.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Create Dish Error", e);
        }
    }

    public void createDishWithoutAutoIncrement(int id, DishData dishData) {
        try(Connection conn = mySqlDBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into dish (id_dish, dish_name, weight, price, fk_category, " +
                    "description) values (?, ?, ?, ?, ?, ?)")){
            stmt.setInt(1,id);
            stmt.setString(2,dishData.getDish_name());
            stmt.setInt(3, dishData.getWeight());
            stmt.setDouble(4, dishData.getPrice());
            stmt.setInt(5, dishData.getCategoryId());
            stmt.setString(6, dishData.getDescription());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Create Dish Error", e);
        }
    }

    public void createCategory(CategoryData categoryData) {
        try(Connection conn = mySqlDBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into category " +
                    "(id_category, category_name) values (?, ?)")){
            stmt.setInt(1,categoryData.getId_category());
            stmt.setString(2,categoryData.getCategory_name());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Create Category Error", e);
        }
    }

    public DishData getDishById(int dishId) {
        try(Connection conn = mySqlDBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from dish where id_dish = ?")){
            stmt.setInt(1, dishId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapingDish(rs);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("getDishById Error", e);
        }
    }

    private DishData mapingDish(ResultSet rs) throws SQLException {
        DishData data = new DishData();
        data.setId_dish(rs.getInt("id_dish"));
        data.setDish_name(rs.getString("dish_name"));
        data.setWeight(rs.getInt("weight"));
        data.setPrice(rs.getDouble("price"));
        data.setCategoryId(rs.getInt("fk_category"));
        data.setDescription(rs.getString("description"));
        return data;
    }

    public void updateDish(DishData dishData) {
        try(Connection conn = mySqlDBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("update dish set dish_name = ?, weight = ?, price = ?, fk_category = ?, description = ? where id_dish = ?")){            stmt.setString(1,dishData.getDish_name());
            stmt.setInt(2, dishData.getWeight());
            stmt.setDouble(3, dishData.getPrice());
            stmt.setInt(4, dishData.getCategoryId());
            stmt.setString(5, dishData.getDescription());
            stmt.setInt(6, dishData.getId_dish());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("update Dish Error", e);
        }
    }

    public void deleteDish(int dishId) {
        try(Connection conn = mySqlDBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from dish where id_dish = ?")){
            stmt.setInt(1, dishId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("deleteDish Error", e);
        }
    }

    public void deleteAll() {
        try(Connection conn = mySqlDBUtil.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.execute("delete from dish");
            deleteAllCategories();
        } catch (Exception e) {
            throw new RuntimeException("deleteAll Error", e);
        }
    }

    public void deleteAllCategories() {
        try(Connection conn = mySqlDBUtil.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.execute("delete from category");
        } catch (Exception e) {
            throw new RuntimeException("deleteAll Error", e);
        }
    }
}
