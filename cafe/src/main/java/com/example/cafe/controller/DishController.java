package com.example.cafe.controller;

import com.example.cafe.dto.*;
import com.example.cafe.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cafe")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @GetMapping("/mainPage")
    public List<DishInfoDto> getDishes() {
        dishService.fillDataBase();
        return dishService.getAllDishes();
    }

    @PostMapping("_search")
    public List<DishInfoDto> search(@RequestBody DishQueryDto query) {
        return dishService.search(query);
    }


    @GetMapping("/categories")
    public List<CategoryInfoDto> getCategories() {
        return dishService.getAllCategories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createDish(@Valid @RequestBody DishSaveDto dto) {
        int id = dishService.createDish(dto);
        return new RestResponse(String.valueOf(id));
    }

    @PutMapping("/{id}")
    public RestResponse updateDish(@Valid @PathVariable int id, @RequestBody DishSaveDto dto) {
        dishService.updateDish(id, dto);
        return new RestResponse("OK");
    }

    @DeleteMapping("/{id}")
    public RestResponse deleteDish(@Valid @PathVariable int id) {
        dishService.deleteDish(id);
        return new RestResponse("OK");
    }

    @DeleteMapping("/mainPage")
    public RestResponse deleteAll() {
        dishService.deleteAll();
        return new RestResponse("OK");
    }
}
