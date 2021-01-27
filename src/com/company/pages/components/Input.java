package com.company.pages.components;

import java.util.Scanner;

public class Input {
    private String text;
    private String label;
    private String regex;
    private boolean required;

    public Input(String label, String regex, boolean required) {
        this.text = "";
        this.label = label;
        this.regex = regex;
        this.required = required;
    }

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