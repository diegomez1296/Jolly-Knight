package pl.rydzinski.spring.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.rydzinski.spring.HeroPackage.Item;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

  @Query(value = "SELECT ITEM_ID , ITEM_LVL, TYPE_OF_ITEM, TYPE_OF_RESOURSE_TO_UPGRADE1, TYPE_OF_RESOURSE_TO_UPGRADE2 FROM ITEM INNER JOIN CHARACTER ON CHARACTER.HERO_ID = ITEM.HERO_ID  WHERE CHARACTER.HERO_ID = :id", nativeQuery = true)
   List<Item> getItemsById (@Param("id") Long id);

    Item findByItemId (Long id);
}
