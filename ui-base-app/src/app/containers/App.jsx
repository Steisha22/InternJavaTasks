import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import {
  BrowserRouter,
  Switch,
  Redirect,
  Route,
} from 'react-router-dom';
import IntlProvider from 'components/IntlProvider';
import Header from 'components/Header';
import PageInitial from 'pageProviders/Initial';
import PageLogin from 'pageProviders/Login';
import PageDishes from 'pageProviders/Dishes';
import PageEditDish from 'pageProviders/EditDish';
import * as PAGES from 'constants/pages';
import {
  fetchUser,
} from '../actions/user';

const App = () => {
  const [state, setState] = useState({
    componentDidMount: false,
  });
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchUser());
    setState(prevState => ({
      ...prevState,
      componentDidMount: true,
    }));
  }, []);

  return (
    <BrowserRouter>
      <IntlProvider>
        <Header />
        {state.componentDidMount && (
            <Switch>
              <Route path={`/${PAGES.LOGIN}`}>
                <PageLogin />
              </Route>
              <Route path={`/${PAGES.INITIAL}`}>
                <PageInitial />
              </Route>
              <Route path={'/cafe/dishes/edit/:id'}>
                <PageEditDish />
              </Route>
              <Route path={'/cafe/dishes/edit'}>
                <PageEditDish />
              </Route>
              <Route path={'/cafe/dishes'}>
                <PageDishes />
              </Route>
              {/*<Route path={'/edit/:id'}>*/}
              {/*  <PageEditDish />*/}
              {/*</Route>*/}
              {/*<Route path={'/edit'}>*/}
              {/*  <PageEditDish />*/}
              {/*</Route>*/}
              <Redirect from="*" to={`/${PAGES.INITIAL}`} />
            </Switch>
        )}
      </IntlProvider>
    </BrowserRouter>
  );
};

export default App;
