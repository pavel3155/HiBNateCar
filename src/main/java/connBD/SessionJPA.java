package connBD;

import Entity.Body;
import Entity.Car;
import Entity.Engine;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.*;

public class SessionJPA {

    /**
     * метод возвращает список объектов класса(сущности) из БД
     * @param cls
     * @return
     * @param <T>
     */
    public static <T> List<T> getAllRow(Class<T> cls){
        SessionFactory sesFactory =new Configuration().configure().buildSessionFactory();
        try(Session session= sesFactory.openSession()){
            String hql = "from "+cls.getName();
            Query<T> query =  session.createQuery(hql, cls);
            List<T> list =query.list();
            for(T item : list) {
                System.out.println(item);
            }
            sesFactory.close();
            return list;
        }
    }

    public static <T> void UpdateCarEngineByID(Class<T> cls,Engine eng, int id){
        SessionFactory sesFactory =new Configuration().configure().buildSessionFactory();
        try(Session session= sesFactory.openSession()){
            session.beginTransaction();
            //String hql2 = "update "+cls.getName()+" set eng="+objFind(Engine.class,9)+" where id=5";
            String hql = "update "+cls.getName()+" set eng=:eng where id=:id";
            Query query =  session.createQuery(hql);
            query.setParameter("eng",eng);
            query.setParameter("id",id);
            int count = query.executeUpdate();
            session.getTransaction().commit();
            sesFactory.close();
        }
    }

    public static  void UpdateCarEngineByID(String clsName,Engine eng, int id){
        SessionFactory sesFactory =new Configuration().configure().buildSessionFactory();
        try(Session session= sesFactory.openSession()){
            session.beginTransaction();
            String hql = "update "+ clsName+" set eng=:eng where id=:id";
            Query query =  session.createQuery(hql);
            query.setParameter("eng",eng);
            query.setParameter("id",id);
            System.out.println(hql);
            int count = query.executeUpdate();
            session.getTransaction().commit();
            sesFactory.close();
        }
    }

    public static  <T> T  megreObj(T objClass) {
        SessionFactory SF=new Configuration().configure().buildSessionFactory();
        Session session= SF.openSession();
        Transaction transaction = session.beginTransaction();
        T objT= session.merge(objClass);
        transaction.commit();
        session.close();
        SF.close();
        return objT;
    }

    /**
     * метод добавляет объект сущность в соответствующую таблицу БД,
     * возвращает id(PK) добавленного в таблицу объекта
     * @param objClass
     * @return -id(PK
     * @param <T>
     */
    public static <T> Integer objADD(T objClass){
        SessionFactory SF=new Configuration().configure().buildSessionFactory();
        Session session= SF.openSession();
        Transaction transaction = session.beginTransaction();
        Integer id=(Integer) session.save(objClass);
        transaction.commit();
        session.close();
        SF.close();
        return id;
    }

    /**
     * метод возвращает объектc по id(PK)соответствующейтаблицы БД
     * @param cls -класс
     * @param pKey -первичный ключ
     * @return - объект класса T
     * @param <T>
     */
    public static <T> T objFind(Class<T> cls, int pKey){
        SessionFactory SF=new Configuration().configure().buildSessionFactory();
        Session session= SF.openSession();
        T obj=session.find(cls,pKey);
        T obj1=session.find(cls,pKey);
        session.close();
        SF.close();
        return obj;
    }


    /**
     * метод обновляет одно поле таблицы, при выполнении условия
     * @param setFieled - обновляемое поле
     * @param setValue - значение, которе требуется установить
     * @param whFieled - поле условия для обновления
     * @param whValue - значение поля условия
     * @param cls -класс(Entity) связанный с таблицей в БД
     * @param <S>
     * @param <W>
     * @param <T>
     */
    public static  <S,W,T> void myQueryUpdate(String setFieled,
                                      S setValue,
                                      String whFieled,
                                      W whValue,
                                      Class<T> cls)
    {
        SessionFactory SF = new Configuration().configure().buildSessionFactory();
        try (Session session = SF.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<T> criteriaUpdate = builder.createCriteriaUpdate(cls);
            Root<T> root = criteriaUpdate.from(cls);
            criteriaUpdate.set(setFieled,setValue);
            criteriaUpdate.where(builder.equal (root.get(whFieled), whValue));
            Transaction transaction = session.beginTransaction();
            int count = session.createQuery(criteriaUpdate).executeUpdate();
            System.out.println("количество обновленных записей: " + count);
            transaction.commit();
        }
        SF.close();
    }

    /**
     * метод обновляет два поля таблицы, при выполнении условия
     * @param setFieled1 - обновляемое поле 1
     * @param setValue1 - новое значение поля 1
     * @param setFieled2 обновляемое поле 2
     * @param setValue2 - новое значение поля 2
     * @param whFieled - поле условия для обновления
     * @param whValue - значение поля условия
     * @param cls -класс(Entity) связанный с таблицей в БД
     * @param <S1>
     * @param <S2>
     * @param <W>
     * @param <T>
     */
    public static  <S1,S2,W,T> void myQueryUpdate2(String setFieled1,
                                              S1 setValue1,
                                              String setFieled2,
                                              S2 setValue2,
                                              String whFieled,
                                              W whValue,
                                              Class<T> cls)
    {
        SessionFactory SF = new Configuration().configure().buildSessionFactory();
        try (Session session = SF.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<T> criteriaUpdate = builder.createCriteriaUpdate(cls);
            Root<T> root = criteriaUpdate.from(cls);
            criteriaUpdate.set(setFieled1,setValue1);
            criteriaUpdate.set(setFieled2,setValue2);
            criteriaUpdate.where(builder.equal(root.get(whFieled), whValue));
            Transaction transaction = session.beginTransaction();
            int count = session.createQuery(criteriaUpdate).executeUpdate();
            System.out.println("количество обновленных записей: " + count);
            transaction.commit();
        }
        SF.close();
    }
    /**
     * метод обновляет одно поле таблицы связанной с калассом (сущностью)'cls',при выполнении двух условий
     * @param setFieled - обновляемое поле
     * @param setValue - значение, которе требуется установить
     * @param whFieled1 - поле условия 1 для обновления
     * @param whValue1 - значение поля условия 1
     * @param whFieled2 - поле условия 2 для обновления
     * @param whValue2- значение поля условия 2
     * @param cls- класс(Entity) связанный с таблицей в БД
     * @param <S>
     * @param <W1>
     * @param <W2>
     * @param <T>
     */
    public static  <S,W1,W2,T> void myQueryUpdate(String setFieled,
                                          S setValue,
                                          String whFieled1,
                                          W1 whValue1,
                                          String whFieled2,
                                          W2 whValue2,
                                          Class<T> cls )
    {
        SessionFactory SF = new Configuration().configure().buildSessionFactory();

        try (Session session = SF.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<T> criteriaUpdate = builder.createCriteriaUpdate(cls);
            Root<T> root = criteriaUpdate.from(cls);
            criteriaUpdate.set(setFieled,setValue);
            List<Predicate> pr = new ArrayList<>();
            Predicate wV1=builder.equal(root.get(whFieled1),whValue1);
            Predicate wV2=builder.equal(root.get(whFieled2),whValue2);
            pr.add(wV1);
            pr.add(wV2);
            criteriaUpdate.where(builder.and(pr.toArray(new Predicate[0])));
            Transaction transaction = session.beginTransaction();
            int count = session.createQuery(criteriaUpdate).executeUpdate();
            System.out.println("количество обновленных записей: " + count);
            transaction.commit();
        }
        SF.close();
    }

    /**
     * метод реализует динамический запрос на обновление
     * @param mSet-коллекция пар(имя поля:значение) для обновления
     * @param mWH-коллекция пар(имя поля:значение) для условия
     * @param cls -класс(Entity) связанный с таблицей в БД
     * @param <T>
     */
    public static  <T> void myQueryUpdate(Map<String,Object> mSet,
                                                   Map<String,Object> mWH,
                                                   Class<T> cls)
    {
        SessionFactory SF = new Configuration().configure().buildSessionFactory();
        try (Session session = SF.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<T> criteriaUpdate = builder.createCriteriaUpdate(cls);
            Root<T> root = criteriaUpdate.from(cls);
            for (String key : mSet.keySet()) {
                Object value = mSet.get(key);
                criteriaUpdate.set(key,value);
            }
            List<Predicate> pr = new ArrayList<>();
            for (String key : mWH.keySet()) {
                Object value = mWH.get(key);
                Predicate predicate=builder.equal(root.get(key),value);
                pr.add(predicate);
            }
            criteriaUpdate.where(builder.and(pr.toArray(new Predicate[0])));
            Transaction transaction = session.beginTransaction();
            int count = session.createQuery(criteriaUpdate).executeUpdate();
            System.out.println("количество обновленных записей: " + count);
            transaction.commit();
        }
        SF.close();
    }
}
