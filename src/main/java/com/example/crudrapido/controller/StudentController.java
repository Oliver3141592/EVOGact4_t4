package com.example.crudrapido.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.example.crudrapido.dto.StudentDto;
import com.example.crudrapido.service.StudentService;
 
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
 
    @Autowired
    private StudentService studentService;
 
    @GetMapping
    public ResponseEntity<Page<StudentDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(studentService.getStudents(pageable));
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        return studentService.getStudent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
 
    @PostMapping
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(dto));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @Valid @RequestBody StudentDto dto) {
        return studentService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (studentService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}