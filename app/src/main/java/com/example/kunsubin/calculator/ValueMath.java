package com.example.kunsubin.calculator;

import java.util.Stack;

/**
 * Created by kunsubin on 2/18/2017.
 */

public class ValueMath {
    InfixToPostfix fx;
    private boolean isError = false;

    public ValueMath(){
        fx=new InfixToPostfix();
    }
    //tính giá trị biểu thức postfix (đảo balan)
    public Double valueMath(String input){
        //đầu vào
        if(input.equals(""))
        {
            isError=true;
            return 0.0;
        }
        //Loại bỏ dấu phẩy để tính
        input=clearPhay(input);
        //xử lý dữ liệu đầu vào
        input = fx.processInput(input);
        //chuyển infix sang postfix
        input = fx.Infix2Postfix(input);
        String[] value = fx.trimString(input);
        Stack<Double> theStack = new Stack<Double>();
        double num = 0.0;
        double ans = 0.0;
        for(int i=0;i<value.length;i++){
            //là toán hạng(số) đưa vào trong stack
            if(!fx.isOperator(value[i]))
                theStack.push(Double.parseDouble(value[i]));
            else{
                //trường hợp biểu thức chỉ có số(không có toán hạng)
                if (theStack.isEmpty()) {
                    isError=true;
                    return 0.0;
                }
                //tính số âm với độ ưu tiên lớn nhất
                double num1 = theStack.pop();
                String op = value[i];
                if (op.equals("~")) {
                    num = -num1;
                }else{
                    //tính các phép tính còn lại
                    double num2=theStack.peek();
                    switch (op){
                        case "+":
                            num=num1+num2;
                            theStack.pop();
                            break;
                        case "-":
                            num=num2-num1;
                            theStack.pop();
                            break;
                        case "x":
                            num=num1*num2;
                            theStack.pop();
                            break;
                        case "÷":
                            //num1=0 tự động trả về infinity
                            num=num2/num1;

                            theStack.pop();
                            break;
                        default:
                            break;
                    }
                }
                //đưa num vào stack
                theStack.push(num);
            }

        }
        //trả về kết quả khi stack chỉ còn 1 phần tử đưa vào ans
        ans=theStack.pop();
        return ans;
    }
    //get error
    public boolean isError() {
        return isError;
    }
    //set error
    public void setError(boolean isError) {
        this.isError = isError;
    }
    //xoa phay
    public String clearPhay(String input){
        String output;
        output=input.replaceAll(",","");
        return output;
    }
}
