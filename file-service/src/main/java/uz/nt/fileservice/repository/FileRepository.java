package uz.nt.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.fileservice.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
}
