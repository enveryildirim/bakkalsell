package com.company.pages.components;

import java.util.Scanner;

public class Input {
    private String text;
    private String label;
    private String regex;
    private boolean isRequired;

    //todo javadoc ile parametrelerin nasıl kullancağı anlatılacak regex boş olursa şu olur diye
    //todo exception düzenlemesi
    public Input(String label, String regex, boolean required) {
        this.text = "";
        this.label = label;
        this.regex = regex;
        this.isRequired = required;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        this.isRequired = required;
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


    public int getTextAfterConvertToInt() {
        try {
            return Integer.parseInt(getText());
        } catch (Exception e) {
            return -1;
        }
    }
    public float getTextAfterConvertToFloat() {
        try {
            return Float.parseFloat(getText());
        } catch (Exception e) {
            return -1;
        }
    }
    public String render() {
        Scanner in = new Scanner(System.in);
        String msjLabel = this.getLabel() == null ? "Etiket Yok" : this.getLabel();
        System.out.println(msjLabel);
        while (true) {
            String text = in.nextLine();

            if (this.isRequired() && text.length() == 0) {
                System.out.printf("%s Alanı Gerekli Lütfen Boş Bırakmayınız", this.getLabel());
                continue;
            }

            if (this.getRegex() != null) {
                if (!text.matches(this.getRegex())) {
                    System.out.printf("%s için Uygun veri giriniz", this.getLabel());
                    continue;
                }
            }

            this.setText(text);
            break;
        }
        return this.getText();
    }
}