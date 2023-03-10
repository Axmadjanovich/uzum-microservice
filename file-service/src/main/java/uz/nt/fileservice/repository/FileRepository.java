package uz.nt.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.fileservice.model.File;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<File> findByIdAndPathlarge(Integer id, String pathLarge);
    Optional<File> findByIdAndPathmedium(Integer id, String pathMedium);
    Optional<File> findByIdAndPathsmall(Integer id, String pathSmall);
}
