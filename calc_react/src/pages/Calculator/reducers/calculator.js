/* eslint-disable import/no-anonymous-default-export */
const initialState = {
    isLoading: false,
    isError: false,
    list: [],
    
    name: "This is Expressions!!!!!",  
};
    
export default (state = initialState, action) => {
    switch (action.type) {
        case 'ERROR_RECEIVE_EXPRESSIONS': {    
            return {    
                ...state,    
                isLoading: false,
                isError: true,    
                errorText: action.errorTextFromBE,
            };    
        }   
        case 'REQUEST_EXPRESSIONS': {    
            return {    
                ...state,    
                isLoading: true, 
                isError: false,   
            };    
        }    
        case 'RECEIVE_EXPRESSIONS': {    
            const {    
                expressions,    
            } = action;    
            return {    
                ...state,    
                isLoading: false,  
                isError: false,  
                list: expressions,    
            };    
        }    
        default: return state;    
    }    
};