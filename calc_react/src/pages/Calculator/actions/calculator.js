import App from 'C:/Study/calc_react/src/App';
const receiveExpressions = expressions => ({
    expressions, 
    type: 'RECEIVE_EXPRESSIONS' 
});

const requestExpressions = () => ({ 
    type: 'REQUEST_EXPRESSIONS' 
});

const errorReceiveExpressions = () => ({ 
    type: 'ERROR_RECEIVE_EXPRESSIONS' 
});

// const getExpressions = (expressionsCount) => new Promise((onSuccess) => {
//     setTimeout(
//         () => onSuccess(Array
//         .from(new Array(expressionsCount).keys())
//         .map(index => ({ name: ['2 + 2', '3 - 2', '25 / 5', '4 * 4']}))),
//         2000
//     );
// });

const getExpressions = (expressionsCount) => {
    return fetch(`localhost/8080?${expressionsCount}`)
    .then(response => response.json())
    .then((json) => {
      App.parse(json)
    })
};

const fetchExpresions = ({ expressionsCount }) => (dispatch) => {
    dispatch(requestExpressions()); // Повідомляю стору, що роблю запит 
    return getExpressions(expressionsCount) // Викликаю функцію запиту студентів
    .then(expressions => dispatch(receiveExpressions(expressions))) // Успіх
    .catch(() => dispatch(errorReceiveExpressions())); // Помилка
};

export default {
    fetchExpresions,
};