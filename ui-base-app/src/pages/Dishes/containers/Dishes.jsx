import React, {useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {useDispatch, useSelector} from 'react-redux';
import {useHistory} from "react-router-dom";
import List from 'components/List';
import ListItemText from 'components/ListItemText';
import Button from 'components/Button';
import Divider from 'components/Divider';

import {
    fetchDishes,
    fetchDeleteDish,
} from "../actions/dish";


const getClasses = makeStyles(() => ({
    container: {
        display: 'flex',
        flexDirection: 'column',
        width: '100%',
    },
}));

const style = {
    width: '100%',
    maxWidth: 360,
};

const style2 = {
    paddingLeft: 2,
};

function Dishes(){
    const history = useHistory();
    const dishes = useSelector(({ dishes }) => dishes);
    const dispatch = useDispatch();
    const classes = getClasses();

    useEffect(() => {
         console.log("ComponentDidMount");
         dispatch(fetchDishes());
         console.log(dishes);
    }, []);

    function createDish() {
        history.push("/cafe/dishes/edit");
    }

    function editDish(id) {
        history.push(`/cafe/dishes/edit/${id}`);
    }

    function deleteDish(dishId) {
        console.log(`Dish № ${dishId} deleted`);
        dispatch(fetchDeleteDish({
            dishId: dishId,
        }));
    }

    return (
        <div className={classes.container}>
            <div style={{
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'center'
            }}>
                <Button style={{
                    margin: 10,
                    backgroundColor: '#27d827',
                    width: 600,
                }} onClick={createDish} >Create dish</Button>
            </div>
            {!dishes.isLoading && dishes.dishes.map((item) => (
                <List sx={style}>
                    <div style={{
                            display: 'flex',
                            flexDirection: 'row',
                    }}>
                        <ListItemText sx={style2} primary={`Dish name is ${item.dish_name}, its weight is ${item.weight} grams,
                        Price: ${item.price} UAH. ${item.dish_category}. Description: "${item.description}"`} />
                        <Button id={item.id} style={{
                            marginRight: 20,
                        }} onClick={() => deleteDish(item.id)} variant="contained">Delete </Button>
                        <Button id={item.id} style={{
                            marginRight: 100,
                        }} onClick={() => editDish(item.id)} variant="contained">Edit</Button>
                    </div>
                    <Divider />
                </List>
                ))}
        </div>
    )
};

export default Dishes;
