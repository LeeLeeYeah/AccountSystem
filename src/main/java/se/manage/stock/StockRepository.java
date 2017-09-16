package se.manage.stock;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<UserStock, Long> {
    List<UserStock> findByAccount(String account);
    List<UserStock> findByRecordID(String recordID);
}
