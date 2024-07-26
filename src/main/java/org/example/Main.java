package org.example;
import Entity.Body;
import Entity.Car;
import Entity.Engine;
import connBD.ConnectJDBC;
import connBD.SessionJPA;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        int idBody = SessionJPA.objADD(new Body("Пикап"));//добавляем новый объет Body в таблицу и получаем его PK
        SessionJPA.getAllRow(Body.class);///получаем список Body
        Body body = SessionJPA.objFind(Body.class, idBody);//получаем объет Body из таблицы по его PK
        Engine eng = SessionJPA.objFind(Engine.class, 5);//получаем объет Engine из таблицы по его PK
        //добавляем новый объект Car в таблицу
        SessionJPA.objADD(new Car("Шкода", "Актавиа", eng, body, new BigDecimal("2000000.0"), "20.11.2023"));
        SessionJPA.getAllRow(Car.class);///получаем список Car
        body.setType("Лифтбек");//присваиваем новый тип "Лифтбек" объекту body
        SessionJPA.megreObj(body);//вносим изменения по объекту body в таблицу
        SessionJPA.getAllRow(Body.class);///получаем список Body, наблюдаем внесенные изменения по объекту body в таблице
        List<Car> cars = SessionJPA.getAllRow(Car.class);//получаем список Car и видим изменения по добавленной записи
        // меняем марку и модель во всех объектах таблицы car
        for (Car objCar : cars) {
            objCar.setMarka("Мерседес");
            objCar.setModel("Вито");
            SessionJPA.megreObj(objCar);
        }
        SessionJPA.getAllRow(Car.class);///получаем список Car, наблюдаем внесенные изменения
        //меняем model и body у всех объектов (записей) Car c Body(id=5...)
        SessionJPA.myQueryUpdate2("model",
                "ЦЛС",
                "body",
                SessionJPA.objFind(Body.class,2),
                "body",
                SessionJPA.objFind(Body.class,5),
                Car.class);
        SessionJPA.getAllRow(Car.class);///получаем список Car, наблюдаем внесенные изменения
        //меняем двигатель объекта Car(таблица car) с id = 5
        SessionJPA.UpdateCarEngineByID("Car",SessionJPA.objFind(Engine.class,9),5);

        SessionJPA.getAllRow(Car.class);///получаем список Car, наблюдаем внесенные изменения

        //меняем model у всех объектов (записей) Car c Body(id=3...) и Engine(id=6...)
        SessionJPA.myQueryUpdate("model",
                "ЦЛС",
                "body",
                SessionJPA.objFind(Body.class,3),
                "eng",
                SessionJPA.objFind(Engine.class,6),
                Car.class);

        SessionJPA.getAllRow(Car.class);///получаем список Car, наблюдаем внесенные изменения

        //коллекция (поле и значение) для обнавления объектов(записей) Car
        Map<String, Object> setMapCar = new HashMap<>();
        setMapCar.put("marka","Mercedes-Benz");
        setMapCar.put("model","CLA");
        //коллекция (поле и значение) условий для выполнения обнавления объектов(записей) Car
        Map<String, Object> whMapCar = new HashMap<>();
        setMapCar.put("body",SessionJPA.objFind(Body.class,2));
        setMapCar.put("eng",SessionJPA.objFind(Engine.class,4));
        //обновление объектов(записей) Car
        SessionJPA.myQueryUpdate(setMapCar,whMapCar, Car.class);

        SessionJPA.getAllRow(Engine.class);///получаем список Engine,
        //коллекция (поле и значение) для обнавления объектов(записей) Engine
        Map<String, Object> setMapEng = new HashMap<>();
        setMapEng.put("typeFuel","дизель");
        //коллекция (поле и значение) условий для выполнения обнавления объектов(записей) Engine
        Map<String, Object> whMapEng = new HashMap<>();
        whMapEng.put("id",8);
        //обновление объектов(записей) Engine
        SessionJPA.myQueryUpdate(setMapEng,whMapEng, Engine.class);

        SessionJPA.getAllRow(Engine.class);///получаем список Engine, наблюдаем внесенные изменения
    }
}



