package com.seon.springvueproject.service.file;

import com.seon.springvueproject.domain.file.FileLoad;
import com.seon.springvueproject.domain.file.FileRepository;
import com.seon.springvueproject.web.dto.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    /**
     * 파일정보 가져오기
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<FileDto> getFiles(Long id){
        List<FileLoad> fileLoadList = fileRepository.findByBoardId(id);
        List<FileDto> fileDtoList = new ArrayList<>();

        if (fileLoadList.isEmpty()) return fileDtoList;

        for (FileLoad fileLoad : fileLoadList){
            FileDto fileDto = FileDto.builder()
                    .id(fileLoad.getId())
                    .realFilename(fileLoad.getRealFilename())
                    .filename(fileLoad.getFilename())
                    .filePath(fileLoad.getFilePath())
                    .build();
            fileDtoList.add(fileDto);
        }

        return fileDtoList;
    }

    /**
     * 파일 다운로드
     * @param id
     * @return
     */
    @Transactional
    public FileDto getDownloadFile(Long id){
        FileLoad fileLoad = fileRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 파일은 존재하지 않습니다." + id));

        return FileDto.builder()
                .id(id)
                .realFilename(fileLoad.getRealFilename())
                .filename(fileLoad.getFilename())
                .filePath(fileLoad.getFilePath())
                .build();
    }
}
