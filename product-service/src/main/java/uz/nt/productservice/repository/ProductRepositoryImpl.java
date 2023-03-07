package uz.nt.productservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
import lombok.RequiredArgsConstructor;
>>>>>>> develop
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.models.Product;

<<<<<<< HEAD
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl {
    @Autowired
    private EntityManager entityManager;

    public List<Product> universalSearch(Map<String, String> params){
        String sqlQuery = "select p from Product p where 1=1 ";
        StringBuilder queryCondition = new StringBuilder();

        generateQueryCondition(queryCondition, params);

        Query query = entityManager.createQuery(sqlQuery + queryCondition, Product.class);

        setParams(query, params);

        return query.getResultList();
    }

    public Page<Product> universalSearch2(Map<String, String> params){
        int page = 0, size = 10;

        if (params.containsKey("size")) {
            size = Integer.parseInt(params.get("size"));
=======
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl {


    private final EntityManager entityManager;

    public Page<Product> universalSearch2(Map<String, String> params) {

        int page = 0, size = 10;

        if (params.containsKey("size")) {
            size = Math.max(Integer.parseInt(params.get("size")), 1);
>>>>>>> develop
        }
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }

<<<<<<< HEAD
        String sqlQuery = "select p from Product p where 1=1 ";
        String sqlCountQuery = "select count(1) from Product p where 1=1 ";
=======

        String sql = "select p from Product p where 1=1 ";

        String sqlCounter = "select count(1) from Product p where 1=1 ";
>>>>>>> develop
        StringBuilder queryCondition = new StringBuilder();

        generateQueryCondition(queryCondition, params);

<<<<<<< HEAD
        Query query = entityManager.createQuery(sqlQuery + queryCondition, Product.class);
        Query countQuery = entityManager.createQuery(sqlQuery + queryCondition, Product.class);
        setParams(query, params);
        setParams(countQuery, params);

        long count = (long) countQuery.getSingleResult();

        if (page>= count / size) {
            if (count % size == 0) {
                page = (int) count / size - 1;
            }
        }
        else {
            page = (int) count / size;
        }

        query.setFirstResult(size * page);
        query.setMaxResults(size);

        List<Product> products = query.getResultList();
        return new PageImpl<>(products, PageRequest.of(page, size), count);
    }

    private void generateQueryCondition(StringBuilder queryCondition, Map<String, String> params){
        if (params.containsKey("name")){
            queryCondition.append(" AND upper(p.name) like :name ");
        }
        if (params.containsKey("amount")){
            queryCondition.append(" AND p.amount = :amount");
        }
        if (params.containsKey("price")){
            queryCondition.append(" AND p.price = :price");
        }
        //...
    }

    private void setParams(Query query, Map<String, String> params){
        if (params.containsKey("name")){
            query.setParameter("name", "%" + params.get("name").toUpperCase() + "%");
        }
        if (params.containsKey("amount")){
            query.setParameter("amount", Integer.parseInt(params.get("amount")));
        }
        if (params.containsKey("price")){
            query.setParameter("price", Integer.parseInt(params.get("price")));
        }
=======
        Query query = entityManager.createQuery(sql + queryCondition, Product.class);
        Query queryCounter = entityManager.createQuery(sqlCounter + queryCondition, Product.class);

        setParams(query, params);
        setParams(queryCounter, params);

        long count = (long) queryCounter.getSingleResult();

        if (count / size <= page) {
            if (count % size == 0) page = (int) (count / size) - 1;
            else page = (int) count / size;
        }

        query.setFirstResult(size*page);
        query.setMaxResults(size);

        return new PageImpl<>(query.getResultList(), PageRequest.of(page,size),count);

    }

    //TODO qidiruvga ID va categoryID ustunlarini qo'shish
    private void generateQueryCondition(StringBuilder queryCondition, Map<String, String> params) {
        if (params.containsKey("name")) {
            queryCondition.append(" AND upper(p.name) like :name ");
        }
        if (params.containsKey("amount")) {
            queryCondition.append(" AND p.amount = :amount");
        }
        if (params.containsKey("price")) {
            queryCondition.append(" AND p.price = :price");
        }
        if (params.containsKey("description")) {
            queryCondition.append(" AND upper(p.description) like :description");
        }
        //...
    }

    private void setParams(Query query, Map<String, String> params) {
        if (params.containsKey("name")) {
            query.setParameter("name", "%" + params.get("name").toUpperCase() + "%");
        }
        if (params.containsKey("amount")) {
            query.setParameter("amount", Integer.parseInt(params.get("amount")));
        }
        if (params.containsKey("price")) {
            query.setParameter("price", Integer.parseInt(params.get("price")));
        }
        if (params.containsKey("description")) {
            query.setParameter("description", "%" + params.get("description").toUpperCase() + "%");
        }
>>>>>>> develop
    }
}
