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

// const getExpressions = (expressionsCount) => new Promise((onSuccess) => {
//     setTimeout(
//         () => onSuccess(Array
//         .from(new Array(expressionsCount).keys())
//         .map(index => ({ name: ['2 + 2', '3 - 2', '25 / 5', '4 * 4']}))),
//         2000
//     );
// });

//Simulating backend
// const getExpressions = (expressionsCount) => new Promise((onSuccess) => {
//     setTimeout(
//         () => onSuccess(Array(['2 + 2', '3 - 1', '25 ÷ 5', '4 * 4'])),
//         2000
//     );
// });

const getDishes = () => {
    console.log("In fetch")
    const url = `http://localhost:8080/cafe/dishes`;
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    };
    return fetch(url, options).catch(() => {
        const dishes = [
            {
                "id": 1,
                "dish_name": "Dish №1",
                "weight": 196,
                "price": 8516.76,
                "dish_category": "Category №2",
                "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            },
            {
                "id": 2,
                "dish_name": "Dish №2",
                "weight": 445,
                "price": 4338.65,
                "dish_category": "Category №1",
                "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            },
            {
                "id": 3,
                "dish_name": "Dish №3",
                "weight": 819,
                "price": 8991.08,
                "dish_category": "Category №1",
                "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            },
            {
                "id": 4,
                "dish_name": "Dish №4",
                "weight": 921,
                "price": 5683.05,
                "dish_category": "Category №3",
                "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            },
        ]
        return dishes;
    });
};


export const fetchDishes = () => (dispatch) => {
    dispatch(requestDishes()); // Повідомляю стору, що роблю запит
    return getDishes()
        .then(response => {
            if (response.ok){
                console.log(response.json());
                response.json()
                    .then(dishes => dispatch(receiveDishes(dishes)))
                    .catch(() => dispatch(errorReceiveDishes()));
            }
            else {
                console.log('Error status ' + response.status)
            }
        })
};

// export default {
//     fetchDishes,
// };