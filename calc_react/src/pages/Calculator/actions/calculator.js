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

//Simulating backend
//Please uncomment this part and comment out the same below if you run the application with a simulated backend
// const getExpressions = (expressionsCount) => new Promise((onSuccess) => {
//     setTimeout(
//         () => onSuccess(Array(['2 + 2', '3 - 1', '25 ÷ 5', '4 * 4'])),
//         2000
//     );
// });

const getExpressions = (expressionsCount) => {
    const url = `http://localhost:8080/?count=${expressionsCount}`;
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    };
    return fetch(url, options);
};

const fetchExpresions = ({ expressionsCount }) => (dispatch) => {
    dispatch(requestExpressions()); // Повідомляю стору, що роблю запит 
    return getExpressions(expressionsCount)
    .then(response => {
        if (response.ok){
            response.json()
            .then(expressions => dispatch(receiveExpressions(expressions)))
            .catch(() => dispatch(errorReceiveExpressions()));
        }
        else {
            console.log('Error status ' + response.status)
        }
    })
};

export default {
    fetchExpresions,
};