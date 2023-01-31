import React from 'react';
import Button from '@material-ui/core/Button';
import TextareaAutosize from '@mui/base/TextareaAutosize';
import { withStyles } from '@material-ui/core/styles';
import Calculator from './pages/Calculator';
import { applyMiddleware, createStore } from 'redux';
import { Provider } from 'react-redux';
import expressionsReducer from './pages/Calculator/reducers/calculator.js';
import thunkMiddleware from 'redux-thunk';



const store = createStore(expressionsReducer, applyMiddleware(thunkMiddleware));

const RegularButton = withStyles({
  root: {
    backgroundColor: '#313131',
    '&:hover': {
      backgroundColor: '#635f5f',
    },
  },
  hover: {},
})(Button);

const OperatorButton = withStyles({
  root: {
    backgroundColor: '#f69906',
    '&:hover': {
      backgroundColor: '#f8b852',
    },
  },
  hover: {},
})(Button);

const EqualsButton = withStyles({
  root: {
    backgroundColor: '#f63a06',
    '&:hover': {
      backgroundColor: '#f4633b',
    },
  },
  hover: {},
})(Button);

const buttonStyle = {
    outline: 'none',
    border: 'none',
    padding: '5%',
    width: 60,
    display: 'flex',
    margin: '2%',
    cursor: 'pointer',
    color: '#fff',
    fontWeight: 500,
    fontSize: '1.5rem',
    height: 62,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: '50%',
};

let firstValue = '';
let secondValue = '';
let operator = '';
let isFinish = false;

const numbers = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
const operators = ['+', '-', '*', '÷'];
let hstr = [];

class App extends React.Component {
  constructor(props){
    super(props)
    this.state ={
      text:"0",
      history: [],
    }

    this.inputValue = this.inputValue.bind(this)
    this.clearAll = this.clearAll.bind(this)
    this.calculate = this.calculate.bind(this)
  }

  render(){
    return (
      <div className="container">
            <TextareaAutosize
              maxRows={6}
              minRows={6}
              value={this.state.history}
              aria-label="maximum height"
              style={{ width: 300,   height: 180,
                color: 'rgb(163, 162, 162)'}}
            />
            <TextareaAutosize value={this.state.text}></TextareaAutosize>
            <div className="buttons">
              <div className="row">
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>7</RegularButton>
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>8</RegularButton>
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>9</RegularButton>
                  <OperatorButton variant='contained' onClick={this.inputValue} style={buttonStyle}>+</OperatorButton>
              </div>
              <div className="row">
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>4</RegularButton>
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>5</RegularButton>
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>6</RegularButton>
                  <OperatorButton variant='contained' onClick={this.inputValue} style={buttonStyle}>-</OperatorButton>
              </div>
              <div className="row">
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>1</RegularButton>
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>2</RegularButton>
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>3</RegularButton>
                  <OperatorButton 
                    variant='contained' 
                    onClick={this.inputValue}
                    style={buttonStyle}                                    
                  >
                    *
                  </OperatorButton>
              </div>
              <div className="row">
                  <RegularButton variant='contained' onClick={this.inputValue} style={buttonStyle}>0</RegularButton>
                  <RegularButton variant='contained' onClick={this.clearAll} style={buttonStyle}>C</RegularButton>
                  <EqualsButton variant='contained' onClick={this.calculate} style={buttonStyle}>=</EqualsButton>
                  <OperatorButton variant='contained' onClick={this.inputValue} style={buttonStyle}>÷</OperatorButton>
              </div>
              <div className="row">
                  <Provider store={store}>
                    <Calculator/>
                  </Provider>
              </div>
            </div>
      </div>
    )
  }

  inputValue(event){
    const curVal = event.target.textContent;

    if(numbers.includes(curVal)){
      if(secondValue === '' && operator === ''){
        firstValue += curVal
        console.log(firstValue, operator, secondValue)
      }
      else if (firstValue !== '' && secondValue !== '' && isFinish){
        if(operator==='') {
          secondValue = ''
          isFinish = false
          firstValue += curVal          
          this.setState({ text: firstValue + operator + secondValue})
          return
        }
        else{
          secondValue = curVal
          isFinish = false
          console.log(firstValue, operator, secondValue)
        }
      }
      else {
        secondValue += curVal
        console.log(firstValue, operator, secondValue)
      }
    }

    if(operators.includes(curVal)){
      
      if(firstValue !== '' && secondValue !== '' && operator !== ''){
        this.calculate()
      }

      operator = curVal

      this.setState({ text: firstValue + operator + ''})
      console.log(firstValue, operator, secondValue)
      return
    }

    if(this.state.text === '0') {
      this.setState({ text: curVal})
    }
    else {
      //this.setState({ text: this.state.text + curVal})
      this.setState({ text: firstValue + operator + secondValue})
    }
  }

  calculate(){
    if(firstValue === '' || operator === '' || secondValue === '') return
    let a = firstValue 
    switch(operator){
      case '+':
        firstValue = (+firstValue) + (+secondValue)
        break
      case '-':
        firstValue = firstValue - secondValue
        break
      case '*':
        firstValue = firstValue * secondValue
        break
      case '÷':
        if(secondValue === '0'){
          this.setState({ text: "Do not divide on 0"})
          this.clearAll()
          return
        }
        firstValue = firstValue / secondValue
        break
      default:
        this.clearAll()
        return
    }

    let wholeExpression = a + operator + secondValue + '=' + firstValue + '\n'
    console.log(wholeExpression)
    hstr.push(wholeExpression)

    isFinish = true
    this.setState({ text: firstValue, history: hstr.join('')})
    operator = ''

    console.log(firstValue, operator, secondValue)
  }

  clearAll(){
    firstValue = ''
    secondValue = ''
    operator = ''
    isFinish = false
    this.setState({ text: '0'})
  }

  parse(json){


  }

}

export default App;
