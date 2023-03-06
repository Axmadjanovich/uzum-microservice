package service.mapper;

import dto.FileDto;
import model.FileDao;

import java.time.LocalDateTime;

public class FileMapper {
    public static FileDto toDto(FileDao fileDao){
        FileDto dto = new FileDto();
        dto.setName(fileDao.getName());
        dto.setExtension(fileDao.getExtension());
        dto.setId(dto.getId());
        return dto;
    }
    public static FileDao toDao(FileDto fileDto){
        FileDao dao = new FileDao();
        dao.setExtension(fileDto.getExtension());
        dao.setName(fileDto.getName());
        dao.setId(fileDto.getId());
        dao.setCreatedAt(LocalDateTime.now());
        return dao;
    }
}
