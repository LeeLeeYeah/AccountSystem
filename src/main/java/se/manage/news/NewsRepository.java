package se.manage.news;

import org.springframework.data.repository.CrudRepository;
import se.manage.stock.UserStock;

import java.util.List;

public interface NewsRepository extends CrudRepository<News, Long> {
    List<News> findByNewsID(String newsID);
    List<News> findByStockCode(String stockCode);
}
