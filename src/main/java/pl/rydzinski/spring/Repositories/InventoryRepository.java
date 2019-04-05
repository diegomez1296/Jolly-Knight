package pl.rydzinski.spring.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.rydzinski.spring.HeroPackage.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory,Long> {

    Inventory findByInventoryId (Long id);
}
