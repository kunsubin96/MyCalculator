package com.example.kunsubin.calculator;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by kunsubin on 2/18/2017.
 */

public class InfixToPostfix {
    //kiểm tra chuỗi s có phải là số không
    public boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    //kiểm tra chuỗi s có phải là toán tử không
    public boolean isOperator(String s) {
        String operator[] = { "+", "-", "x", "÷","~", ")", "("};
        Arrays.sort(operator);
        if (Arrays.binarySearch(operator, s) > -1) {
            return true;
        } else {
            return false;
        }
    }

    //thiết lập độ ưu tiên đảo-->nhân, chia-->cộng trừ
    public int priority(String s) {
        if (s.equals("+") || s.equals("-")) {
            return 1;
        }
        if (s.equals("x") || s.equals("÷")) {
            return 2;
        }
        if (s.equals("~")) {
            return 3;
        }
        return 0;
    }

    // kiểm tra xem s có phải là phép toán 1 ngôi không
    public boolean isOneMath(String c) {
        String operator[] = {"(", "~"};
        Arrays.sort(operator);
        if (Arrays.binarySearch(operator, c) > -1) {
            return true;
        } else {
            return false;
        }
    }
    //chuẩn hóa chuổi
    public String standardize(String s) {
        //bỏ khoảng trắng đầu và đuôi
        s = s.trim();
        //loại bỏ các khoảng trắng lên tiếp đẩy về 1 khoảng trắng
        s = s.replaceAll("\\s+", " ");
        return s;
    }
    //cắt chuỗi theo khoảng trắng
    public String[] trimString(String s) {
        String temp[] = s.split(" ");
        return temp;
    }
    //chuẩn hóa biểu thức, kiểm duyệt các trường hợp đặc biệt của phép toán
    public String standardizeMath(String[] s) {
        String s1 = "";
        //đếm số ( và )
        int open = 0, close = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals("(")) {
                open++;
            } else if (s[i].equals(")")) {
                close++;
            }
        }
        //trường hợp -(3+6)...-->0-(3+6)
        if(s.length>1&&(s[0].equals("+")||s[0].equals("-"))&&s[1].equals("("))
            s1="0 "+s1;
        for (int i = 0; i < s.length; i++) {
            //duyệt trường hợp số dương
            if ((i == 0 || (i > 0 && !isNumber(s[i - 1])
                    && !s[i - 1].equals(")")))
                    && (s[i].equals("+"))
                    && (isNumber(s[i + 1]) || s[i + 1].equals("+"))) {
                continue;
            }
            //duyệt trường hợp số âm: 3---4 thành 3-~~4
            if ((i == 0 || (i > 0 && !isNumber(s[i - 1])
                    && !s[i - 1].equals(")")))
                    && (s[i].equals("-"))
                    && (isNumber(s[i + 1]) || s[i + 1].equals("-"))) {
                s1 = s1 + "~ ";
            }
            else {
                    s1 = s1 + s[i] + " ";
            }
            //trường hợp -(-(3+6)) và rộng hơn-->0-(0-(3+6))...
            if((i<s.length-3)&&s[i].equals("(")&&(s[i+1].equals("+")||s[i+1].equals("-"))&&s[i+2].equals("("))
                s1=s1+"0 ";
        }
        // thêm dấu ngoặc ) cuối nếu thiếu
        for (int i = 0; i < (open - close); i++) {
            s1 += ") ";
        }

        return s1;
    }
    // xử lý chuỗi nhập vào
    public String processInput(String sMath) {
        sMath = sMath.toLowerCase();
        //chuẩn hóa biểu thức
        sMath = standardize(sMath);
        String s="";
        //tạo các khoảng trắng cho chuỗi con trong chuỗi biểu thức
        for(int i=0;i<sMath.length();i++){
            if(isOperator(String.valueOf( sMath.charAt(i))))
                s=s+" "+String.valueOf( sMath.charAt(i))+" ";
            else
                s=s+String.valueOf( sMath.charAt(i));
        }
        //chuẩn hóa chuỗi s
        s = standardize(s);
        //kiểm duyệt trường hợp đặc biệt
        s = standardizeMath(trimString(s));
        return s;
    }
    //chuyển sang đảo Balan
    public String Infix2Postfix(String math) {
        //cắt chuỗi
        String[] array = trimString(math);

        String s1 = "";
        Stack<String> theStack = new Stack<String>();
        for (int i = 0; i < array.length; i++) {
            // neu không phải là toán tử mà là số
            if (!isOperator(array[i]))
            {
                s1 = s1 + array[i] + " ";
            } else { 
            	// nếu là toán tử
                if (array[i].equals("(")) {
                    //"(" ->đưa ( vào stack
                    theStack.push(array[i]);
                } else {
                    if (array[i].equals(")")) { // c là ")"
                        // kiểm duyệt lại phần tử trong stack cho đến khi gặp "("
                        String temp = "";
                        do {
                            temp = theStack.peek();
                            if (!temp.equals("(")) {
                                s1 = s1 + theStack.peek() + " ";
                            }
                            theStack.pop();
                        } while (!temp.equals("("));
                    } else {
                        // stack không rỗng
                        // độ ưu tiên toán tử đỉnh stack >= toán tử hiện tại
                        while (!theStack.isEmpty()
                                && priority(theStack.peek()) >= priority(array[i])&&!isOneMath(array[i])) {
                            s1 = s1 + theStack.pop() + " ";
                        }
                        // đưa phần tử hiện tại vào stack
                        theStack.push(array[i]);
                    }
                }
            }
        }
        //nếu còn toán tử trong stack lấy ra lần lượt và đưa vào s1
        while (!theStack.isEmpty()) {
            s1 = s1 + theStack.pop() + " ";
        }
        return s1;
    }
}
