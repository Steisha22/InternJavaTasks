package com.example.cafe.service;

import com.example.cafe.dao.JdbcDao;
import com.example.cafe.data.CategoryData;
import com.example.cafe.data.DishData;
import com.example.cafe.dto.*;
import com.example.cafe.exeption.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DishServiceImpl implements DishService{
    @Autowired
    private JdbcDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<DishInfoDto> getAllDishes() {
        return dao.getAllDishes();
    }

    @Override
    public List<DishInfoDto> search(DishQueryDto query) {
        return dao.search(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryInfoDto> getAllCategories() {
        return dao.getAllCategories();
    }

    @Override
    public int createDish(DishSaveDto dto) {
        validateDish(dto);
        DishData data = new DishData();
        mapDishDtoToData(data, dto);
        return dao.createDish(data);
    }

    private static void validateDish(DishSaveDto dto) {
        if (dto.getDish_name().matches("\\s*")) {
            throw new IllegalArgumentException("Dish name cannot be empty");
        }
        if (dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price cannot be less than or equal to zero");
        }
        if (dto.getWeight() <= 0) {
            throw new IllegalArgumentException("Weight cannot be less than or equal to zero");
        }
    }

    private void mapDishDtoToData(DishData data, DishSaveDto dto) {
        data.setDish_name(dto.getDish_name());
        data.setWeight(dto.getWeight());
        data.setPrice(dto.getPrice());
        data.setDescription(dto.getDescription());
        data.setCategoryId(dto.getCategoryId());
    }

    @Override
    public void updateDish(int id, DishSaveDto dto) {
        DishData dishData = dao.getDishById(id);
        if(dishData != null) {
            mapDishDtoToData(dishData, dto);
            dao.updateDish(dishData);
        }
        else{
            throw new NotFoundException("Dish with id %d not found".formatted(id));
        }
    }

    @Override
    public void deleteDish(int dishId) {
        dao.deleteDish(dishId);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public void fillDataBase(){
        dao.deleteAll();
        CategoryData categoryData = new CategoryData();
        for(int i = 1; i < 6; i++){
            categoryData.setId_category(i);
            categoryData.setCategory_name("Category №" + i);
            dao.createCategory(categoryData);
        }

        DishData dishData = new DishData();
        for(int i = 1; i < 101; i++){
            dishData.setDish_name("Dish №" + i);
            dishData.setWeight((int)(( Math.random() * (1001 - 10) + 10)));
            dishData.setPrice(Math.random()* (10001-10) + 10);
            dishData.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
            dishData.setCategoryId((int)(( Math.random() * (6 - 1) + 1)));
            dao.createDishWithoutAutoIncrement(i, dishData);
        }
    }
}
