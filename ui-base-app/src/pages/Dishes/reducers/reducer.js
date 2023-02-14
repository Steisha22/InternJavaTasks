// const initialState = {
//   dishes: [
//       {
//         "id": 1,
//         "dish_name": "Dish №1",
//         "weight": 196,
//         "price": 8516.76,
//         "dish_category": "Category №2",
//         "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
//       },
//       {
//         "id": 2,
//         "dish_name": "Dish №2",
//         "weight": 445,
//         "price": 4338.65,
//         "dish_category": "Category №1",
//         "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
//       },
//       {
//         "id": 3,
//         "dish_name": "Dish №3",
//         "weight": 819,
//         "price": 8991.08,
//         "dish_category": "Category №1",
//         "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
//       },
//       {
//         "id": 4,
//         "dish_name": "Dish №4",
//         "weight": 921,
//         "price": 5683.05,
//         "dish_category": "Category №3",
//         "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
//       },
//   ],
//   isLoading: false,
//   isError: false,
// };
//
const initialState = {
  isLoading: false,
  isError: false,
  dishes: [
      {
        "id": 4,
        "dish_name": "Dish №4",
        "weight": 921,
        "price": 5683.05,
        "dish_category": "Category №3",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
      },
  ],
};

export default (state = initialState, action) => {
  switch (action.type) {
    case 'ERROR_RECEIVE_DISHES': {
      return {
        ...state,
        isLoading: false,
        isError: true,
        errorText: action.errorTextFromBE,
      };
    }
    case 'REQUEST_DISHES': {
      return {
        ...state,
        isLoading: true,
        isError: false,
      };
    }
    case 'RECEIVE_DISHES': {
      const {
        dishes,
      } = action;
      console.log("RECEIVE_DISHES");
      return {
        ...state,
        isLoading: false,
        isError: false,
        dishes: dishes,
      };
    }
    default:
      console.log("default");
      return state;
  }
};

// export default (state = initialState, {type, payload}) => {
//   switch (type) {
//
//     default: return state;
//   }
// }
