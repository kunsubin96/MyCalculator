package com.example.kunsubin.calculator;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {

    private TextView nhap;
    private Button so0, so1, so2, so3, so4, so5, so6, so7, so8, so9;
    private Button nhan, chia, cong, tru;
    private Button xoaAll, dao, phay, ngoac, bang;
    private ImageButton xoa;
    private ImageButton xuong;
    //cuộn nội dung trong textview xuống khi đầy lăn xuống hàng
    private ScrollView scrollView;
    //xét sau khi nhấn dấu = ra kết quả thì nhấn toán tử đẩy kết quả lên cộng tiếp
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setOnClickButton();
        setOnTouchButton();
        //auto size textview
        //set ontouch cho imagebutton xoa
        xoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.getBackground().setColorFilter(getResources().getColor(R.color.hieuUngButton),PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


    }
    //ánh xạ với các view
    public void init() {
        nhap = (TextView) findViewById(R.id.nhap);

        scrollView=(ScrollView)findViewById(R.id.scrollView);

        so0 = (Button) findViewById(R.id.so0);
        so1 = (Button) findViewById(R.id.so1);
        so2 = (Button) findViewById(R.id.so2);
        so3 = (Button) findViewById(R.id.so3);
        so4 = (Button) findViewById(R.id.so4);
        so5 = (Button) findViewById(R.id.so5);
        so6 = (Button) findViewById(R.id.so6);
        so7 = (Button) findViewById(R.id.so7);
        so8 = (Button) findViewById(R.id.so8);
        so9 = (Button) findViewById(R.id.so9);

        nhan = (Button) findViewById(R.id.nhan);
        chia = (Button) findViewById(R.id.chia);
        cong = (Button) findViewById(R.id.cong);
        tru = (Button) findViewById(R.id.tru);

        xoa = (ImageButton) findViewById(R.id.xoa);
        xoaAll = (Button) findViewById(R.id.xoaAll);
        dao = (Button) findViewById(R.id.dao);
        phay = (Button) findViewById(R.id.phay);
        ngoac = (Button) findViewById(R.id.ngoac);
        bang = (Button) findViewById(R.id.bang);

        xuong=(ImageButton)findViewById(R.id.xuong);
    }
    //set event click cho các button
    public void setOnClickButton() {
        so0.setOnClickListener(this);
        so1.setOnClickListener(this);
        so2.setOnClickListener(this);
        so3.setOnClickListener(this);
        so4.setOnClickListener(this);
        so5.setOnClickListener(this);
        so6.setOnClickListener(this);
        so7.setOnClickListener(this);
        so8.setOnClickListener(this);
        so9.setOnClickListener(this);

        nhan.setOnClickListener(this);
        chia.setOnClickListener(this);
        cong.setOnClickListener(this);
        tru.setOnClickListener(this);

        xoa.setOnClickListener(this);
        xoaAll.setOnClickListener(this);
        dao.setOnClickListener(this);
        phay.setOnClickListener(this);
        ngoac.setOnClickListener(this);
        bang.setOnClickListener(this);

    }
    //set event ontouch cho các button
    public void setOnTouchButton(){
        so0.setOnTouchListener(this);
        so1.setOnTouchListener(this);
        so2.setOnTouchListener(this);
        so3.setOnTouchListener(this);
        so4.setOnTouchListener(this);
        so5.setOnTouchListener(this);
        so6.setOnTouchListener(this);
        so7.setOnTouchListener(this);
        so8.setOnTouchListener(this);
        so9.setOnTouchListener(this);

        nhan.setOnTouchListener(this);
        chia.setOnTouchListener(this);
        cong.setOnTouchListener(this);
        tru.setOnTouchListener(this);

        xoaAll.setOnTouchListener(this);
        dao.setOnTouchListener(this);
        phay.setOnTouchListener(this);
        ngoac.setOnTouchListener(this);
        bang.setOnTouchListener(this);
    }
    //xử lý sự kiện click của các nút
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.so0:
                //nếu phép tính trả xong kết quả khi nhấn = rùi, nhấn số thì xét lại
                if(!flag){
                    //( )3 --->( )*3
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so0.getText());
                    else{
                        //xử lý 0000-->0 nhấn vô nghĩa, nếu 0. thì cho nhấn tiếp
                        if(Chuoi0())
                            nhap.append(so0.getText());
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so0.getText());
                    flag=false;
                }
                //set dấu phẩy phần nghìn
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so1:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so1.getText());
                    else{
                        //01-->1 tránh trường hợp 0111-->0,111
                        if(Chuoi0())
                            nhap.append(so1.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so1.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so1.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so1.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so2:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so2.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so2.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so2.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so2.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so2.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so3:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so3.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so3.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so3.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so3.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so3.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so4:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so4.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so4.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so4.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so4.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so4.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so5:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so5.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so5.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so5.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so5.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so5.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so6:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so6.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so6.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so6.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so6.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so6.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so7:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so7.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so7.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so7.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so7.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so7.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so8:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so8.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so8.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so8.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so8.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so8.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.so9:
                if(!flag){
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+so9.getText());
                    else{
                        if(Chuoi0())
                            nhap.append(so9.getText());
                        else{
                            if(nhap.getText().length()>1){
                                nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                                nhap.append(so9.getText());
                            }else{
                                nhap.setText("");
                                nhap.append(so9.getText());
                            }
                        }
                    }
                }else{
                    xoaAll.performClick();
                    nhap.append(so9.getText());
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.nhan:
                //nếu phép tính trả xong kết quả khi nhấn = rùi, nhấn phép toán thì lấy kết quả cộng dồn lên
                if(!flag)
                    xulyPhepTinh("x");
                else {
                    nhap.setText(getKq());
                    xulyPhepTinh("x");
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.chia:
                if(!flag)
                    xulyPhepTinh("÷");
                else {
                    nhap.setText(getKq());
                    xulyPhepTinh("÷");
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.cong:
                if(!flag)
                    xulyPhepTinh("+");
                else {
                    nhap.setText(getKq());
                    xulyPhepTinh("+");
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.tru:
                if(!flag)
                    xulyPhepTinh("-");
                else {
                    nhap.setText(getKq());
                    xulyPhepTinh("-");
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.xoaAll:
                nhap.setText("");
                flag=false;
                break;
            case R.id.xoa:
                if(flag){
                    String[] arr=nhap.getText().toString().split("=");
                    nhap.setText(arr[0].trim());
                    if (nhap.getText().length() > 0)
                    {
                        //cắt rùi set chuỗi
                        nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                    }
                }else{
                    if (nhap.getText().length() > 0)
                    {
                        //cắt rùi set chuỗi
                        nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1));
                    }
                }
                flag=false;
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.ngoac:
                if(!flag){
                    boolean check=checkNgoac();
                    if(check){
                        // 3( )-->3*( ) or )( --> )*(
                        if(nhap.getText().toString().length()>0&&(nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')'
                                ||nhap.getText().toString().charAt(nhap.getText().toString().length()-1)=='.'
                                || new InfixToPostfix().isNumber(String.valueOf(nhap.getText().toString().charAt(nhap.getText().toString().length()-1)))))
                            nhap.append("x(");
                        else
                            nhap.append("(");
                    }
                    else
                    {
                        nhap.append(")");
                    }
                }
                else{
                    xoaAll.performClick();
                    nhap.append("(");
                    flag=false;
                }
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.phay:
                if(!flag){
                    //).-->)*0.
                    if(nhap.getText().toString().length()>0&&nhap.getText().toString().charAt(nhap.getText().toString().length()-1)==')')
                        nhap.append("x"+checkPhay(nhap.getText().toString()));
                    else
                        nhap.append(checkPhay(nhap.getText().toString()));
                }else{
                    xoaAll.performClick();
                    nhap.append("0.");
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.bang:
                try{
                    if(flag){
                        nhap.setText(nhap.getText());
                    }else{
                        String input =nhap.getText().toString();
                        Double ouput;
                        ValueMath vl=new ValueMath();
                        ouput=vl.valueMath(input);

                        if(vl.isError()){
                            Toast.makeText(this.getApplicationContext(),"Biểu thức trống",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            nhap.append("\n");
                            if(ouput>=0){
                                //hiện kết quả thêm , phần ngàn và nếu đuôi là .0 thì khử luôn
                                nhap.append("= "+getDecimalFormattedFromString(removeLastChars(ouput,".0")));
                            }else{
                                //Trường hợp dấu âm khử - sau đó xet phần ngàn rùi thêm - đằng trước
                                String kq=String.valueOf(ouput);
                                kq=kq.subSequence(1, kq.length()).toString();
                                //hiện kết quả thêm , phần ngàn và nếu đuôi là .0 thì khử luôn
                                nhap.append("= -"+getDecimalFormattedFromString(removeLastChars(Double.parseDouble(kq),".0")));
                            }
                            flag=true;
                            setColorChar();
                            autoScroll();
                        }
                    }
                }catch (Exception ex){
                    Toast.makeText(this.getApplicationContext(),"Lỗi",Toast.LENGTH_SHORT).show();
                }
                finally {
                    break;
                }
            case R.id.dao:
                if(!flag)
                    xulyDao();
                else {
                    nhap.setText(getKq());
                    xulyDao();
                    flag=false;
                }
                nhap.setText(showDecimalFormat());
                setColorChar();
                scrollView.fullScroll(View.FOCUS_DOWN);
                break;
            default:
                break;
        }

    }
    //kiểm tra ngoặc đóng mở cho biểu thức
    public boolean checkNgoac() {
        String s = nhap.getText().toString().replaceAll(",","");
        int counter1 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                counter1++;
            }
        }
        int counter2 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                counter2++;
            }
        }
        if(nhap.getText().toString().trim().equals(""))
            return true;
        if(nhap.getText().charAt(nhap.getText().length()-1)=='(')
            return true;
        if(nhap.getText().charAt(nhap.getText().length()-1)=='+'||
                nhap.getText().charAt(nhap.getText().length()-1)=='-'||
        nhap.getText().charAt(nhap.getText().length()-1)=='x'||
        nhap.getText().charAt(nhap.getText().length()-1)=='÷')
            return true;
        if(counter1>counter2)
            return false;
        else return true;

    }
    //check phẩy
    public String checkPhay(String input){
        if(input.equals(""))
            return "0.";
        InfixToPostfix fx=new InfixToPostfix();
        //bỏ , để xử lý
        String temp=input.replaceAll(",","");
        String s="";
        //tạo các khoảng trắng cho chuỗi con trong chuỗi biểu thức
        for(int i=0;i<temp.length();i++){
            if(fx.isOperator(String.valueOf(temp.charAt(i))))
                s=s+" "+String.valueOf( temp.charAt(i))+" ";
            else
                s=s+String.valueOf(temp.charAt(i));
        }
        s=s.trim();
        s = s.replaceAll("\\s+", " ");

        String[]arr=s.split(" ");
        if(fx.isOperator(arr[arr.length-1]))
            return "0.";
        for(int i=0;i<arr[arr.length-1].length();i++){
            if(arr[arr.length-1].charAt(i)=='.')
                return "";
        }
        return ".";

    }
    //hiệu ứng nhấn nút button (on touch button)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Button b=(Button)view;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                b.getBackground().setColorFilter(getResources().getColor(R.color.hieuUngButton),PorterDuff.Mode.SRC_ATOP);
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                b.getBackground().clearColorFilter();
                view.invalidate();
                break;
            }
        }
        return false;
    }
    //loại bỏ .0 cuối cùng của kế quả nguyên
    private String removeLastChars(double eval, String text){

        String res = String.valueOf(eval);
        int length = text.length();

        if (res.length() > length){
            res = res.substring((res.length() - length), res.length()).equals(text)
                    ? res.substring(0, (res.length() - length)) : res;
        }

        return res;
    }
    //xử lý nhấn dấu (phép toán) thỏa điều kiện
    public void xulyPhepTinh(String dau){
        InfixToPostfix fx=new InfixToPostfix();
        String temp=nhap.getText().toString().replaceAll(",","");
        String s="";
        //tạo các khoảng trắng cho chuỗi con trong chuỗi biểu thức
        for(int i=0;i<temp.length();i++){
            if(fx.isOperator(String.valueOf(temp.charAt(i))))
                s=s+" "+String.valueOf( temp.charAt(i))+" ";
            else
                s=s+String.valueOf(temp.charAt(i));
        }
        s=s.trim();
        s = s.replaceAll("\\s+", " ");

        String []Array=s.split(" ");

        if(dau.equals("x")||dau.equals("÷")){
            if(!nhap.getText().toString().equals("")){
                if(fx.isNumber(Array[Array.length-1])||Array[Array.length-1].equals(")"))
                {
                    nhap.setText(nhap.getText()+dau);
                }else{
                    if((Array[Array.length-1].equals("+")||Array[Array.length-1].equals("-")||Array[Array.length-1].equals("x")||
                            Array[Array.length-1].equals("÷"))&&nhap.getText().toString().length()>1){
                        nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1)+dau);
                    }else {

                    }
                }
            }else {

            }
        }
        if(dau.equals("+")||dau.equals("-")){
            if(!nhap.getText().toString().equals("")){
                if(fx.isNumber(Array[Array.length-1])||Array[Array.length-1].equals(")"))
                {
                    nhap.setText(nhap.getText()+dau);
                }else{
                    if(Array[Array.length-1].equals("+")||Array[Array.length-1].equals("-")||Array[Array.length-1].equals("x")||
                            Array[Array.length-1].equals("÷")){
                        nhap.setText(nhap.getText().subSequence(0, nhap.getText().length() - 1)+dau);
                    }else if(Array[Array.length-1].equals("(")){
                        nhap.setText(nhap.getText()+dau);
                    }
                }
            }else {
                nhap.setText(nhap.getText()+dau);
            }
        }
    }
    //xử lý dấu đảo +/- phép âm: khi số cuối là số và nhấn +/- vd: 34--> (-34) và ngược lại (-34)-->34
    public void xulyDao(){
        InfixToPostfix fx=new InfixToPostfix();
        String temp=nhap.getText().toString().replaceAll(",","");
        String s="";
        //tạo các khoảng trắng cho chuỗi con trong chuỗi biểu thức
        for(int i=0;i<temp.length();i++){
            if(fx.isOperator(String.valueOf(temp.charAt(i))))
                s=s+" "+String.valueOf( temp.charAt(i))+" ";
            else
                s=s+String.valueOf(temp.charAt(i));
        }
        s=s.trim();
        s = s.replaceAll("\\s+", " ");

        String []Array=s.split(" ");

        if(fx.isNumber(Array[Array.length-1])&&!Array[Array.length-1].equals("0")&&!Array[Array.length-1].equals("0.")){
            String a="(-"+getDecimalFormattedFromString(Array[Array.length-1])+")";
            nhap.setText(nhap.getText().subSequence(0, nhap.getText().length()-getDecimalFormattedFromString(Array[Array.length-1]).length())+a);
        }else if(Array.length>3&&Array[Array.length-1].equals(")")&&fx.isNumber(Array[Array.length-2])&&
                Array[Array.length-3].equals("-")&&
                Array[Array.length-4].equals("(")){
            String a=getDecimalFormattedFromString(Array[Array.length-2]);
            nhap.setText(nhap.getText().subSequence(0, nhap.getText().length()-getDecimalFormattedFromString(Array[Array.length-2]).length()-3)+a);
        }

    }
    //set color ký tự biểu thức
    public void setColorChar(){
        InfixToPostfix fx=new InfixToPostfix();
        String temp=nhap.getText().toString();
        String s="";
        if(flag){
            String []arr=temp.split("=");
            arr[0]=arr[0].trim();
            for(int i=0;i<arr[0].length();i++){
                if(fx.isNumber(String.valueOf(temp.charAt(i)))||temp.charAt(i)=='('||temp.charAt(i)==')'||temp.charAt(i)=='.'||temp.charAt(i)==',')
                    s=s+"<font color='"+"#4a5968"+"'>"+String.valueOf(temp.charAt(i))+"</font>";
                else
                    s=s+"<font color='"+"#1e90ff"+"'>"+String.valueOf(temp.charAt(i))+"</font>";
            }
            String s1="<font color='"+"#1e9e17"+"'>="+arr[1]+"</font>";
            nhap.setText("");
            nhap.append(Html.fromHtml(s));
            nhap.append("\n");
            nhap.append(Html.fromHtml(s1));
        }else{

            for(int i=0;i<temp.length();i++){
                if(fx.isNumber(String.valueOf(temp.charAt(i)))||temp.charAt(i)=='('||temp.charAt(i)==')'||temp.charAt(i)=='.'||temp.charAt(i)==',')
                    s=s+"<font color='"+"#4a5968"+"'>"+String.valueOf(temp.charAt(i))+"</font>";
                else
                    s=s+"<font color='"+"#1e90ff"+"'>"+String.valueOf(temp.charAt(i))+"</font>";
            }
            nhap.setText(Html.fromHtml(s), TextView.BufferType.SPANNABLE);
        }

    }
    //getKq
    public String getKq(){
        String [] arr=nhap.getText().toString().split("=");
        return arr[1];
    }
    //Làm cho textview luôn hiện dưới đáy khi nhấn =
    public void autoScroll(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
    //Hàm trả về chuỗi phân cách phần ngàn
    private String showDecimalFormat(){
        int countDigit=0;
        StringBuilder input=new StringBuilder(nhap.getText().toString());
        for(int i=input.length()-1;i>-1;i--){
            char ch=input.charAt(i);
            if(Character.isDigit(ch)||ch=='.'||ch==','){
                countDigit++;
            }else{
                break;
            }
        }
        String tt="";
        if(countDigit>0){
            String temp=input.toString().substring(input.length()-countDigit);
            temp=temp.replaceAll(",","");
            tt=getDecimalFormattedFromString(temp);
            int index2insert=input.length()-countDigit;
            input.delete(input.length()-countDigit,input.length());
            input.insert(index2insert,tt);
        }
        return input.toString();
    }
    //Thêm , giữa các phần ngàn
    public String getDecimalFormattedFromString(String value)
    {
        //343+ =>1,234
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            j--;
            str3 = ".";
        }
        for (int k = j; ; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3) {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }
    //xử lý chuỗi 000000-->0 &&01-->1 ....
    public boolean Chuoi0(){
        InfixToPostfix fx=new InfixToPostfix();
        if(nhap.getText().toString().equals("")||(nhap.getText().toString().length()==1&&fx.isOperator(nhap.getText().toString())))
            return true;
        else if(fx.isNumber(nhap.getText().toString().replaceAll(",",""))&&nhap.getText().toString().equals("0"))
            return false;
        else {
            String temp=nhap.getText().toString().replaceAll(",","");
            String s="";
            //tạo các khoảng trắng cho chuỗi con trong chuỗi biểu thức
            for(int i=0;i<temp.length();i++){
                if(fx.isOperator(String.valueOf(temp.charAt(i))))
                    s=s+" "+String.valueOf( temp.charAt(i))+" ";
                else
                    s=s+String.valueOf(temp.charAt(i));
            }
            s=s.trim();
            s = s.replaceAll("\\s+", " ");

            String []Array=s.split(" ");

            if(fx.isNumber(Array[Array.length-1])&&Array[Array.length-1].equals("0"))
                return false;
        }
        return true;
    }

}
