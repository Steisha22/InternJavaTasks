const receiveDishes = dishes => ({
    dishes,
    type: 'RECEIVE_DISHES'
});

const requestDishes = () => ({
    type: 'REQUEST_DISHES'
});

const errorReceiveDishes = () => ({
    type: 'ERROR_RECEIVE_DISHES'
});

const getDishes = () => {
    console.log("In fetch")
    const url = `http://localhost:8080/cafe/dishes`;
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    };
    return fetch(url, options);
};

export const fetchDishes = () => (dispatch) => {
    dispatch(requestDishes()); // Повідомляю стору, що роблю запит
    return getDishes()
        .then(response => {
            if (response.ok){
                response.json()
                    .then(dishes => dispatch(receiveDishes(dishes)))
                    .catch(() => dispatch(errorReceiveDishes()));
            }
            else {
                console.log('Error status ' + response.status)
            }
        })
};

const deleteDish = (dishId) => {
    console.log(`In fetch ${dishId}`)
    const url = `http://localhost:8080/cafe/${dishId}`;
    const options = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    };
    return fetch(url, options);
}

export const fetchDeleteDish = ({ dishId }) => (dispatch) => {
    console.log(dishId);
    return deleteDish(dishId)
        .then(() => {
            dispatch(fetchDishes())
        })
};

const getDishById = (dishId) => {
    console.log(`In fetch ${dishId}`)
    const url = `http://localhost:8080/cafe/${dishId}`;
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    };
    return fetch(url, options);
};

export const fetchDishById = ({ dishId }) => (dispatch) => {
    dispatch(requestDishes());
    return getDishById(dishId)
        .then(response => {
            if (response.ok){
                response.json()
                    .then(dishes => dispatch(receiveDishes(dishes)))
                    .catch(() => dispatch(errorReceiveDishes()));
            }
            else {
                console.log('Error status ' + response.status)
            }
        })
};

const createDish = ({dish_name, weight, price, description, categoryId }) => {
    console.log(`In fetch ${dish_name}`)
    const url = `http://localhost:8080/cafe`;
    const options = {
        method: 'POST',
        body: JSON.stringify({
            dish_name: `${dish_name}`,
            weight: `${weight}`,
            price: `${price}`,
            description: `${description}`,
            categoryId: `${categoryId}`
        }),
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    };
    return fetch(url, options);
};

export const fetchCreateDish = ({ dish_name, weight, price, description, categoryId }) => (dispatch) => {
    return createDish({
        dish_name,
        weight,
        price,
        description,
        categoryId
    })
        .then(response => {
            if (response.ok){
                console.log("Dish created");
            }
            else {
                console.log('Error status ' + response.status)
            }
        })
};

const editDish = ({id, dish_name, weight, price, description, categoryId }) => {
    console.log(`In fetch ${dish_name}`)
    const url = `http://localhost:8080/cafe/${id}`;
    const options = {
        method: 'PUT',
        body: JSON.stringify({
            dish_name: `${dish_name}`,
            weight: `${weight}`,
            price: `${price}`,
            description: `${description}`,
            categoryId: `${categoryId}`
        }),
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    };
    return fetch(url, options);
};

export const fetchEditDish = ({ id, dish_name, weight, price, description, categoryId }) => (dispatch) => {
    return editDish({
        id,
        dish_name,
        weight,
        price,
        description,
        categoryId
    })
        .then(dispatch(fetchDishes()));
};