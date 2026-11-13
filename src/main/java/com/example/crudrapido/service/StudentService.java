package com.example.crudrapido.service;
 
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
 
import com.example.crudrapido.dto.StudentDto;
import com.example.crudrapido.entity.Student;
import com.example.crudrapido.repository.StudentRepository;
 
@Service
public class StudentService {
 
    @Autowired
    private StudentRepository studentRepository;
 
    public Page<StudentDto> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::toDto);
    }
 
    public Optional<StudentDto> getStudent(Long id) {
        return studentRepository.findById(id).map(this::toDto);
    }
 
    public StudentDto create(StudentDto dto) {
        return toDto(studentRepository.save(toEntity(new Student(), dto)));
    }
 
    public Optional<StudentDto> update(Long id, StudentDto dto) {
        return studentRepository.findById(id)
                .map(s -> toDto(studentRepository.save(toEntity(s, dto))));
    }
 
    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
 
    // DTO -> Entity
    private Student toEntity(Student s, StudentDto dto) {
        s.setFirstName(dto.firstName());
        s.setLastName(dto.lastName());
        s.setEmail(dto.email());
        s.setDescription(dto.description());
        return s;
    }
 
    // Entity -> DTO
    private StudentDto toDto(Student s) {
        return new StudentDto(s.getStudentId(), s.getFirstName(), s.getLastName(),
                s.getEmail(), s.getDescription());
    }
}