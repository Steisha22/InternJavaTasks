package com.example.cafe.service;

import com.example.cafe.dto.*;

import java.util.List;

public interface DishService {
    List<DishInfoDto> getAllDishes();

    List<DishInfoDto> search(DishQueryDto query);

    List<CategoryInfoDto> getAllCategories();

    int createDish(DishSaveDto dto);

    void updateDish(int id, DishSaveDto dto);

    void deleteDish(int dishId);

    void deleteAll();

    void fillDataBase();
}
