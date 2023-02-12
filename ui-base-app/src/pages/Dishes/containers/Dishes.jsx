import React, {useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {useDispatch, useSelector} from 'react-redux';
import Typography from 'components/Typography';
import List from 'components/List';
import ListItemText from 'components/ListItemText';
import Button from 'components/Button';
import Divider from 'components/Divider';
import useAccessValidate from 'hooks/useAccessValidate';

import {
    fetchDishes,
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

const Dishes = ({
                     authorities,
                 }) => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log("ComponentDidMount");
        dispatch(fetchDishes())
    }, []);
    const classes = getClasses();
    // const {
    //     availableItems,
    // } = useSelector(({ reducer })=> reducer);
    const dishes = useSelector(({ dishes }) => dishes);
    const canSeeList = useAccessValidate({
        ownedAuthorities: authorities,
        neededAuthorities: ['МОЖНО_ВОТ_ЭТУ_ШТУКУ'],
    });

    return (
        <div className={classes.container}>
            {canSeeList && dishes.map((item, index) => (
                <List sx={style}>
                    <div style={{
                            display: 'flex',
                            flexDirection: 'row',
                    }}>
                        <ListItemText sx={style2} primary={`${item.dish_name}`} />
                        <Button style={{
                            marginRight: 20,
                        }} variant="contained">Delete </Button>
                        <Button style={{
                            marginRight: 100,
                        }} variant="contained">Edit</Button>
                    </div>
                    <Divider />
                </List>

                // <Link
                //     // href={index % 2 === 0
                //     //   ? `https://www.google.com.ua/search?q=${item}&hl=ru`
                //     //   : undefined}
                //     // to={index % 2 !== 0
                //     //   ? (location => ({
                //     //     ...location,
                //     //     pathname: `/${item}/dishes`,
                //     //     search: `${location.search}&newProp=42`,
                //     //   }))
                //     //   : undefined}
                //     href={`${item}`}
                // >
                //     <Typography>
                //         {item}
                //     </Typography>
                // </Link>
            ))}
            {!canSeeList && (
                <Typography>
                    Не могу ничего показать :(
                </Typography>
            )}
        </div>
    )
};

export default Dishes;
