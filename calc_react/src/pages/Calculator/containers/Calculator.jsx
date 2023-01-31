import React from "react";
import {connect} from 'react-redux';
import Button from '@material-ui/core/Button';
import calculatorActions from '../actions/calculator';
import { withStyles } from '@material-ui/core/styles';
import { bindActionCreators } from 'redux';

const StyledButton = withStyles({
    root: {
    outline: 'none',
    border: 'none',
    padding: '5%',
    width: 300,
    display: 'flex',
    marginTop: '2%',
    cursor: 'pointer',
    color: '#fff',
    fontWeight: 500,
    fontSize: '1rem',
    height: 62,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#313131',
    '&:hover': {
        backgroundColor: '#635f5f',
    },
    },
    hover: {},
  })(Button);

class Calculator extends React.Component{
    render () {
        console.log(this.props);
        return (
            <div>
                <StyledButton 
                    onClick={() => this.props.actionFetchExpressions({
                        expressionsCount: 5,
                    })}>
                Get and solve expressions
                </StyledButton>
            </div>
        )
    }
}

const mapReduxStateToProps = reduxState => ({
    ...reduxState,    
});

const mapDispatchToProps = (dispatch) => {
    const {
        fetchExpresions,
    } = bindActionCreators(calculatorActions, dispatch);
    return ({
      actionFetchExpressions: fetchExpresions,
    });
   };
   

export default connect(mapReduxStateToProps, mapDispatchToProps)(Calculator);