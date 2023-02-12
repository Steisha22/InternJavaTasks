import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {useSelector} from 'react-redux';
import Link from 'components/Link';
import Typography from 'components/Typography';
import useAccessValidate from 'hooks/useAccessValidate';

const getClasses = makeStyles(() => ({
  container: {
    display: 'flex',
    flexDirection: 'column',
  },
}));

const Initial = ({
  authorities,
}) => {
  const classes = getClasses();
  const {
    availableItems,
  } = useSelector(({ reducer })=> reducer);
  const canSeeList = useAccessValidate({
    ownedAuthorities: authorities,
    neededAuthorities: ['МОЖНО_ВОТ_ЭТУ_ШТУКУ'],
  });

  return (
        <div className={classes.container}>
          {canSeeList && availableItems.map((item, index) => (
              //<a href={`/${item}/dishes`} onClick={() => console.log("kjshfjhj")}>{item}</a>
              // <a href={`/${item}/dishes`} onClick={() => (dispatch) => {
              //
              //   dispatch(requestDishes()); // Повідомляю стору, що роблю запит
              //   return getDishes()
              //       .then(response => {
              //         if (response.ok){
              //           console.log(response.json());
              //           response.json()
              //               .then(dishes => dispatch(receiveDishes(dishes)))
              //               .catch(() => dispatch(errorReceiveDishes()));
              //         }
              //         else {
              //           console.log('Error status ' + response.status)
              //         }
              //       })
              // }}>{item}</a>
            <Link
              // href={index % 2 === 0
              //   ? `https://www.google.com.ua/search?q=${item}&hl=ru`
              //   : undefined}
              // to={index % 2 !== 0
              //   ? (location => ({
              //     ...location,
              //     pathname: `/${item}/dishes`,
              //     search: `${location.search}&newProp=42`,
              //   }))
              //   : undefined}
                href={`/${item}/dishes`}
            >
              <Typography>
                {item}
              </Typography>
            </Link>
          ))}
          {!canSeeList && (
            <Typography>
              Не могу ничего показать :(
            </Typography>
          )}
        </div>
  )
};

export default Initial;
