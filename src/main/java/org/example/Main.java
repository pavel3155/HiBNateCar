package org.example;
import Entity.Body;
import Entity.Car;
import Entity.Engine;
import connBD.ConnectJDBC;
import connBD.SessionJPA;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        //разворачиваем БД из скрипта
        ConnectJDBC conn = new ConnectJDBC("postgres", "123456");
        conn.DropDB("db_car");
        conn.CreatDB("db_car");
        conn.connToDB("db_car", "");
        conn.exeScript("tbls_db_car.sql");
        conn.connClose();

        SessionJPA.getAllRow(Body.class);///получаем список Body
        SessionJPA.getAllRow(Engine.class);///получаем список Engine
        SessionJPA.getAllRow(Car.class);///получаем список Car

        int idBody = SessionJPA.objADD(new Body("Пикап"));//добавляем новый объет в таблицу
        Body body = SessionJPA.objFind(Body.class, idBody);//получаем объет из таблицы
        Engine eng = SessionJPA.objFind(Engine.class, 5);//получаем объет из таблицы
        //добавляем новый объет в таблицу
        SessionJPA.objADD(new Car("Шкода", "Актавиа", eng, body, new BigDecimal("2000000.0"), "20.11.2023"));
        SessionJPA.getAllRow(Body.class);///получаем список Body
        SessionJPA.getAllRow(Car.class);///получаем список Car
        body.setType("Лифтбек");//присваиваем новый тип "Лифтбек" объекту body
        SessionJPA.megreObj(body);//вносим изменения по объекту body в таблицу
        SessionJPA.getAllRow(Body.class);///получаем список Body, наблюдаем внесенные изменения по объекту body в таблице
        List<Car> cars = SessionJPA.getAllRow(Car.class);//получаем список Car и видим изменения по добавленной записи
        // меняем марку и модель во всех объектах таблиц car
        for (Car objCar : cars) {
            objCar.setMarka("Мерседес");
            objCar.setModel("Вито");
            SessionJPA.megreObj(objCar);
        }
        SessionJPA.getAllRow(Car.class);///получаем список Car, наблюдаем внесенные изменения
    }
}



