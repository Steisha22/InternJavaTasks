import React, {useEffect, useState, useRef} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {useHistory, useParams} from "react-router-dom";
import Button from 'components/Button';
import TextField from 'components/TextField';
import {useDispatch, useSelector} from "react-redux";

import {
    fetchDishes,
    fetchDishById,
    fetchCreateDish, fetchEditDish,
} from "pages/Dishes/actions/dish";

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(2),
            width: '55ch',
        },
    },
}));

const getClasses = makeStyles(() => ({
    container: {
        display: 'flex',
        flexDirection: 'column',
        width: '100%',
    },
}));

const initialState = {
    dish_name: "",
    weight: '',
    price: '',
    description: "",
    categoryId: ''
};

function EditDish(){
    const params = useParams();
    const current = params.id;
    const classes = getClasses();
    const classes2 = useStyles();
    const history = useHistory();
    const dishes = useSelector(({ dishes }) => dishes);
    const dispatch = useDispatch();
    const [state, setState] = useState(initialState);
    //const dishNameRef = useRef('afafaf');

    useEffect(() => {
        //console.log(state);
        if(current !== undefined){
            dispatch(fetchDishById({
                dishId: current,
            }));
        }
    }, []);

    useEffect(() => {
        setState(dishes.dishes);
    }, [dishes]);

    async function saveChanges(){
        if(current !== undefined){
            await dispatch(fetchEditDish({
                id: current,
                dish_name: state.dish_name,
                weight: state.weight,
                price: state.price,
                description: state.description,
                categoryId: state.categoryId,
            }));
        }
        else{
            await dispatch(fetchCreateDish({
                dish_name: state.dish_name,
                weight: state.weight,
                price: state.price,
                description: state.description,
                categoryId: state.categoryId,
            }));
        }
        history.push('/cafe/dishes');
    }

    return (
        <div className={classes.container}>
            {current === undefined && (
                <form className={classes2.root} noValidate autoComplete="off">
                    <div>
                        <h1>Create dish</h1>
                        <TextField
                        fullWidth
                        inputType="text"
                        label='dish_name'
                        variant="outlined"
                        onChange={({ target }) => setState(prevState => ({
                        ...prevState,
                        dish_name: target.value,
                    }))}
                        />
                        <TextField
                        fullWidth
                        inputType="text"
                        label='weight'
                        variant="outlined"
                        onChange={({ target }) => setState(prevState => ({
                        ...prevState,
                        weight: target.value,
                    }))}
                        />
                        <TextField
                        fullWidth
                        inputType="text"
                        label='price'
                        variant="outlined"
                        onChange={({ target }) => setState(prevState => ({
                        ...prevState,
                        price: target.value,
                    }))}
                        />
                        <TextField
                        fullWidth
                        inputType="text"
                        label='description'
                        variant="outlined"
                        onChange={({ target }) => setState(prevState => ({
                        ...prevState,
                        description: target.value,
                    }))}
                        />
                        <TextField
                        fullWidth
                        inputType="Number"
                        label='categoryId'
                        variant="outlined"
                        onChange={({ target }) => setState(prevState => ({
                        ...prevState,
                        categoryId: target.value,
                    }))}
                        />
                    </div>
                </form>
            )}
            {current !== undefined && (
                <form className={classes2.root} noValidate autoComplete="off">
                    <div>
                        <h1>Edit dish with ID: {current}</h1>
                        <TextField
                            fullWidth
                            inputType="text"
                            label='dish_name'
                            variant="outlined"
                            onChange={({ target }) => setState(prevState => ({
                                ...prevState,
                                dish_name: target.value,
                            }))}
                            value={state.dish_name}
                        />
                        <TextField
                            fullWidth
                            inputType="text"
                            label='weight'
                            variant="outlined"
                            onChange={({ target }) => setState(prevState => ({
                                ...prevState,
                                weight: target.value,
                            }))}
                            value={state.weight}
                        />
                        <TextField
                            fullWidth
                            inputType="text"
                            label='price'
                            variant="outlined"
                            onChange={({ target }) => setState(prevState => ({
                                ...prevState,
                                price: target.value,
                            }))}
                            value={state.price}
                        />
                        <TextField
                            fullWidth
                            inputType="text"
                            label='description'
                            variant="outlined"
                            onChange={({ target }) => setState(prevState => ({
                                ...prevState,
                                description: target.value,
                            }))}
                            value={state.description}
                        />
                        <TextField
                            fullWidth
                            inputType="text"
                            label='categoryId'
                            variant="outlined"
                            onChange={({ target }) => setState(prevState => ({
                                ...prevState,
                                categoryId: target.value,
                            }))}
                            value={state.categoryId}
                        />
                    </div>
                </form>
            )}
            <div style={{
                margin: 30,
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-around'
            }}>
                <Button style={{
                    height: 50,
                    width: 200,
                    backgroundColor: "red",
                }} onClick={() => {history.goBack(); dispatch(fetchDishes())}}>Cancel</Button>
                <Button style={{
                    height: 50,
                    width: 200,
                    backgroundColor: "#27d827",
                }} onClick={saveChanges}>Save</Button>
            </div>
            {/*<button onClick={() => hist.goBack()}>Go Back</button>*/}
            {/*<button onClick={() => hist.push(/shop/${next})}>Next product</button>*/}
        </div>
    );
};

export default EditDish;