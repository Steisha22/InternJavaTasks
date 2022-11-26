package org.example.Task2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class Violation extends DefaultHandler {
    private String date_time;
    private String first_name;
    private String last_name;
    private String type;
    private double final_amount;
    //Змінна для фіксування того, в якому елементі ми знаходимося
    private String lastElementName;

    public ArrayList<Violation> violations = new ArrayList<>();;

    public Violation() {
    }

    public Violation(String date_time, String first_name, String last_name, String type, double final_amount) {
        this.date_time = date_time;
        this.first_name = first_name;
        this.last_name = last_name;
        this.type = type;
        this.final_amount = final_amount;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        lastElementName = qName;
    }

    //Метод, в якому спочатку видаляються всі зайві переноси рядка та фільтрується інформація,
    //і якщо там ще залишилася інформація, то, значить, це потрібний текст,
    //а далі, використовуючи lastElementName, визначається, який це тег
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String information = new String(ch, start, length);
        information = information.replace("\n", "").trim();

        if (!information.isEmpty()) {
            if (lastElementName.equals("date_time"))
                date_time = information;
            if (lastElementName.equals("first_name"))
                first_name = information;
            if (lastElementName.equals("last_name"))
                last_name = information;
            if (lastElementName.equals("type"))
                type = information;
            if (lastElementName.equals("final_amount"))
                final_amount = Double.valueOf(information);
        }
    }

    //Метод, в якому перевіряється, чи рахована вся інформація, і якщо вся інформація зчитана,
    // то створюється нове порушення та скидається інформація
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ((date_time != null && !date_time.isEmpty()) && (first_name != null && !first_name.isEmpty())
                && (last_name != null && !last_name.isEmpty()) && (type != null && !type.isEmpty())
                && (final_amount != 0.0)) {
            violations.add(new Violation(date_time, first_name, last_name, type, final_amount));
            date_time = null;
            first_name = null;
            last_name = null;
            type = null;
            final_amount = 0;
        }
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(double final_amount) {
        this.final_amount = final_amount;
    }
}