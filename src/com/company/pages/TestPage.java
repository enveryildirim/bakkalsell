package com.company.pages;

import com.company.Constant;
import com.company.dal.DB;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPage extends PageBase{
    public TestPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        Input txt=new Input(null,"AD SOYAD:", "[A-Za-z0-9]{5,}$",true);
        //Input txt=new Input(null,"Şifre","^[a-z]{0,10}$",true);
        String deger=txt.render();
        System.out.println("Componentent alınan veri:::::"+txt.getText());
        System.out.printf("Devam etmek için bir tuşa basın sayfa sonu");
        in.nextLine();
        return PageName.TEST;
    }
}
class Input {
    public Input(String text, String label, String regex, boolean required) {
        this.text = text;
        this.label = label;
        this.regex = regex;
        this.required = required;
    }

    private String text;
    private String label;
    private String regex;
    private boolean required;


    public boolean isRequired() {
        return required;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }
    public String getRegex() {
        return regex;
    }
    public void setRegex(String regex) {
        this.regex = regex;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    public int getInt(){
        try {
            return Integer.parseInt(getText());
        }catch (Exception e){
            return -1;
        }
    }

    public String render(){
        Scanner in = new Scanner(System.in);
        System.out.println(this.getLabel()==null?"Etiket Yok":this.getLabel());
        while(true){
            String text=in.nextLine();
            if(this.isRequired()&&text.length()==0) {
                System.out.println("Gerekli Alan Lütfen Boş Bırakmayınız");
                continue;
            }
            if(this.getRegex()!=null){
                if(!text.matches(this.getRegex())){
                    System.out.println("Uygun veri giriniz");
                    continue;
                }
            }
            this.setText(text);
            break;
        }
        return this.getText();
    }
}