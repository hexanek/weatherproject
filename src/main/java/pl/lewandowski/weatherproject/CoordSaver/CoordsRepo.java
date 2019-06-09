package pl.lewandowski.weatherproject.CoordSaver;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordsRepo extends CrudRepository<Coords,Long> {
}
