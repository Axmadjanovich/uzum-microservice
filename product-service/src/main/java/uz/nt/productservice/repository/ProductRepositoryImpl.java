package uz.nt.productservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.models.Product;

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
        }
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }

        String sqlQuery = "select p from Product p where 1=1 ";
        String sqlCountQuery = "select count(1) from Product p where 1=1 ";
        StringBuilder queryCondition = new StringBuilder();

        generateQueryCondition(queryCondition, params);

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
    }
}
